package com.user.dao;

import static com.util.PathUtils.getPathBash;
import static java.io.File.separator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.user.model.User;


//TODO: Implementar DAO depois do arquivo de bash
public class UserFileDAO {	
	
	private static final String SEARCH_USER = "searchUser.sh";
	private static final String MAX_SIZE = "maxSize.sh";
	private static final String INBOX_BETWEEN = "inboxSearchBetween.sh";
	private static final String MAX_INBOX = "maxInbox.sh";
	
	private File fileUsers;
	
	static {
		loadBash();
	}
		
	public UserFileDAO(String p_pathFile) {
		fileUsers = Paths.get(p_pathFile).toFile();
		
		if (!fileUsers.exists() || fileUsers.isDirectory()) {
			throw new IllegalArgumentException("File not found");
		}		
	}

	public List<User> getUserByEmail(String p_email) {	
		if (null == p_email) {
			p_email = "";
		}
		return executeBash(SEARCH_USER, p_email);
	}
	
	public User getUserMoreInbox() {
		return executeBashSingleResult(MAX_INBOX);
	}
	
	public User getUserMoreSize() {
		return executeBashSingleResult(MAX_SIZE);
	}
	
	public List<User> getUserByInboxBetween(Integer p_begin, Integer p_end) {
		String l_begin = p_begin == null ? "0" : p_begin.toString();
		
		if (p_end == null) {
			return executeBash(INBOX_BETWEEN, l_begin, Integer.toString(getUserMoreInbox().getInbox()));
		}
		else {
			return executeBash(INBOX_BETWEEN, l_begin, p_end.toString());
		}
		
	}
	
	private List<User> executeBash(String p_bash, String... p_commands) {
		Scanner l_inputProcess = null;
		try {
			String[] l_finalCommand = null;
			if (p_commands == null || p_commands.length == 0) {
				l_finalCommand = new String[]{getPathBash() + separator + p_bash, 
						fileUsers.getAbsolutePath()};
			}
			else {
				l_finalCommand = new String[p_commands.length + 2];
				System.arraycopy(p_commands, 0, l_finalCommand, 2, p_commands.length);
				l_finalCommand[0] = getPathBash() + separator +  p_bash;
				l_finalCommand[1] = fileUsers.getAbsolutePath();
			}
			
			Process l_process = new ProcessBuilder(l_finalCommand).start();
			l_inputProcess = new Scanner(l_process.getInputStream());
			
			List<User> l_list = new ArrayList<>();
			while(l_inputProcess.hasNext()) {
				String l_line = l_inputProcess.nextLine();
				if (l_line != null && !l_line.trim().isEmpty()) {
					String[] l_arrUser = l_line.split(" ");
					l_list.add(new User(l_arrUser[0],
							Integer.parseInt(l_arrUser[1]), Integer.parseInt(l_arrUser[2])));
				}
			}
			l_process.waitFor();
			return l_list;
		} catch (IOException | InterruptedException | NumberFormatException e) {
			throw new RuntimeException("Cannot not possible to execute the bash" + p_bash);
			
		} finally {
			if (null != l_inputProcess)
				l_inputProcess.close();
		}
	}
	
	private List<User> executeBash(String p_bash) {
		return executeBash(p_bash,null);
	}
	
	private User executeBashSingleResult(String p_bash, String... p_commands) {
		List<User> l_list = executeBash(p_bash, p_commands);
		if (l_list.isEmpty()) {
			return null;
		}
		else {
			return l_list.get(0);
		}
	}
	
	private User executeBashSingleResult(String p_bash) {
		return executeBashSingleResult(p_bash, null);
	}	
	
	private static void loadBash() {		
		String[] l_arrBash = {SEARCH_USER,
				MAX_SIZE,
				INBOX_BETWEEN,
				MAX_INBOX};
		
		ClassLoader l_loader = UserFileDAO.class.getClassLoader(); 
		for (String l_bash : l_arrBash) {
			URL l_url = l_loader.getResource("META-INF" + "/" + "scripts" + "/" + l_bash);			
			try (FileInputStream l_fileInput = new FileInputStream(l_url.getPath().replaceAll("%", "/"));
					Scanner l_input = new Scanner(l_fileInput);
					FileOutputStream l_fileOutput = new FileOutputStream(getPathBash() + separator + l_bash, false);
					PrintStream l_output = new PrintStream(l_fileOutput)) {
				while (l_input.hasNext()) {
					l_output.println(l_input.nextLine());
				}
				Paths.get(getPathBash() + separator + l_bash).toFile().setExecutable(true);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
