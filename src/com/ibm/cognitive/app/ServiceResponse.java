package com.ibm.cognitive.app;

public class ServiceResponse {
	
	private boolean success;
	private String status;
	private Object result;
	
	
	/**
	 * @param success
	 * @param status
	 * @param result
	 */
	public ServiceResponse(boolean success, String status, Object result) {
		super();
		this.success = success;
		this.status = status;
		this.result = result;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	} 
	
	

}
