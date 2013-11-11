package com.tobedone.ui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.tobedone.utils.Constants;

import java.awt.Color;

public class HelpFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelpFrame() {
		setTitle("2BeDone");
		Image icon = Toolkit.getDefaultToolkit().getImage(
				".\\src\\main\\resources\\icon.jpg");
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

		// Add
		JPanel helpAdd = new JPanel();
		helpAdd.setBackground(Color.WHITE);
		tabbedPane.addTab("Add", null, helpAdd, null);
		helpAdd.setLayout(null);

		JTextArea add = new JTextArea(17, 45);
		add.setBounds(0, 0, 549, 272);
		add.setEditable(false);
		helpAdd.add(add);
		add.setText(Constants.HELP_ADD);

		// Sync
		JPanel helpSync = new JPanel();
		tabbedPane.addTab("Sync", null, helpSync, null);
		helpSync.setLayout(null);

		JTextArea sync = new JTextArea(17, 45);
		sync.setBounds(0, 0, 549, 272);
		sync.setEditable(false);
		helpSync.add(sync);
		sync.setText(Constants.HELP_SYNC);

		// Undo/Redo
		JPanel helpUndoUndoRedo = new JPanel();
		helpUndoUndoRedo.setBackground(Color.WHITE);
		tabbedPane.addTab("Undo/Redo", null, helpUndoUndoRedo, null);
		helpUndoUndoRedo.setLayout(null);

		JTextArea urdo = new JTextArea(17, 45);
		urdo.setBounds(0, 0, 549, 272);
		urdo.setEditable(false);
		helpUndoUndoRedo.add(urdo);
		urdo.setText(Constants.HELP_URDO);

		// Time
		JPanel timeFormat = new JPanel();
		timeFormat.setBackground(Color.WHITE);
		tabbedPane.addTab("Time Format", null, timeFormat, null);
		timeFormat.setLayout(null);

		JTextArea timeformat = new JTextArea(17, 45);
		timeformat.setBounds(0, 0, 549, 272);
		timeformat.setEditable(false);
		timeFormat.add(timeformat);
		timeformat.setText(Constants.HELP_TIME);

		// others
		JPanel helpOther = new JPanel();
		helpOther.setBackground(Color.WHITE);
		tabbedPane.addTab("Others", null, helpOther, "do nothing");
		helpOther.setLayout(null);

		JTextArea other = new JTextArea(17, 45);
		other.setBounds(0, 0, 549, 272);
		other.setEditable(false);
		helpOther.add(other);
		other.setText(Constants.HELP_OTHER);

	}
}
