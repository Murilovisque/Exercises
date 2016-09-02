package com.swing.user.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.user.dao.UserFileDAO;
import com.user.model.User;

import net.miginfocom.swing.MigLayout;

public class FrmUserSearch {
	
	private static int WIDTH = 500;
	private static int HEIGHT = 500;

	private JFrame frmMain;
	private JPanel pnlMain;
	private JButton btnUserFile;
	private JLabel lblUserFile;
	private JLabel lblEmail;
	private JTextField tfdEmail;
	private JButton btnEmail;
	private JLabel lblMore;
	private JButton btnMoreInbox;
	private JButton btnMoreSize;
	private JLabel lblInboxBetween;
	private JTextField tfdInboxBetweenBegin;
	private JTextField tfdInboxBetweenEnd;
	private JButton btnInboxBetween;
	private JTable tblResultSearch;
	private JScrollPane scrResultSearch;
		
	private UserFileDAO userFileDAO;
	private boolean initialized = false;
	
	public void show() {
		if (!initialized) {			
			buildScreen();
			makeScreen();
			initialized = true;
			loadTblResultSearch(new ArrayList<>());
			enableFields(false);
		}
	}
	
	private void makeScreen() {
		frmMain.add(pnlMain);
		pnlMain.add(btnUserFile);
		pnlMain.add(lblUserFile, "wrap");
		pnlMain.add(lblEmail);
		pnlMain.add(tfdEmail, "split 2");
		pnlMain.add(btnEmail, "wrap");
		pnlMain.add(lblMore);
		pnlMain.add(btnMoreInbox, "split 2");
		pnlMain.add(btnMoreSize, "wrap");
		pnlMain.add(lblInboxBetween);
		pnlMain.add(tfdInboxBetweenBegin, "split 3");
		pnlMain.add(tfdInboxBetweenEnd);
		pnlMain.add(btnInboxBetween, "wrap");
		pnlMain.add(scrResultSearch, "span");
		
		frmMain.pack();
		frmMain.setSize(WIDTH, HEIGHT);
		frmMain.setVisible(true);
	}	
	
	private void buildScreen() {
		buildVisual();
		buildFrmMain();
		buildPnlMain();
		buildLblUserFile();
		buildBtnUserFile();
		buildBtnEmail();
		buildLblEmail();
		buildTfdEmail();
		buildBtnEmail();
		buildLblMore();
		buildBtnMoreInbox();
		buildBtnMoreSize();
		buildLblInboxBetween();
		buildTfdInboxBetweenBegin();
		buildTfdInboxBetweenEnd();
		buildBtnInboxBetween();
		buildTblResultSearch();
	}

	private void loadTblResultSearch(List<User> p_listUser) {
		tblResultSearch.setModel(new UserTableModel(p_listUser));
	}
	
	private void loadTblResultSearch(User p_user) {
		tblResultSearch.setModel(new UserTableModel(p_user == null ? null : Arrays.asList(p_user)));
	}	

	private void buildFrmMain() {
		frmMain = new JFrame("User Search");
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void buildPnlMain() {
		pnlMain = new JPanel(new MigLayout("", "left", "center"));
	}

	private void buildLblEmail() {
		lblEmail = new JLabel("Search email:");
	}

	private void buildTfdEmail() {
		tfdEmail = new JTextField(30);
	}

	private void buildLblMore() {
		lblMore = new JLabel("More:");
	}

	private void buildBtnMoreInbox() {
		btnMoreInbox = new JButton("Inbox");		
		btnMoreInbox.addActionListener(
				e -> loadTblResultSearch(userFileDAO.getUserMoreInbox()));
	}

	private void buildTblResultSearch() {
		tblResultSearch = new JTable();
		scrResultSearch = new JScrollPane();
		scrResultSearch.getViewport().add(tblResultSearch);
		scrResultSearch.setSize(WIDTH - 10, 300);
	}
	
	private void buildLblInboxBetween() {
		lblInboxBetween = new JLabel("Inbox between:");
	}

	private void buildTfdInboxBetweenBegin() {
		tfdInboxBetweenBegin = new JTextField(10);		
	}
	
	private void buildTfdInboxBetweenEnd(){
		tfdInboxBetweenEnd = new JTextField(10);
	}
	
	private void buildBtnMoreSize() {
		btnMoreSize = new JButton("Size");
		btnMoreSize.addActionListener(
				e -> loadTblResultSearch(userFileDAO.getUserMoreSize()));
	}
	
	private void buildBtnEmail() {
		btnEmail = new JButton("Search");
		btnEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<User> l_list = userFileDAO.getUserByEmail(tfdEmail.getText());
				if (l_list.isEmpty()) {
					JOptionPane.showMessageDialog(frmMain, "Empty result");
				} else{					
					loadTblResultSearch(l_list);				
				}				
			}
		});
				
	}

	private void buildBtnInboxBetween() {
		btnInboxBetween = new JButton("Search");
		btnInboxBetween.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String l_begin = tfdInboxBetweenBegin.getText();
					String l_end = tfdInboxBetweenEnd.getText();
					Integer l_intBegin = null;
					Integer l_intEnd = null;
					
					if (l_begin != null && !l_begin.trim().isEmpty()) {
						l_intBegin = Integer.parseInt(l_begin);
					}
					if (l_end != null && !l_end.trim().isEmpty()) {
						l_intEnd = Integer.parseInt(l_end);
					}
					loadTblResultSearch(userFileDAO.getUserByInboxBetween(l_intBegin, l_intEnd));
				} catch (NumberFormatException l_ex) {
					// TODO: Validar inglês
					JOptionPane.showMessageDialog(frmMain, "Fields inbox begin and inbox end must have only number");
				}
			}
		});
	}
	
	private void buildBtnUserFile() {
		btnUserFile = new JButton("Append");
		btnUserFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser l_chooser = new JFileChooser();
				int l_result = l_chooser.showOpenDialog(frmMain);
				if (JFileChooser.APPROVE_OPTION == l_result) {
					try {
						userFileDAO = new UserFileDAO(l_chooser.getSelectedFile().getAbsolutePath());
						lblUserFile.setText(l_chooser.getSelectedFile().getName());
						enableFields(true);
					} catch (RuntimeException l_ex) {
						JOptionPane.showMessageDialog(frmMain, l_ex.getMessage());
					}
				}
				
			}			
		});
	}
	
	private void buildLblUserFile() {
		lblUserFile = new JLabel();		
	}
	
	private void enableFields(boolean p_enable) {
		tfdEmail.setEnabled(p_enable);
		btnEmail.setEnabled(p_enable);
		btnMoreInbox.setEnabled(p_enable);
		btnMoreSize.setEnabled(p_enable);
		tfdInboxBetweenBegin.setEnabled(p_enable);
		tfdInboxBetweenEnd.setEnabled(p_enable);
		btnInboxBetween.setEnabled(p_enable);		
	}
	
	private void buildVisual() {
		try {			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException
				| InstantiationException | IllegalAccessException ex) {
			ex.printStackTrace();
		}
	}
}