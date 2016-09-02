package com.test.user.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.test.util.TestUtils;
import com.user.dao.UserFileDAO;
import com.user.model.User;

public class UserFileDAOTest {
	
	private UserFileDAO userFileDao;
	
	@Before
	public void initialize() {
		userFileDao = new UserFileDAO(TestUtils.loadFileUserTest());
	}

	@Test(expected=IllegalArgumentException.class)
	public void buildUserFileDAO() {
		new UserFileDAO("/sdocsocas");
	}
	

	@Test
	public void getUserByEmail() {
		List<User> l_list = userFileDao.getUserByEmail("dame");
		assertTrue(l_list.size() == 1);
		assertTrue(l_list.get(0).getEmail().startsWith("dame"));
	}
	
	@Test
	public void getUserMoreInbox() {
		User l_user = userFileDao.getUserMoreInbox();
		assertTrue("li_digik@uol.com.br".equals(l_user.getEmail()));
		assertTrue(l_user.getInbox() == 7);
	}
	
	@Test
	public void getUserMoreSize() {
		User l_user = userFileDao.getUserMoreSize();
		assertTrue("yyfdinny@uol.com.br".equals(l_user.getEmail()));
		assertTrue(l_user.getSize() == 10);
	}
	
	@Test
	public void getUserByInboxBetween() {
		List<User> l_list = userFileDao.getUserByInboxBetween(3, 5);
		assertTrue(l_list.size() == 1);
		assertTrue(l_list.get(0).getInbox() == 5);
		
		l_list = userFileDao.getUserByInboxBetween(3, null);
		assertTrue(l_list.size() == 2);
		
	}
}
