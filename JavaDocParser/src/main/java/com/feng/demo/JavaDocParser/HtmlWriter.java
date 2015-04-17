package com.feng.demo.JavaDocParser;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class HtmlWriter
{
	
	private static String ROOT_DIR;
	{
		OS os = getSystemOS();
		if(os == OS.MAC)
		{
			ROOT_DIR = "/Users/feng/dev/javadocs/";
		}
		else if(os == OS.WIN)
		{
			ROOT_DIR = "d:\\tmp\\javadocs\\";
		}
	}
	public HtmlWriter()
	{
		
	}
	public boolean write2html(WriteJob job)
	{
		String fileName = getFileName(job.getPackageName(), job.getVersion());
		try
		{
			FileUtils.write(new File(fileName), job.getContent());
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getFileName(String packageName,String version)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(ROOT_DIR);
		sb.append(version);
		sb.append(File.separator);
		sb.append(packageName.replace(".", File.separator));
		sb.append(".html");
		return sb.toString();		
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
	private enum OS
	{
		MAC,WIN;
	}
}

