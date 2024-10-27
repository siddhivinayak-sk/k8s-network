package com.digistore.product.model;

import java.io.Serializable;

/**
 * 
 * @author kumar-sand
 *
 * @param <T>
 */
public class ResponseDTO<T extends Object> implements Serializable {

	private static final long serialVersionUID = 0l;
	
	private T data;
	private String responseMessage;
	private String responseCode;
	
	public ResponseDTO() {
		super();
	}

	public ResponseDTO(T data, String responseMessage, String responseCode) {
		super();
		this.data = data;
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
}
