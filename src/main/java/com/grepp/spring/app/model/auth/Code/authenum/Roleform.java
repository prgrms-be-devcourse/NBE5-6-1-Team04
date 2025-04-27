package com.grepp.spring.app.model.auth.Code.authenum;

public enum Roleform {
  ADMIN("ADMIN"),
  USER("CUSTOMER");

  private String role;

  Roleform(String role) {
    this.role = role;
  }

  public String getRole(){
    return role;
  }
}
