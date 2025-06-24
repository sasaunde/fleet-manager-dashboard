package com.fleetmanager.fleetmanager.model;

public class UserQuery {

  private final String prompt;

  private String body;

  public UserQuery(String prompt, String body) {
    this.prompt = prompt;
    this.body = body;
  }

  public UserQuery(String prompt) {
    this.prompt = prompt;
  }

  public String getPrompt() {
    return prompt;
  }

  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }
}
