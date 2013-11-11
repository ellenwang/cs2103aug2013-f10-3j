package com.tobedone.parser;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author A0117215R
 * @version 0.5
 * @since 01-09-2013
 * 
 */
public class ParserAtd {
	
	Parser parser = Parser.getInstance();
	String testString1 = "add goto school";
	String testString2 = "+ goto school";
	String testString3 = "remove 11";
	String testString4 = "add";
	String testString5 = "xyz";
	
	//@author A0117215R
	//test extractCommandType() method in Parser
	@Test
	public void testExtractCommandType() {	
		assertEquals("add",parser.extractCommandType(testString1));
		assertEquals("+",parser.extractCommandType(testString2));
		assertEquals("remove",parser.extractCommandType(testString3));
		assertEquals("add",parser.extractCommandType(testString4));
	}
	
	
	//@author A0117215R
	//test extractCommandType() method in Parser
	@Test
	public void testExtractCommandParas() {	
		assertEquals("goto school",parser.extractCommandParas(testString1));
		assertEquals("goto school",parser.extractCommandParas(testString2));
		assertEquals("11",parser.extractCommandParas(testString3));
		assertEquals(null,parser.extractCommandParas(testString4));
	}
}
