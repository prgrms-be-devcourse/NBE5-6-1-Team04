package com.grepp.spring.app.controller.web.auth.form;

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
