package com.tobedone.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

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
import com.tobedone.logic.ToDoList;
import com.tobedone.logic.ToDoListImp;
import com.tobedone.taskitem.*;
import com.tobedone.taskitem.TaskItem.Status;
import com.tobedone.utils.Constants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

/**
 * @author Tian Xing
 * @version 0.5
 * @since 01-09-2013
 * 
 *        This class takes charge of Display GUI of 2BeDone
 * 
 */
public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable tasksList;
	private JScrollPane scrollPane;
	private final JLabel inputTips = new JLabel(Constants.TIP_BEGIN);
	private final JLabel feedback = new JLabel(Constants.INFO_UNFINISHED_TASKS);
	private static CommandExecuteResult executeResult;
	private int[] tableRows = new int[Constants.TABLE_ROW_MAX_COUNT];

	public static void setExecuteResult(CommandExecuteResult result) {
		executeResult = result;
	}

	/**
	 * Launch the application.
	 * 
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// Date date1 = Constants.simpleDateFormat.parse("10:00,12-11-2013");
		// Date date2 = Constants.simpleDateFormat.parse("11:00,12-11-2013");
		// TaskItem taskItem1 =new TaskItem("attend se tutorial",
		// "finished",date1, date2, 1);
		// TaskItem taskItem2 =new TaskItem("call mom", "unfinished", null,
		// date2, 3);
		// TaskItem taskItem3 =new TaskItem("call mom", "unfinished", null,
		// null, 2);
		//
		// Vector<TaskItem> tasks = new Vector<TaskItem>();
		// tasks.add(taskItem1);
		// tasks.add(taskItem2);
		// tasks.add(taskItem3);
		//
		ToDoList toDoService = new ToDoListImp();
		Vector<TaskItem> tasks = toDoService.getAllTasks();
		CommandExecuteResult result = new CommandExecuteResult(tasks,
		Constants.INFO_ALL_TASKS);
		
		setExecuteResult(result);
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

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		inputTips.setFont(new Font("Apple Braille", Font.PLAIN, 11));
		inputTips.setForeground(new Color(0x102b6a));
		inputTips.setBounds(13, 63, 424, 15);
		contentPane.add(inputTips);

		feedback.setFont(new Font("Apple Braille", Font.PLAIN, 11));
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

				if (keyCode == KeyEvent.VK_ENTER) {
					textField.setText("");
					TextUI.readUserInput(inputString);
					TextUI.executeCommands();

					display(TextUI.getCommandExecuteResult());
				}

				inputTips.setText(autoSetTips(inputString));
				if (keyCode != KeyEvent.VK_BACK_SPACE
						&& keyCode != KeyEvent.VK_ENTER) {
					textField.setText(autoSetInput(inputString));
				}
			}
		});

		textField.setBounds(10, 36, 595, 24);
		contentPane.add(textField);
		textField.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 108, 595, 240);
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
		
		display(executeResult);
	}

	protected void display(CommandExecuteResult result) {
		for (int i = 0; i < result.getAimTasks().size(); i++) {
			tableRows[i] = result.getAimTasks().get(i).getPriority();
		}
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer() {
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

	private String[] getAttributes(TaskItem task, int index) {
		String indexString = index + Constants.EMPTY_STRING;
		String description = task.getDescription();
		String status;
		String start;
		String end;

		if (task.getStatus().equals(Status.FINISHED)) {
			status = "     √";
		} else {
			status = "     -";
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
	// @author A0117215R
	private String autoSetTips(String input) {
		if (input.equals("")) {
			return Constants.TIP_BEGIN;
		} else if (input.startsWith("c")) {
			return Constants.TIP_CLEAR;
		} else if (input.startsWith("a") || input.startsWith("+")) {
			return Constants.TIP_ADD;
		} else if (input.startsWith("++")) {
			return Constants.TIP_UPDATE;
		} else if (input.startsWith("up")) {
			return Constants.TIP_UPDATE;
		} else if (input.startsWith("un")) {
			return Constants.TIP_UNDO;
		}else if (input.startsWith("s")) {
			return Constants.TIP_SEARCH + Constants.TIP_SEPRETER
					+ Constants.TIP_SYNC;
		} else if (input.startsWith("li")) {
			return Constants.TIP_LIST;
		} else if (input.startsWith("h") || input.startsWith("?")) {
			return Constants.TIP_HELP;
		} else if (input.startsWith("r")) {
			return Constants.TIP_REMOVE;
		} else {
			return Constants.TIP_WRONG;
		}
	}

	/**
	 * Automatically fill user input
	 * 
	 * @return the filled input
	 */
	// @author A0117215R
	private String autoSetInput(String input) {

		if (input.equals("")) {
			return Constants.TIP_BEGIN;
		} else if (input.equals("a")) {
			return Constants.CMD_ADD;
		} else if (input.equals("c")) {
			return Constants.CMD_CLEAR;
		} else if (input.equals("up")) {
			return Constants.CMD_UPDATE;
		} else if (input.equals("un")) {
			return Constants.CMD_UNDO;
		}else if (input.equals("se")) {
			return Constants.CMD_SEARCH;
		} else if (input.equals("sy")) {
			return Constants.CMD_SYNC;
		} else if (input.equals("l")) {
			return Constants.CMD_LIST;
		} else if (input.equals("h")) {
			return Constants.CMD_HELP;
		} else if (input.equals("r")) {
			return Constants.CMD_REMOVE;
		} else {
			return input;
		}
	}

}
