package pages.api;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class CandidateModule {
    private static final Logger log = LoggerFactory.getLogger(CandidateModule.class);
    private final String baseURL;
    private Cookies sessionCookies;

    public CandidateModule() {
        this("https://opensource-demo.orangehrmlive.com");
    }

    public CandidateModule(String baseURL) {
        log.info("Initializing CandidateModule with baseURL: {}", baseURL);
        this.baseURL = baseURL;
        log.debug("CandidateModule initialized successfully");
    }

    public void authenticateViaAPI(String username, String password) {
        log.info("Starting API authentication for user: {}", username);

        // Step 1: Load login page and extract CSRF token
        log.debug("Step 1: Loading login page to extract CSRF token");
        Response loginPage = given()
                .relaxedHTTPSValidation()
                .when()
                .get(baseURL + "/web/index.php/auth/login")
                .then()
                .extract().response();

        log.debug("Login page response status: {}", loginPage.statusCode());
        sessionCookies = loginPage.getDetailedCookies();
        log.debug("Login page cookies captured: {}", sessionCookies);

        if (loginPage.statusCode() < 200 || loginPage.statusCode() >= 400) {
            log.error("Failed to load login page: {}", loginPage.statusLine());
            throw new IllegalStateException("Failed to load login page: " + loginPage.statusLine());
        }

        String html = loginPage.asString();
        log.debug("Extracting CSRF token from login page HTML");
        Pattern p = Pattern.compile(":token=\"&quot;([^&]+)&quot;\"");
        Matcher m = p.matcher(html);
        String csrfToken = null;
        if (m.find()) {
            csrfToken = m.group(1);
        }
        if (csrfToken == null || csrfToken.isEmpty()) {
            log.error("CSRF token not found on login page");
            log.debug("HTML excerpt: {}", html.length() > 500 ? html.substring(0, 500) : html);
            throw new IllegalStateException("CSRF token not found on login page");
        }
        log.debug("CSRF token extracted successfully: {}", csrfToken);

        // Step 2: Post credentials with form-encoded body
        log.debug("Step 2: Posting login credentials with session cookies");
        Response loginResp = given()
                .relaxedHTTPSValidation()
                .cookies(sessionCookies)
                .contentType("application/x-www-form-urlencoded")
                .formParam("_token", csrfToken)
                .formParam("username", username)
                .formParam("password", password)
                .redirects().follow(true)
                .when()
                .post(baseURL + "/web/index.php/auth/validate")
                .then()
                .extract().response();

        int status = loginResp.statusCode();
        log.debug("Login response status: {}", status);

        // Update session cookies from login response
        if (loginResp.getDetailedCookies() != null && !loginResp.getDetailedCookies().asList().isEmpty()) {
            sessionCookies = loginResp.getDetailedCookies();
            log.debug("Session cookies updated after login: {}", sessionCookies);
        }

        // Check if login was successful by inspecting response body
        String responseBody = loginResp.asString();
        if (responseBody.contains("auth-login") || responseBody.contains(":token=")) {
            log.error("Login failed - still on login page after authentication");
            throw new IllegalStateException("API login failed: credentials invalid or CSRF token expired");
        }

        if (status < 200 || status >= 400) {
            log.error("API login failed: {} - {}", loginResp.statusLine(), responseBody.substring(0, Math.min(200, responseBody.length())));
            throw new IllegalStateException("API login failed: " + loginResp.statusLine());
        }
        log.info("Login credentials posted successfully");

        // Step 3: Verify session by loading dashboard
        log.debug("Step 3: Verifying session by loading dashboard");
        Response verify = given()
                .relaxedHTTPSValidation()
                .cookies(sessionCookies)
                .when()
                .get(baseURL + "/web/index.php/dashboard/index")
                .then()
                .extract().response();

        log.debug("Dashboard verification response status: {}", verify.statusCode());

        // Update cookies again if needed
        if (verify.getDetailedCookies() != null && !verify.getDetailedCookies().asList().isEmpty()) {
            sessionCookies = verify.getDetailedCookies();
            log.debug("Session cookies updated after dashboard verification: {}", sessionCookies);
        }

        String dashboardBody = verify.asString();
        if (dashboardBody.contains("auth-login") || verify.statusCode() >= 400) {
            log.error("Login verification failed - redirected to login page");
            throw new IllegalStateException("Login verification failed: session not established");
        }

        log.info("API authentication completed successfully for user: {}", username);
    }

    public Response addCandidate(Map<String, Object> candidateData) {
        log.info("Adding candidate via API: {} {}", candidateData.get("firstName"), candidateData.get("lastName"));
        log.debug("Building candidate payload");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("firstName", Objects.toString(candidateData.get("firstName"), ""));
        payload.put("middleName", Objects.toString(candidateData.getOrDefault("middleName", ""), ""));
        payload.put("lastName", Objects.toString(candidateData.get("lastName"), ""));
        payload.put("email", Objects.toString(candidateData.get("email"), ""));
        payload.put("contactNumber", Objects.toString(candidateData.getOrDefault("contactNumber", ""), ""));
        payload.put("keywords", Objects.toString(candidateData.getOrDefault("keywords", ""), ""));
        payload.put("comment", Objects.toString(candidateData.getOrDefault("comment", ""), ""));
        payload.put("dateOfApplication", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        log.debug("Sending POST request to create candidate with session cookies");
        Response response = given()
                .relaxedHTTPSValidation()
                .cookies(sessionCookies)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(baseURL + "/web/index.php/api/v2/recruitment/candidates")
                .then()
                .extract().response();

        log.info("Add candidate response status: {}", response.statusCode());
        return response;
    }

    public Response getCandidates() {
        log.info("Fetching candidates list (limit: 50, offset: 0)");
        Response response = given()
                .relaxedHTTPSValidation()
                .cookies(sessionCookies)
                .queryParam("limit", 50)
                .queryParam("offset", 0)
                .when()
                .get(baseURL + "/web/index.php/api/v2/recruitment/candidates")
                .then()
                .extract().response();

        log.debug("Get candidates response status: {}", response.statusCode());
        return response;
    }

    public Response deleteCandidate(Number candidateId) {
        log.info("Deleting candidate with ID: {}", candidateId);

        Map<String, Object> body = new HashMap<>();
        body.put("ids", Collections.singletonList(candidateId));

        Response response = given()
                .relaxedHTTPSValidation()
                .cookies(sessionCookies)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .delete(baseURL + "/web/index.php/api/v2/recruitment/candidates")
                .then()
                .extract().response();

        log.info("Delete candidate response status: {}", response.statusCode());
        return response;
    }

    public Response deleteCandidates(Collection<? extends Number> candidateIds) {
        log.info("Deleting multiple candidates with IDs: {}", candidateIds);

        Map<String, Object> body = new HashMap<>();
        body.put("ids", new ArrayList<>(candidateIds));

        Response response = given()
                .relaxedHTTPSValidation()
                .cookies(sessionCookies)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .delete(baseURL + "/web/index.php/api/v2/recruitment/candidates")
                .then()
                .extract().response();

        log.info("Delete candidates response status: {}", response.statusCode());
        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findCandidateByName(String firstName, String lastName) {
        log.info("Searching for candidate by name: {} {}", firstName, lastName);

        Response resp = getCandidates();
        List<Map<String, Object>> data = resp.jsonPath().getList("data");

        if (data == null) {
            log.warn("No candidate data returned from API");
            return null;
        }

        log.debug("Searching through {} candidates", data.size());
        for (Map<String, Object> c : data) {
            if (firstName.equals(c.get("firstName")) && lastName.equals(c.get("lastName"))) {
                log.info("Candidate found: {} {}", firstName, lastName);
                return c;
            }
        }

        log.info("Candidate not found: {} {}", firstName, lastName);
        return null;
    }
}

