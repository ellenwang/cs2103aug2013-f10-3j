package com.tobedone.command;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author A0104167M
 * @version 0.5
 * @since 6-11-2012
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ AddCommandTest.class, ClearCommandTest.class,
		DoCommandTest.class, OverdueCommandTest.class, RemoveCommandTest.class })
// @author A0104167M
public class AllCommandBasicTests {
}
