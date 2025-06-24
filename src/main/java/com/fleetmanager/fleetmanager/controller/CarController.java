package com.fleetmanager.fleetmanager.controller;

import com.fleetmanager.fleetmanager.model.AgentResponse;
import com.fleetmanager.fleetmanager.model.Car;
import com.fleetmanager.fleetmanager.model.UserQuery;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CarController {

    public Map<String, Car> cars = new HashMap<>();

    final String projectId = "hacker2025-team-26-dev";
    final String agentUrl = "https://us-central1-aiplatform.googleapis.com/v1beta1/projects/hacker2025-team-26-dev/locations/us-central1/reasoningEngines/3657881671564263424:streamQuery";

    private void queryData() {

        if(cars.isEmpty()) {
            try {

                BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
                QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(
                            "SELECT V.Vehicle_ID, V.Make, V.Model, V.Model_Year,\n"
                                + "PM.Battery_Current, PM.SoH, PM.Distance_Traveled, PM.Battery_Voltage, PM.Failure_Probability\n"
                                + "FROM \n"
                                + "hacker2025-team-26-dev.EV_Predictive_Maintenance.VEHICLE V\n"
                                + "LEFT JOIN hacker2025-team-26-dev.EV_Predictive_Maintenance.PM\n"
                                + "ON PM.Vehicle_ID = V.Vehicle_ID\n"
                                + " LIMIT 5")
                        // Use standard SQL syntax for queries.
                        // See: https://cloud.google.com/bigquery/sql-reference/
                        .setUseLegacySql(false)
                        .build();

                JobId jobId = JobId.newBuilder().setProject(projectId).build();
                Job queryJob = bigquery.create(
                    JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

                // Wait for the query to complete.
                queryJob = queryJob.waitFor();

                // Check for errors
                if (queryJob == null) {
                    throw new RuntimeException("Job no longer exists");
                } else if (queryJob.getStatus().getExecutionErrors() != null
                    && queryJob.getStatus().getExecutionErrors().size() > 0) {
                    // TODO(developer): Handle errors here. An error here do not necessarily mean that the job
                    // has completed or was unsuccessful.
                    // For more details: https://cloud.google.com/bigquery/troubleshooting-errors
                    throw new RuntimeException("An unhandled error has occurred");
                }

                // Get the results.
                TableResult result = queryJob.getQueryResults();

                // Print all pages of the results.

                for (FieldValueList row : result.iterateAll()) {
                    System.out.println("Turning " + row + " into a car");
                    Car car = new Car(
                        row.get("Vehicle_ID").getStringValue(),
                        row.get("Make").getStringValue(),
                        row.get("Model").getStringValue(),
                        row.get("Model_Year").getNumericValue().intValue(),
                        row.get("Distance_Traveled") != null ? row.get("Distance_Traveled").getNumericValue().intValue() : 0, // Placeholder for mileage
                        row.get("Battery_Voltage") != null ? row.get("Battery_Voltage").getStringValue() : "", // Placeholder for fuel level
                        "2025-06-10", // Placeholder for next service date
                        row.get("SoH") != null ? row.get("SoH").getStringValue() : "", // Placeholder for condition
                        false, // Placeholder for recall status
                        row.get("Failure_Probability") != null ? row.get("Failure_Probability").getStringValue()+" % chance of failure" : "" // Placeholder for issues
                    );
                    cars.put(car.getLicensePlate(), car);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private String search(String query) throws IOException {

        try {
            GoogleCredentials googleCredentials = GoogleCredentials.getApplicationDefault();

            HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(googleCredentials);
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(credentialsAdapter);

            String requestBody = """
            {
                "input": {
                    "message": "%s",
                    "user_id":"sarah.saunders@capgemini.com"
                }
            }
            """;

            com.google.api.client.http.HttpRequest request =
                requestFactory.buildPostRequest(new GenericUrl(agentUrl),
                    ByteArrayContent.fromString("application/json", String.format(requestBody, query)));
            request.getHeaders().setContentType("application/json");

            JsonObjectParser parser = new JsonObjectParser(GsonFactory.getDefaultInstance());
            request.setParser(parser);
            com.google.api.client.http.HttpResponse response = request.execute();

            String responseStr = response.parseAsString();
            System.out.println("Got " + responseStr + " back from agent");
            Gson gson = new GsonBuilder().setLenient().create();
            AgentResponse agentResponse = gson.fromJson(responseStr, AgentResponse.class);
            return agentResponse.getAgentResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    private String getAgentUrl(String query) {
        try {

               return search(query);

        } catch (IOException e) {
            return "Error querying agent: " + e.getMessage();

        }
    }


    public CarController() {
        queryData();

    }

    @GetMapping("/")
    public String getDashboard(Model model) {
        model.addAttribute("cars", cars.values());
        return "index";
    }

    @GetMapping("/agent")
    public String getAgent(Model model) {
        model.addAttribute("user", new UserQuery("What do you want to ask today"));
        return "agentchat";
    }

    @PostMapping("/chat")
    public String sendChat(Model model, @RequestParam String userInput) {

        UserQuery userQuery = (UserQuery) model.getAttribute("user");
        if (userQuery == null) {
            userQuery = new UserQuery("What do you want to ask today");
        }

        String agentResponse = getAgentUrl(userInput);
        userQuery.setBody(agentResponse);

        model.addAttribute("user", userQuery);
        return "agentchat";
    }


    @GetMapping("/car")
    @ResponseBody
    public Car getCarDetails(@RequestParam String licensePlate) {
        return cars.get(licensePlate);
    }
}
