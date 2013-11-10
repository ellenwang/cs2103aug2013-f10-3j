package com.tobedone.parser.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParserTest {
	
	String test1 = "by 9am today" ;
	CommandParser parser = CommandParser.getInstance();
	
	@Test
	public void testParseDate() throws Exception {
		parser.parseDate(test1, false);
		fail("Not yet implemented");
	}

}
