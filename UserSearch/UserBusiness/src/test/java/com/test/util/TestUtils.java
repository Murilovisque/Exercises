package com.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.util.PathUtils;

public class TestUtils {

	public static String loadFileUserTest() {
		List<String> l_users = Arrays.asList("li_digik@uol.com.br inbox 07 size 04",
							"damejoxo@uol.com.br inbox 05 size 08",
							"yyfdinny@uol.com.br inbox 02 size 10");
		
		String l_file = PathUtils.getPathTemp() + File.separator + "testsUserDao";
		try (FileOutputStream l_fileOutput = new FileOutputStream(l_file, false);
				PrintStream l_output = new PrintStream(l_fileOutput)) {
			l_users.forEach(s -> l_output.println(s));
		}
		catch (IOException l_ex) {
			l_ex.printStackTrace();
		}
		return l_file;
	}
}
