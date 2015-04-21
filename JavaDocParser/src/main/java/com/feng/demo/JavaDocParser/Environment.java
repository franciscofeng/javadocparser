package com.feng.demo.JavaDocParser;


public class Environment
{
	private static String ROOT_DIR;
	private static String RESOURSE_ZIP;
	static {
		OS os = getSystemOS();
		if(os == OS.MAC)
		{
			ROOT_DIR = "/Users/feng/dev/javadocs/";
			RESOURSE_ZIP = "/Users/feng/dev/gitrepos/javadocparser/JavaDocParser/target/classes/api.zip";
		}
		else if(os == OS.WIN)
		{
			ROOT_DIR = "d:\\tmp\\javadocs\\";
			RESOURSE_ZIP = "D:\\git\\javadocparser\\JavaDocParser\\target\\classes\\api.zip";
		}
	}
	public static OS getSystemOS()
	{
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf("win") >= 0)
		{
			return OS.WIN;
		}else if(os.indexOf("mac") >=0)
		{
			return OS.MAC;
		}
		return OS.MAC;
	}
	
	public static String getRootPath()
	{
		return ROOT_DIR;
	}
	public static String getResourceZip()
	{
		return RESOURSE_ZIP;
	}
	private enum OS
	{
		MAC,WIN;
	}
}
