package com.feng.demo.JavaDocParser;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class HtmlWriter
{
	
	private static String ROOT_DIR = "/Users/feng/dev/javadocs/";
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	public boolean write2html(String content,String packageName,String version)
	{
		String fileName = getFileName(packageName, version);
		try
		{
			FileUtils.write(new File(fileName), content);
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
		sb.append(packageName.replaceAll("\\.", File.separator));
		sb.append(".html");
		return sb.toString();		
	}
}
