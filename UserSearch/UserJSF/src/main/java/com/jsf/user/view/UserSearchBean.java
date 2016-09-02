package com.jsf.user.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.user.dao.UserFileDAO;
import com.user.model.User;
import com.util.PathUtils;

@Named
@ViewScoped
public class UserSearchBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Part userFile;
	private String searchEmail;
	private Integer inboxBegin;
	private Integer inboxEnd;
	
	private List<User> listUser = new ArrayList<>();
	private UserFileDAO userFileDao = null;
	private boolean isDisable;	
		
	@PostConstruct
	public void initialize() {
		isDisable = userFileDao == null;
	}
	
	public void userFileSelected() {
		if (userFile != null) {
			String l_path = PathUtils.getPathTemp() + File.separator + "userFileUpload";
			try (Scanner l_inputFile = new Scanner(userFile.getInputStream());					
				PrintStream l_outputFile = new PrintStream(new FileOutputStream(l_path), false);) {
				l_outputFile.print(l_inputFile.useDelimiter("\\A").next());	
				userFileDao = new UserFileDAO(l_path);
				isDisable = false;
				FacesMessage l_message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uploaded successfully", "");
				FacesContext.getCurrentInstance().addMessage(null, l_message);
			} catch (IOException e) {
				FacesMessage l_message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The file cannot be imported", "");
				FacesContext.getCurrentInstance().addMessage(null, l_message);
			}	
		}
		else {
			isDisable = true;
		}
	}
	
	public void searchEmailAction() {
		listUser = userFileDao.getUserByEmail(searchEmail);
	}
	
	public void inboxMoreAction() {
		User l_user = userFileDao.getUserMoreInbox();
		if (l_user != null) {
			listUser = Arrays.asList(l_user);
		}
	}
	public void sizeMoreAction() {
		User l_user = userFileDao.getUserMoreSize();
		if (l_user != null) {
			listUser= Arrays.asList(l_user);
		}		
	}
	
	public void inboxBetweenAction() {
		listUser = userFileDao.getUserByInboxBetween(getInboxBegin(), getInboxEnd());
	}
	
	public void setSearchEmail(String searchEmail) {
		this.searchEmail = searchEmail;
	}
	
	public String getSearchEmail() {
		return searchEmail;
	}
	
	public void setUserFile(Part userFile) {
		this.userFile = userFile;
	}
	
	public Part getUserFile() {
		return userFile;
	}
	
	public List<User> getListUser() {
		return listUser;
	}
	
	public Integer getInboxBegin() {
		return inboxBegin;
	}
	
	public void setInboxBegin(Integer inboxBegin) {
		this.inboxBegin = inboxBegin;
	}
	
	public Integer getInboxEnd() {
		return inboxEnd;
	}
	
	public void setInboxEnd(Integer inboxEnd) {
		this.inboxEnd = inboxEnd;
	}
	
	public boolean getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}
}
