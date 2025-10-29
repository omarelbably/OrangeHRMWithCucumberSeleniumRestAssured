package steps.api;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pages.api.CandidateModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CandidateSteps {
    private CandidateModule api;
    private Integer candidateId;
    private Map<String, Object> candidateData;

    @When("user logins via API with username {string} and password {string}")
    public void user_logins_via_api(String username, String password) {
        api = new CandidateModule();
        api.authenticateViaAPI(username, password);
    }

    @When("user adds a candidate via API with the following details:")
    public void user_adds_candidate_via_api(DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);
        Map<String, Object> candidate = new HashMap<>();
        candidate.put("firstName", data.get("First Name"));
        candidate.put("middleName", data.getOrDefault("Middle Name", ""));
        candidate.put("lastName", data.get("Last Name"));
        candidate.put("email", data.get("Email"));
        candidate.put("contactNumber", data.getOrDefault("Contact Number", ""));
        candidate.put("keywords", data.getOrDefault("Keywords", ""));
        candidate.put("comment", data.getOrDefault("Comment", ""));

        Response response = api.addCandidate(candidate);
        assertThat("Add candidate should be successful", response.statusCode(), is(anyOf(is(200), is(201))));

        Map<String, Object> dataNode = response.jsonPath().getMap("data");
        if (dataNode != null) {
            candidateData = dataNode;
            Object idVal = dataNode.get("id");
            if (idVal instanceof Number) {
                candidateId = ((Number) idVal).intValue();
            }
        }
    }

    @Then("the candidate should be created successfully via API")
    public void candidate_should_be_created() {
        assertThat(candidateData, is(notNullValue()));
        assertThat(candidateId, is(notNullValue()));
        assertThat(candidateId, greaterThan(0));
    }

    @When("user retrieves the candidate list via API")
    public void user_retrieves_candidate_list() {
        Response resp = api.getCandidates();
        assertThat(resp.statusCode(), is(200));
        List<Map<String, Object>> list = resp.jsonPath().getList("data");
        assertThat(list, is(notNullValue()));
    }

    @When("user finds the candidate with first name {string} and last name {string} via API")
    public void user_finds_candidate(String firstName, String lastName) {
        Map<String, Object> candidate = api.findCandidateByName(firstName, lastName);
        if (candidate != null) {
            Object idVal = candidate.get("id");
            if (idVal instanceof Number) {
                candidateId = ((Number) idVal).intValue();
            }
            candidateData = candidate;
        }
        assertThat("Candidate should be found", candidate, is(notNullValue()));
    }

    @When("user deletes the candidate via API")
    public void user_deletes_candidate() {
        assertThat("candidateId must be set before deletion", candidateId, is(notNullValue()));
        Response resp = api.deleteCandidate(candidateId);
        assertThat(resp.statusCode(), anyOf(is(200), is(204)));
    }

    @Then("the candidate should be deleted successfully via API")
    public void candidate_deleted_successfully() {
        Response resp = api.getCandidates();
        List<Map<String, Object>> list = resp.jsonPath().getList("data");
        Map<String, Object> stillExists = null;
        if (list != null) {
            for (Map<String, Object> c : list) {
                if (c != null && c.get("id") instanceof Number && ((Number) c.get("id")).intValue() == candidateId) {
                    stillExists = c; break;
                }
            }
        }
        assertThat(stillExists, is(nullValue()));
    }

    @Then("the candidate with first name {string} and last name {string} should not exist in the system")
    public void candidate_should_not_exist(String firstName, String lastName) {
        Map<String, Object> candidate = api.findCandidateByName(firstName, lastName);
        assertThat(candidate, is(nullValue()));
    }
}
