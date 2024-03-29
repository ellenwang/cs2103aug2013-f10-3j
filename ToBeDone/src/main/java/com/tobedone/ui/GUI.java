package com.tobedone.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.BasicConfigurator;

import com.tobedone.logic.CommandExecuteResult;
import com.tobedone.taskitem.*;
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.utils.Constants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class takes charge of Display GUI of 2BeDone
 * 
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable tasksList;
	private JScrollPane scrollPane;
	private final JLabel inputTips = new JLabel(Constants.TIP_BEGIN);
	private final JLabel feedback = new JLabel(Constants.INFO_UNFINISHED_TASKS);
	private int[] tableRows = new int[Constants.TABLE_ROW_MAX_COUNT];

	// A0117215R
	/**
	 * Launch the application.
	 * 
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		BasicConfigurator.configure();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// @author A0117215R
	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle(Constants.TOBEDONE);
		Image icon = Toolkit.getDefaultToolkit().getImage(
				".\\src\\main\\resources\\icon.jpg");
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 380);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		inputTips.setFont(new Font("Arial", Font.PLAIN, 11));
		inputTips.setForeground(new Color(0x102b6a));
		inputTips.setBounds(13, 63, 424, 15);
		contentPane.add(inputTips);

		feedback.setFont(new Font("Arial", Font.PLAIN, 11));
		feedback.setForeground(new Color(0x102b6a));
		feedback.setBounds(13, 90, 424, 15);
		contentPane.add(feedback);

		textField = new JTextField();
		textField.setForeground(Color.DARK_GRAY);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = Constants.INPUT_EMPTY;
				try {
					keyCode = e.getKeyCode();
				} catch (Exception e1) {
					System.err.println();
				}

				String inputString = textField.getText();

				// when user press enter, pass the input string to TextUI
				if (keyCode == KeyEvent.VK_ENTER) {
					if (inputString.equals(Constants.CMD_HELP)
							|| inputString.equals(Constants.CMD_SHORTCUT_HELP)) {
						new HelpFrame();
					} else {
						inputString = inputString.trim();
						// clear the input filed
						textField.setText(Constants.EMPTY_STRING);

						TextUI.readUserInput(inputString);
						try {
							TextUI.executeCommands();
							display(TextUI.getCommandExecuteResult());
						} catch (Exception e2) {
							feedback.setText(e2.getMessage());
						}
					}
				}

				// automatically set the tips and input field by analyzing the
				// user input
				inputTips.setText(autoSetTips(inputString));
				if (keyCode != KeyEvent.VK_BACK_SPACE
						&& keyCode != KeyEvent.VK_ENTER
						&& keyCode != KeyEvent.VK_SHIFT
						&& keyCode != KeyEvent.VK_UP
						&& keyCode != KeyEvent.VK_DOWN
						&& keyCode != KeyEvent.VK_LEFT
						&& keyCode != KeyEvent.VK_RIGHT
						&& keyCode != KeyEvent.VK_CONTROL) {
					textField.setText(autoSetInput(inputString));
				}
			}
		});

		textField.setBounds(10, 36, 595, 24);
		contentPane.add(textField);
		textField.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 108, 600, 240);
		contentPane.add(scrollPane);

		tasksList = new JTable();
		tasksList.setShowVerticalLines(false);
		tasksList.setShowHorizontalLines(false);
		tasksList.setRowSelectionAllowed(false);
		tasksList.setShowGrid(false);
		scrollPane.setViewportView(tasksList);
		tasksList.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Index", "Status", "Description",
						"Start Time/Date", "End Time/Date" }));
		tasksList.getColumnModel().getColumn(0).setPreferredWidth(45);
		tasksList.getColumnModel().getColumn(0).setMinWidth(45);
		tasksList.getColumnModel().getColumn(0).setMaxWidth(45);
		tasksList.getColumnModel().getColumn(1).setPreferredWidth(46);
		tasksList.getColumnModel().getColumn(1).setMinWidth(46);
		tasksList.getColumnModel().getColumn(1).setMaxWidth(46);
		tasksList.getColumnModel().getColumn(2).setPreferredWidth(250);
		tasksList.getColumnModel().getColumn(2).setMinWidth(250);
		tasksList.getColumnModel().getColumn(2).setMaxWidth(250);
		tasksList.getColumnModel().getColumn(3).setPreferredWidth(127);
		tasksList.getColumnModel().getColumn(3).setMinWidth(127);
		tasksList.getColumnModel().getColumn(3).setMaxWidth(127);
		tasksList.getColumnModel().getColumn(4).setPreferredWidth(127);
		tasksList.getColumnModel().getColumn(4).setMinWidth(127);
		tasksList.getColumnModel().getColumn(4).setMaxWidth(127);
		tasksList.setRowHeight(30);

		try {
			TextUI.setCommandString(Constants.CMD_SHORTCUT_LIST_UNFINISHED);
			TextUI.executeCommands();
		} catch (Exception e1) {
			feedback.setText(e1.toString());
		}
		display(TextUI.getCommandExecuteResult());

	}

	// @author A0117215R
	/**
	 * this method determines the displaying format of taskItem list
	 */
	protected void display(CommandExecuteResult result) {
		for (int i = 0; i < result.getAimTasks().size(); i++) {
			tableRows[i] = result.getAimTasks().get(i).getPriority();
		}
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (tableRows[row] == 1) {
					setForeground(new Color(0x1a2933));
				}
				if (tableRows[row] == 2) {
					setForeground(new Color(0x87481f));
				}
				if (tableRows[row] == 3) {
					setForeground(new Color(0xb22c46));
				}
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);

			}
		};

		DefaultTableModel tableModel = (DefaultTableModel) tasksList.getModel();
		tableModel.setRowCount(0);
		feedback.setText(result.getFeedback());
		for (int i = 0; i < result.getAimTasks().size(); i++) {
			tableModel.addRow(getAttributes(result.getAimTasks().get(i), i));
		}

		tasksList.getColumnModel().getColumn(0).setCellRenderer(dtc);
		tasksList.getColumnModel().getColumn(1).setCellRenderer(dtc);
		tasksList.getColumnModel().getColumn(2).setCellRenderer(dtc);
		tasksList.getColumnModel().getColumn(3).setCellRenderer(dtc);
		tasksList.getColumnModel().getColumn(4).setCellRenderer(dtc);
	}

	// @author A0117215R
	/**
	 * this method determines the format of attributes of a task
	 */
	private String[] getAttributes(TaskItem task, int index) {
		String indexString = Constants.INDEX_PREFIX + index
				+ Constants.EMPTY_STRING;
		String description = task.getDescription();
		String status;
		String start;
		String end;

		// displaying format of status
		if (task.getStatus().equals(Status.FINISHED)) {
			status = Constants.STATUS_FINISHED;
		} else {
			status = Constants.STATUS_UNFINISHED;
		}

		if (task instanceof TimedTask) {
			start = Constants.simpleDateFormat.format(((TimedTask) task)
					.getStartTime());
			end = Constants.simpleDateFormat.format(((TimedTask) task)
					.getEndTime());
		} else if (task instanceof DeadlinedTask) {
			start = Constants.EMPTY_STRING;
			end = Constants.simpleDateFormat.format(((DeadlinedTask) task)
					.getEndTime());
		} else {
			start = Constants.EMPTY_STRING;
			end = Constants.EMPTY_STRING;
		}
		return new String[] { indexString, status, description, start, end };
	}

	/**
	 * generate tips according to user input
	 * 
	 * @return the tips
	 */
	private String autoSetTips(String inputString) {
		String input = inputString.toLowerCase();

		if (input.equals("")) {
			return Constants.TIP_BEGIN;
		} else if (input.startsWith("c")) {
			return Constants.TIP_CLEAR;
		} else if (input.startsWith("a") || input.startsWith("+")) {
			return Constants.TIP_ADD;
		} else if (input.startsWith("++")) {
			return Constants.TIP_UPDATE;
		} else if (input.startsWith("u")) {
			return Constants.TIP_UNDO + Constants.TIP_SEPRETER
					+ Constants.TIP_UPDATE;
		} else if (input.startsWith("upd")) {
			return Constants.TIP_UPDATE;
		} else if (input.startsWith("un")) {
			return Constants.TIP_UNDO;
		} else if (input.startsWith("s")) {
			return Constants.TIP_SEARCH;
		} else if (input.startsWith("f")) {
			return Constants.TIP_FINISH;
		} else if (input.startsWith("upl")) {
			return Constants.TIP_UPLOAD;
		} else if (input.startsWith("d")) {
			return Constants.TIP_DOWNLOAD;
		} else if (input.startsWith("l")) {
			return Constants.TIP_LIST;
		} else if (input.startsWith("h") || input.startsWith("?")) {
			return Constants.TIP_HELP;
		} else if (input.startsWith("r")) {
			return Constants.TIP_REMOVE + Constants.TIP_SEPRETER
					+ Constants.TIP_REDO;
		} else {
			return Constants.TIP_WRONG;
		}
	}

	/**
	 * Automatically fill user input
	 * 
	 * @return the filled input
	 */
	private String autoSetInput(String inputString) {

		String input = inputString.toLowerCase();

		if (input.equals("")) {
			return Constants.TIP_BEGIN;
		} else if (input.equals("a")) {
			return Constants.CMD_ADD;
		} else if (input.equals("c")) {
			return Constants.CMD_CLEAR;
		} else if (input.equals("upd")) {
			return Constants.CMD_UPDATE;
		} else if (input.equals("un")) {
			return Constants.CMD_UNDO;
		} else if (input.equals("s")) {
			return Constants.CMD_SEARCH;
		} else if (input.equals("f")) {
			return Constants.CMD_FINISH;
		} else if (input.startsWith("upl")) {
			return Constants.CMD_UPLOAD;
		} else if (input.startsWith("d")) {
			return Constants.CMD_DOWNLOAD;
		} else if (input.equals("li")) {
			return Constants.CMD_LIST;
		} else if (input.equals("h")) {
			return Constants.CMD_HELP;
		} else if (input.equals("rem")) {
			return Constants.CMD_REMOVE;
		} else {
			return inputString;
		}
	}

}
