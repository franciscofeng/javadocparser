package com.feng.demo.JavaDocParser;

import junit.framework.TestCase;

public class HtmlReaderTest extends TestCase
{
	HtmlReader htmlReader ;
	String url = "";
	protected void setUp() throws Exception
	{
		htmlReader = new HtmlReader(url);
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testGetVersion()
	{
		fail("Not yet implemented");
	}

	public void testGetMethodsBlock()
	{
		fail("Not yet implemented");
	}

	public void testGetMethods()
	{
		fail("Not yet implemented");
	}

	public void testFormatVersion()
	{
		String jdk11 = "JDK1.1";
		String jdk15 = "1.5";
		String jdk18 = "1.8";
		String chaos1 = "1.6<p";
		String lostDot = "1.02";
		String longversion = "1.2.3.4";
		
		assertEquals("1.1", htmlReader.formatVersion(jdk11));
		assertEquals("1.5", htmlReader.formatVersion(jdk15));
		assertEquals("1.8", htmlReader.formatVersion(jdk18));
		assertEquals("1.6", htmlReader.formatVersion(chaos1));
		assertEquals("1.2.3.4", htmlReader.formatVersion(longversion));
		assertEquals("1.0.2", htmlReader.formatVersion(lostDot));
	}

}
