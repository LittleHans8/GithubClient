/**
 * Created by YuGang Yang on October 28, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package cn.littlehans.githubclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) public class ErrorModel {
  @JsonProperty("status_code") public int mStatusCode;
  @JsonProperty("message") public String mMessage;
  public String mTitle;
  public List<Errors> errors;

  public ErrorModel() {

  }

  public ErrorModel(int statusCode, String title, String msg) {
    this.mStatusCode = statusCode;
    this.mTitle = title;
    this.mMessage = msg;
  }

  public ErrorModel(int statusCode, String msg) {
    this.mStatusCode = statusCode;
    this.mMessage = msg;
  }

  public String getMessage() {
    return mMessage == null ? "" : mMessage;
  }

  public int getStatusCode() {
    return mStatusCode;
  }

  public static class Errors {
    public String resource;
    public String field;
    public String code;
  }
}
