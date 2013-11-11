package com.tobedone.parser.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 */
public class CommandParserAtd {
	
	CommandParser parser = CommandParser.getInstance();
	String testString1 = "add";
	String testString2 = "-";
	String testString3 = "xyz";
	
	String index1 = "test";
	String index2 = "11";
	String index3 = "2";
	
	String priority1 = "h";
	String priority2 = "low";
	
	String date1 = "by today";
	String date2 = "from 9pm this fri";

	//@author A0117215R
	//test getCommandParser() method in Parser
	@Test
	public void testGetCommandParser() throws Exception {	
		assertEquals("add",parser.getCommandParser(testString1).toString());
		assertEquals("remove",parser.getCommandParser(testString2).toString());
		
		try {
			parser.getCommandParser(testString3);
		} catch (Exception e) {
			assertEquals(Constants.MSG_ERROR_INVALID_COMMAND,e.getMessage());
		}
		
	}
	
	//@author A0117215R
	//test parseIndex() method in Parser
	@Test
	public void testParseIndex() throws Exception {
		try {
			parser.parseIndex(4, index1);
		} catch (Exception e) {
			assertEquals(Constants.MSG_ERROR_INVALID_ARGUMENT_INDEX, e.getMessage());
		}
	
		parser.parseIndex(2, index2);
		parser.parseIndex(1, index3);
	}
	
	//@author A0117215R
	//test parsePriority() method in Parser
	@Test
	public void testParsePriority() {
		assertEquals(3, parser.parsePriority(priority1));
		assertEquals(1, parser.parsePriority(priority2));
	}
	
	//@author A0117215R
	//test parseDate() method in Parser
	@Test
	public void testParseDate() throws Exception {
		assertEquals("Mon Nov 11 00:01:00 SGT 2013", parser.parseDate(date1, true).toString());
		assertEquals("Fri Nov 15 21:00:00 SGT 2013", parser.parseDate(date2, true).toString());
	}
	
	
}
