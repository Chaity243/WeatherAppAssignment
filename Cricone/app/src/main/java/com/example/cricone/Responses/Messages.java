package com.example.cricone.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messages {

	@SerializedName("respond_message_1")
	@Expose
	private String respondMessage1;

	public String getRespondMessage1() {
		return respondMessage1;
	}

	public void setRespondMessage1(String respondMessage1) {
		this.respondMessage1 = respondMessage1;
	}

}