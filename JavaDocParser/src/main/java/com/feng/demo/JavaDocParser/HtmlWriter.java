package com.feng.demo.JavaDocParser;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class HtmlWriter
{
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
		sb.append(Environment.getRootPath());
		sb.append(version);
		sb.append(File.separator);
		sb.append("api");
		sb.append(File.separator);
		sb.append(packageName.replace(".", File.separator));
		sb.append(".html");
		return sb.toString();		
	}
	
}

