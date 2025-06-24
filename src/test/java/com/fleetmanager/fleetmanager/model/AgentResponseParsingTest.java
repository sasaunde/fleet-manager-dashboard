package com.fleetmanager.fleetmanager.model;

import com.fleetmanager.fleetmanager.model.AgentResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentResponseParsingTest {

  @Test
  void shouldParseJsonIntoAgentResponse() {
    // JSON string to be tested
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
    assertEquals("Welcome to AIgentic Fleet Management! How may I help you today?\n", agentResponse.getAgentResponse());

  }
}
