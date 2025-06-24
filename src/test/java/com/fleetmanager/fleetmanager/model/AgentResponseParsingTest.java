package com.fleetmanager.fleetmanager.model;

import com.fleetmanager.fleetmanager.model.AgentResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentResponseParsingTest {

  @Test
  void shouldParseJsonIntoAgentResponse() {
    // JSON string to be tested
    String json2 = "{\"content\": {\"parts\": [{\"text\": \"Welcome to AIgentic Fleet Management!\\n\\n"
        + "To check if your car needs a service, I will transfer you to the predictive maintenance agent.\\n\"}, "
        + "{\"function_call\": {\"id\": \"adk-52e74505-7dc0-4a81-8731-85cc3ebca482\", \"args\": "
        + "{\"agent_name\": \"predictive_maintenance_agent\"}, \"name\": \"transfer_to_agent\"}}], \"role\": \"model\"}, \"usage_metadata\":"
        + " {\"candidates_token_count\": 43, \"candidates_tokens_details\": [{\"modality\": \"TEXT\", \"token_count\": 43}], \"prompt_token_count\": 354, "
        + "\"prompt_tokens_details\": [{\"modality\": \"TEXT\", \"token_count\": 354}], \"total_token_count\": 397, \"traffic_type\": \"ON_DEMAND\"}, \"invocation_id\": \"e-53049e9d-b131-4159-b810-9c28989df345\", \"author\": \"root_agent\", \"actions\": {\"state_delta\": {}, \"artifact_delta\": {}, \"requested_auth_configs\": {}}, \"long_running_tool_ids\": [], \"id\": \"Dk4tt6KI\", \"timestamp\": 1750763588.114708}";
    String json = """
        {
            "content": {
                "parts": [
                    {
                        "text": "Welcome to AIgentic Fleet Management! How may I help you today?\\n"
                    }
                ],
                "role": "model"
            },
            "usage_metadata": {
                "candidates_token_count": 16,
                "candidates_tokens_details": [
                    {
                        "modality": "TEXT",
                        "token_count": 16
                    }
                ],
                "prompt_token_count": 347,
                "prompt_tokens_details": [
                    {
                        "modality": "TEXT",
                        "token_count": 347
                    }
                ],
                "total_token_count": 363,
                "traffic_type": "ON_DEMAND"
            },
            "invocation_id": "e-9a20d637-22ee-4699-815b-e86143565337",
            "author": "root_agent",
            "actions": {
                "state_delta": {},
                "artifact_delta": {},
                "requested_auth_configs": {}
            },
            "id": "VcaFyatw",
            "timestamp": 1750677821.499728
        }
        """;

    // Parse JSON into AgentResponse object
    Gson gson = new Gson();
    AgentResponse agentResponse = gson.fromJson(json, AgentResponse.class);

    // Assertions to verify the parsed object
    assertNotNull(agentResponse);
    assertEquals("Welcome to AIgentic Fleet Management! How may I help you today?", agentResponse.getAgentResponses().get(0));

    AgentResponse agentResponse2 = gson.fromJson(json2, AgentResponse.class);
    assertNotNull(agentResponse2);


    String json3 = "{\"content\": {\"parts\": [{\"function_response\": {\"id\": \"adk-964e1d11-9b58-4555-b3d3-50806a2a000a\", \"name\": \"transfer_to_agent\", \"response\": {\"result\": null}}}], \"role\": \"user\"}, \"invocation_id\": \"e-3c7981f1-7831-4784-95f5-92f9b29c6ba4\", \"author\": \"root_agent\", \"actions\": {\"state_delta\": {}, \"artifact_delta\": {}, \"transfer_to_agent\": \"predictive_maintenance_agent\", \"requested_auth_configs\": {}}, \"id\": \"imHhWsRE\", \"timestamp\": 1750780014.748581}\n";
    String json4 = "{\"content\": {\"parts\": [{\"text\": \"What is the vehicle ID you would like to check?\\n\"}], \"role\": \"model\"}, \"usage_metadata\": {\"candidates_token_count\": 12, \"candidates_tokens_details\": [{\"modality\": \"TEXT\", \"token_count\": 12}], \"prompt_token_count\": 546, \"prompt_tokens_details\": [{\"modality\": \"TEXT\", \"token_count\": 546}], \"total_token_count\": 558, \"traffic_type\": \"ON_DEMAND\"}, \"invocation_id\": \"e-3c7981f1-7831-4784-95f5-92f9b29c6ba4\", \"author\": \"predictive_maintenance_agent\", \"actions\": {\"state_delta\": {}, \"artifact_delta\": {}, \"requested_auth_configs\": {}}, \"id\": \"FmYqFrBh\", \"timestamp\": 1750780015.000861}";

    AgentResponse agentResponse3 = gson.fromJson(json3, AgentResponse.class);
    assertNotNull(agentResponse3);
    assertEquals(agentResponse3.getAgentResponses().get(0), "AGENT ACTION: transfer to agent");

    AgentResponse agentResponse4 = gson.fromJson(json4, AgentResponse.class);
    assertNotNull(agentResponse4);
    assertEquals(agentResponse4.getAgentResponses().get(0), "What is the vehicle ID you would like to check?");

  }
}
