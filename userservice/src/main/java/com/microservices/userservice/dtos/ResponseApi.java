package com.microservices.userservice.dtos;

public class ResponseApi {
    private boolean isSuccessful;
    private String message;
    
	public ResponseApi() {
		super();
	}
    
	public ResponseApi(boolean isSuccessful, String message) {
		super();
		this.isSuccessful = isSuccessful;
		this.message = message;
	}
	
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
