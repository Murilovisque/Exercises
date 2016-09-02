package com.util;

import static java.io.File.separator;

import java.io.File;
import java.nio.file.Paths;

public final class PathUtils {
	
	private static String PATH_PROJECT;
	private static String PATH_BASH;
	private static String PATH_TEMP;

	static {
		loadPathProject();
		loadPathBash();
		loadPathTemp();
	}
	
	public static String getPathBash() {
		return PATH_BASH;
	}
	
	public static String getPathTemp() {
		return PATH_TEMP;
	}
	
	private static void loadPathTemp() {
		File l_user = Paths.get(PATH_PROJECT + File.separator + "temp").toFile();
		if (!l_user.exists()) {
			l_user.mkdir();
		}
		PATH_TEMP = l_user.getAbsolutePath();
		
	}

	private static void loadPathBash() {
		File l_user = Paths.get(PATH_PROJECT + File.separator + "scripts").toFile();
		if (!l_user.exists()) {
			l_user.mkdir();
		}
		PATH_BASH = l_user.getAbsolutePath();	
	}

	private static void loadPathProject() {
		String l_path = System.getenv("HOME");
		if (l_path == null || l_path.trim().isEmpty()) {
			l_path = System.getenv("user.dir");
			if (l_path == null || l_path.trim().isEmpty()) {
				l_path = "";
			}			
		}
		File l_user = Paths.get(l_path + separator + ".ExamBash").toFile();
		if (!l_user.exists()) {
			l_user.mkdir();
		}
		PATH_PROJECT = l_user.getAbsolutePath();		
	}
}
