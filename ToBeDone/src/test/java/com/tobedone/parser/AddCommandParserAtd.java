package com.tobedone.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tobedone.utils.Constants;

/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 */
public class AddCommandParserAtd {

	AddCommandParser parser = new AddCommandParser();
	String testString1 = "hello by today";
	String testString2 = "hello guys h";
	String testString3 = "hello from home to shcool from 11pm this fri to 14:00 1-12";
	String testString4 = "by today";
	
	//@author A0117215R
	//test parse(String paraString) method in AddCommandParser
	@Test
	public void testParse() throws Exception {
		parser.parse(testString1);
		assertEquals("hello ", parser.getDescription());
		assertEquals(null, parser.getStartTime());
		assertEquals(null, parser.getEndTime());
		assertEquals("Mon Nov 11 23:59:00 SGT 2013", parser.getDeadline().toString());
		assertEquals(2, parser.getPriority());
		
		parser.restart();
		
		parser.parse(testString2);
		assertEquals("hello guys ", parser.getDescription());
		assertEquals(null, parser.getStartTime());
		assertEquals(null, parser.getEndTime());
		assertEquals(null, parser.getDeadline());
		assertEquals(3, parser.getPriority());
		
		parser.restart();
		
		parser.parse(testString3);
		assertEquals("hello from home to shcool ", parser.getDescription());
		assertEquals("Fri Nov 15 23:00:00 SGT 2013", parser.getStartTime().toString());
		assertEquals("Sun Dec 01 14:00:00 SGT 2013", parser.getEndTime().toString());
		assertEquals(null, parser.getDeadline());
		assertEquals(2, parser.getPriority());
		
		parser.restart();
		
		//the description is null which should throws 
		try {
			parser.parse(testString4);
		} catch (Exception e) {
			assertEquals(Constants.MSG_ERROR_EMPTY_DESCRIPTION, e.getMessage());
		}
	}

}
