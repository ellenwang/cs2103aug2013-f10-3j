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
public class ListCommandParserAtd {

	ListCommandParser parser = new ListCommandParser();
	String testString2 = "abc";
	String testString3 = null;
	
	//@author A0117215R
	//test parse(String paraString) method in AddCommandParser
	@Test
	public void testParse() throws Exception {
		try {
			parser.parse(testString2);
		} catch (Exception e) {
			assertEquals(Constants.MSG_ERROR_INVALID_ARGUMENT, e.getMessage());
		}
		
		try {
			parser.parse(testString3);
		} catch (Exception e) {
			assertEquals(Constants.MSG_ERROR_EMPTY_ARGUMENT, e.getMessage());
		}	
	}

}
