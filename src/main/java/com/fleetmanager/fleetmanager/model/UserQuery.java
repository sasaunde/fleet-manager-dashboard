package com.fleetmanager.fleetmanager.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserQuery {

  private final String prompt;

  private List<String> responses = new ArrayList<>();

  private List<String> actions = new ArrayList<>();

  public UserQuery(String prompt,  List<String> body) {
    this.prompt = prompt;
    this.responses = body;
  }

  public UserQuery(String prompt) {
    this.prompt = prompt;
  }

  public String getPrompt() {
    return prompt;
  }

  public void addAction(String action) {
    this.actions.add(action);
  }

  public List<String> getActions() {
    return actions;
  }
  public  List<String> getResponses() {
    return responses;
  }
  public void setResponse(String body) {

    List<String> tmpList = new ArrayList<>(this.responses);
    tmpList.add(body);
    this.responses = Collections.unmodifiableList(tmpList);
  }

  public void setResponses(List<String> responses) {
    this.responses = responses;
  }
}
