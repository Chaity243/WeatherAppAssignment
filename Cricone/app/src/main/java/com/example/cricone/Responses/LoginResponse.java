package com.example.cricone.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("api_status")
	@Expose
	private String apiStatus;
	@SerializedName("api_text")
	@Expose
	private String apiText;
	@SerializedName("api_version")
	@Expose
	private String apiVersion;
	@SerializedName("user_id")
	@Expose
	private Boolean userId;
	@SerializedName("messages")
	@Expose
	private Messages messages;
	@SerializedName("timezone")
	@Expose
	private String timezone;

	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	public String getApiText() {
		return apiText;
	}

	public void setApiText(String apiText) {
		this.apiText = apiText;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public Boolean getUserId() {
		return userId;
	}

	public void setUserId(Boolean userId) {
		this.userId = userId;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}