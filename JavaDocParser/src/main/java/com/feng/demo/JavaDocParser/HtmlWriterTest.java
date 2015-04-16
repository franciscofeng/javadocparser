package com.feng.demo.JavaDocParser;

import junit.framework.TestCase;

public class HtmlWriterTest extends TestCase
{
	HtmlWriter writer = null;
	protected void setUp() throws Exception
	{
		super.setUp();
		writer = new HtmlWriter();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testWrite2html()
	{
		fail("Not yet implemented");
	}

	public void testGetFileName()
	{
		String packageName = "java.lang.Test";
		String version = "1.1";
		assertEquals("/Users/feng/dev/javadocs/1.1/java/lang/Test.html", writer.getFileName(packageName, version));
		
	}

}
