package com.fleetmanager.fleetmanager.model;

import java.util.List;

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

  public String getAgentResponse() {
    return content.getParts().get(0).getText();
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

class Part {
  final String text;
  final String role;

  Part(String text, String role) {
    this.text = text;
    this.role = role;
  }

  public String getText() {
    return text;
  }
}

