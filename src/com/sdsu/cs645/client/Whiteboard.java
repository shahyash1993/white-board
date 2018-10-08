package com.sdsu.cs645.client;

import com.sdsu.cs645.shared.FieldVerifier;

import javafx.scene.layout.Pane;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Whiteboard implements EntryPoint {
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";
	private final WhiteboardServiceAsync whiteboardService = GWT.create(WhiteboardService.class);

	private HTML status;
	private RichTextArea board;
	
	public void onModuleLoad() {
		status = new HTML();
		status.getElement().setId("status_msg");
		buildLogin();
	}
	
	private void buildLogin() {
		FlowPanel loginPanel = new FlowPanel();
		//loginPanel.setStyleName("log_panel");//class = log_panel
		loginPanel.getElement().setId("log_panel");//id = log_panel
		
		final PasswordTextBox passwordTextBox = new PasswordTextBox();
		loginPanel.add(new HTML("<h1>Please Enter your Password: </h1>"));
		loginPanel.add(new Label("Password"));
		loginPanel.add(passwordTextBox);
		
		FlowPanel bPanel = new FlowPanel();
		bPanel.setStyleName("blog_panel");
		
		Button loginButton = new Button("Login");
		Button clearButton = new Button("Clear");
		loginButton.setStyleName("log_button");
		clearButton.setStyleName("log_button");
		
		
		clearButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				status.setText("");
				passwordTextBox.setText("");
				passwordTextBox.setFocus(true);
			}
		});
		
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				validateLogin(passwordTextBox.getText());
				passwordTextBox.setFocus(true);
			}
		});
	    
		bPanel.add(clearButton);
		bPanel.add(loginButton);
		bPanel.add(new HTML("<br/>Code by Yash Paragkumar Shah, Spring 2018"));
		
		
		loginPanel.add(bPanel);
		loginPanel.add(status);
		
		RootPanel.get().add(loginPanel);
		passwordTextBox.setFocus(true);
	
	}//end of method
	
	private void validateLogin(String login) {
		AsyncCallback callback = new AsyncCallback() {

			public void onSuccess(Object results) {
				String answer = (String)results;
				if(answer.equals("OK")) {
					status.setText("");
					buildeMainPanel();
				}
				else {
					status.setText("Wrong Password Enterred!");
				}
			}

			public void onFailure(Throwable caught) {
				status.setText("Faialed"+caught.getMessage());
				caught.printStackTrace();
			}
		};
		whiteboardService.validateLogin(login, callback);
	}
	
	//make whiteboard
	private void buildeMainPanel() {
		FlowPanel main = new FlowPanel();
		main.add(new HTML("<h1>Online Whiteboard</h1>"));
		main.add(getButtonPanel());
		
		board = new RichTextArea();
	    
		main.add(board);
		main.add(status);
		
		
		RootPanel.get().clear();
		RootPanel.get().add(main);
		board.setFocus(true);
		main.add(new HTML("<h3>Code By Yash Paragkumar Shah, jadrn038"));
		
		loadPanel();
	}//end of buildeMainPanel
	
	private FlowPanel getButtonPanel() {
		FlowPanel p = new FlowPanel();
		
		Button clear = new Button("Clear");
		Button save = new Button("Save");
		Button load = new Button("Load");
		
		///
	 /*   PushButton saveBtn = new PushButton(
	        new Image("/save.png"));
	    PushButton loadBtn = new PushButton(
		        new Image("/load.png"));
	    PushButton clearBtn = new PushButton(
		        new Image("/clear.jpg"));
*/
	    ///
	    
		
		clear.setStyleName("my-button");
		save.setStyleName("my-button");
		load.setStyleName("my-button");
		
	/*	saveBtn.setStyleName("my-button");
		loadBtn.setStyleName("my-button");
		clearBtn.setStyleName("my-button");
*/		
		clear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				board.setHTML("");
				status.setHTML("");
			}
		});
		
		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				savePanel();
			}
		});
		
		load.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadPanel();
			}
		});
		
		p.setStyleName("button-panel");
		
		  
		p.add(clear);

/*		  p.add(saveBtn);
		  p.add(clearBtn);
		  p.add(loadBtn);
*/
		  p.add(load);
		p.add(save);
		
		return p;
	}
	
	private void savePanel() {
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object results) {
				String answer = (String)results;
				//status.setText("Answer:"+answer);
				//System.out.println("Save | Result: "+(String)results);
				if(answer.equals("OK")) {
					status.setText("Whiteboard saved!");
				}
				else {
					status.setText("Error! Whiteboard not saved!");
				}
			}

			public void onFailure(Throwable caught) {
				status.setText("Faialed"+caught.getMessage());
				caught.printStackTrace();
			}
		};
		whiteboardService.save(board.getHTML(), callback);
	}
	
	private void loadPanel() {
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object results) {
				String answer = (String)results;

				board.setHTML(answer);
				status.setText("Whiteboard Loaded from Disk.");
			}

			public void onFailure(Throwable caught) {
				status.setText("Faialed"+caught.getMessage());
				caught.printStackTrace();
			}
		};
		whiteboardService.load(callback);
	}
	
}//end of class










