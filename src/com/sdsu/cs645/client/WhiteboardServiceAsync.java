package com.sdsu.cs645.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>WhiteboardService</code>.
 */
public interface WhiteboardServiceAsync {
	void validateLogin(String input, AsyncCallback<String> callback) 
			throws IllegalArgumentException;
	void save(String input, AsyncCallback<String> callback) 
			throws IllegalArgumentException;
	void load(AsyncCallback<String> callback) 
			throws IllegalArgumentException;
	
}
