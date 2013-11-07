package com.tobedone.parser.utils;

import java.util.HashMap;
import java.util.Map;

import com.tobedone.utils.Constants;

public class CommandParseMap {
	private static CommandParseMap commandParseMap;
	private Map<String, String> commandmap = new HashMap<String, String>();
	
	private CommandParseMap(){
		//create
		commandmap.put(Constants.CMD_ADD, Constants.PARSER_CLA_NAME_ADD);
		commandmap.put(Constants.CMD_SHORTCUT_ADD,Constants.PARSER_CLA_NAME_ADD);
		//update
		commandmap.put(Constants.CMD_UPDATE, Constants.PARSER_CLA_NAME_UPDATE);
		commandmap.put(Constants.CMD_SHORTCUT_UPDATE, Constants.PARSER_CLA_NAME_UPDATE);
		//delete
		commandmap.put(Constants.CMD_REMOVE, Constants.PARSER_CLA_NAME_REMOVE);
		commandmap.put(Constants.CMD_SHORTCUT_REMOVE, Constants.PARSER_CLA_NAME_REMOVE);
		//finish
		commandmap.put(Constants.CMD_FINISH, Constants.PARSER_CLA_NAME_FINISH);
		commandmap.put(Constants.CMD_SHORTCUT_FINISH, Constants.PARSER_CLA_NAME_FINISH);
		//List
		commandmap.put(Constants.CMD_LIST, Constants.PARSER_CLA_NAME_LIST);
		commandmap.put(Constants.CMD_SHORTCUT_LIST_ALL, Constants.PARSER_CLA_NAME_LSA);
		commandmap.put(Constants.CMD_SHORTCUT_LIST_UNFINISHED, Constants.PARSER_CLA_NAME_LSU);
		commandmap.put(Constants.CMD_SHORTCUT_LIST_FINISHED, Constants.PARSER_CLA_NAME_LSF);
		//search
		commandmap.put(Constants.CMD_SEARCH, Constants.PARSER_CLA_NAME_SEARCH);
		commandmap.put(Constants.CMD_SHORTCUT_SEARCH, Constants.PARSER_CLA_NAME_SEARCH);
		//undo
		commandmap.put(Constants.CMD_UNDO, Constants.PARSER_CLA_NAME_UNDO);
		commandmap.put(Constants.CMD_SHORTCUT_UNDO, Constants.PARSER_CLA_NAME_UNDO);
		//view
		commandmap.put(Constants.CMD_VIEW, Constants.PARSER_CLA_NAME_VIEW);
		commandmap.put(Constants.CMD_SHORTCUT_VIEW, Constants.PARSER_CLA_NAME_VIEW);
		//help
		commandmap.put(Constants.CMD_HELP, Constants.PARSER_CLA_NAME_HELP);
		commandmap.put(Constants.CMD_SHORTCUT_HELP, Constants.PARSER_CLA_NAME_HELP);
		//clear
		commandmap.put(Constants.CMD_CLEAR, Constants.PARSER_CLA_NAME_CLEAR);
		commandmap.put(Constants.CMD_SHORTCUT_CLEAR, Constants.PARSER_CLA_NAME_CLEAR);
		//sync
		commandmap.put(Constants.CMD_SYNC, Constants.PARSER_CLA_NAME_SYNC);
		commandmap.put(Constants.CMD_SHORTCUT_SYNC, Constants.PARSER_CLA_NAME_SYNC);	
	}
	
	/**
	 * implement the Singleton pattern
	 * 
	 * @return the CommandParseMap instance
	 */
	// @author A0117215R
	protected static CommandParseMap getInstance() {
		if (commandParseMap == null) {
			commandParseMap = new CommandParseMap();
		}
		return commandParseMap;
	}
	
	
	protected Map<String,String> getMap(){
		return commandmap;
	}
	
}
