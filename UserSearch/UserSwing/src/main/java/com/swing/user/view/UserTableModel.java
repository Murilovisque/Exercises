package com.swing.user.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.user.model.User;

public class UserTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private List<User> listUser;
	
	public UserTableModel(List<User> p_listUser) {
		listUser = p_listUser == null ? new ArrayList<>() : p_listUser;
	}

	@Override
	public int getRowCount() {
		return listUser.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User l_user = listUser.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return l_user.getEmail();
			case 1:
				return l_user.getInbox();
			case 2:
				return l_user.getSize();
			
			default:
				return "";
		}
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Email";
			case 1:
				return "Inboox";
			case 2:
				return "Size";
			
			default:
				return "";
		}
	}

}
