package com.tobedone.ui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class HelpFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelpFrame() {
		setTitle("2BeDone");
		Image icon = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\resources\\icon.jpg");   
		setIconImage(icon);
		setVisible(true);
		setBounds(710, 110, 582, 364);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(6, 6, 570, 320);
		contentPane.add(tabbedPane);
		
		//Add
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Add", null, panel_1, null);
		panel_1.setLayout(null);
		
		JTextArea textArea_1 = new JTextArea(17,45);
		textArea_1.setBounds(0, 0, 549, 272);
		textArea_1.setEditable(false);
		panel_1.add(textArea_1);
		
		//Update
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Update", null, panel_2, null);
		panel_2.setLayout(null);
		
		JTextArea textArea_2 = new JTextArea(17,45);
		textArea_2.setBounds(0, 0, 549, 272);
		textArea_2.setEditable(false);
		panel_2.add(textArea_2);
		
		//Sync
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Sync", null, panel_2, null);
		panel_3.setLayout(null);
		
		JTextArea textArea_3 = new JTextArea(17,45);
		textArea_3.setBounds(0, 0, 549, 272);
		textArea_3.setEditable(false);
		panel_3.add(textArea_3);
		
		//Undo/Redo
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		tabbedPane.addTab("Undo/Redo", null, panel_4, null);
		panel_4.setLayout(null);
		
		JTextArea textArea_4 = new JTextArea(17,45);
		textArea_4.setBounds(0, 0, 549, 272);
		textArea_4.setEditable(false);
		panel_4.add(textArea_4);
		
		//Time
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		tabbedPane.addTab("Time Format", null, panel_5, null);
		panel_5.setLayout(null);
		
		JTextArea textArea_5 = new JTextArea(17,45);
		textArea_5.setBounds(0, 0, 549, 272);
		textArea_5.setEditable(false);
		panel_5.add(textArea_5);
		
		//others
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		tabbedPane.addTab("Others", null, panel_6, "do nothing");
		panel_6.setLayout(null);
		
		JTextArea textArea_6 = new JTextArea(17,45);
		textArea_6.setBounds(0, 0, 549, 272);
		textArea_6.setEditable(false);
		panel_6.add(textArea_6);
		
	}
}
