package com.fleetmanager.fleetmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.stream.Collectors;


public class AgentResponse {
  private Content content;
  private String invocationId;
  private String author;
  private Actions actions;
  private String id;
  private double timestamp;

  // Getters and setters...

  public List<String> getAgentResponses() {

    return content.getParts().stream().map(part -> {
      if (part.isFunctionResponse()) {
        return "AGENT ACTION: " + part.getFunctionResponse().getName().replace("_", " ");
      } else {
        return part.getText() != null ? part.getText().trim() : "--->";
      }
    }).toList();
  }

  public static class Content {
    private List<Part> parts;
    private String role;

    // Getters and setters...
    public List<Part>getParts() {
      return parts;
    }
  }

  public static class Part {

    @SerializedName("function_response")
    private FunctionResponse functionResponse;

    private String text;


    // Getters and setters...
    public boolean isFunctionResponse() {
      return functionResponse != null;
    }

    public String getText() {
      return text;
    }

    public FunctionResponse getFunctionResponse() {
      return functionResponse;
    }
  }

  public static class FunctionResponse {
    private String id;
    private String name;
    private Response response;

    // Getters and setters...
    public String getName() {
      return name;
    }
  }

  public static class Response {
    private Object result;

    // Getters and setters...
  }

  public static class Actions {
    private Object stateDelta;
    private Object artifactDelta;
    private Object requestedAuthConfigs;

    // Getters and setters...
  }
}

/*
public class AgentResponse {

  private Content content;

  public AgentResponse(Content content) {
    this.content = content;
  }
  // - {"content":
  // {"parts": [{"text": "Welcome to AIgentic Fleet Management! How may I help you today?\n"}], "role": "model"},
  // "usage_metadata": {"candidates_token_count": 16, "candidates_tokens_details":
  // [{"modality": "TEXT", "token_count": 16}], "prompt_token_count": 347,
  // "prompt_tokens_details": [{"modality": "TEXT", "token_count": 347}],
  // "total_token_count": 363, "traffic_type": "ON_DEMAND"},
  // "invocation_id": "e-df2d355e-1bb1-4620-a5ab-0edbb3287dd0",
  // "author": "root_agent",
  // "actions": {"state_delta": {}, "artifact_delta": {}
  // , "requested_auth_configs": {}}, "id": "wZuz1LD3", "timestamp": 1750668778.313572}

  // OR

  //Welcome to AIgentic Fleet Management! To predict which of your cars needs a service, I'll transfer you to the predictive maintenance agent.
  //
  //{"content": {"parts": [{"function_response":
  // {"id": "adk-7b6d3a82-d14e-4929-aec4-b4155f90a744",
  // "name": "transfer_to_agent",
  // "response": {"result": null}}
  // }
  // ],
  // "role": "user"},
  // "invocation_id": "e-af7dc653-9a59-4704-b367-2adff9f3e752", "author": "root_agent",
  // "actions": {"state_delta": {}, "artifact_delta": {}, "transfer_to_agent": "predictive_maintenance_agent", "requested_auth_configs": {}},
  // "id": "hhOuQ9jh", "timestamp": 1750776684.092037}
  //{"content": {"parts": [{"text": "Please provide the vehicle ID so I can retrieve its health information and predict its maintenance needs.\n"}], "role": "model"}, "usage_metadata": {"candidates_token_count": 19, "candidates_tokens_details": [{"modality": "TEXT", "token_count": 19}], "prompt_token_count": 550, "prompt_tokens_details": [{"modality": "TEXT", "token_count": 550}], "total_token_count": 569, "traffic_type": "ON_DEMAND"}, "invocation_id": "e-af7dc653-9a59-4704-b367-2adff9f3e752", "author": "predictive_maintenance_agent", "actions": {"state_delta": {}, "artifact_delta": {}, "requested_auth_configs": {}}, "id": "z0PxObzt", "timestamp": 1750776684.345803}


  public String getAgentResponse() {
    //return content.getParts().get(0).getText();

    return content.getParts().stream().map(part -> {
      if (part.isFunctionResponse()) {
        return part.getFunctionResponse().getName();
      } else {
        return part.getText();
      }
    }).collect(Collectors.joining("\n"));
  }

}

class Content {
  final List<Part> parts;

  Content(List<Part> parts) {
    this.parts = parts;
  }
  public List<Part> getParts() {
    return parts;
  }
}

class FunctionResponse {
  String name;

  public FunctionResponse(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}

class Part {
  final String text;
  final String role;

  @JsonProperty("function_response")
  final FunctionResponse functionResponse;

  Part(String text, String role, FunctionResponse functionResponse) {
    this.text = text;
    this.role = role;
    this.functionResponse = functionResponse;
  }

  public FunctionResponse getFunctionResponse() {
    return functionResponse;
  }

  public boolean isFunctionResponse() {
    return functionResponse != null;
  }

  public String getText() {
    return text;
  }
}

*/
