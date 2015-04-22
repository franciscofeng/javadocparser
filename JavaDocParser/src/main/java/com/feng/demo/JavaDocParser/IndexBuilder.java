package com.feng.demo.JavaDocParser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IndexBuilder
{
	private static String[] EXT = {"html"};
	private static String INDEX_FILE_NAME = "allclasses-noframe.html";
	private static String[] PACKAGES = {"java"};
	private static String API = "api";

	public void execute(String path)
	{
		String apiPath = path+File.separator+API+File.separator;
		Set<String> classNameSet = new HashSet<String>();
		for(String packageName : PACKAGES)
		{
			String packagePath = apiPath+packageName+File.separator;
			classNameSet.addAll(this.getAllClassNames(packagePath));
		}
		String indexFileName = apiPath+INDEX_FILE_NAME;
		this.buildIndex(indexFileName, classNameSet);
	}
	
	public Set<String> getAllClassNames(String path)
	{
		Set<String> classNameSet = new HashSet<String>();
		Collection<File> fileList = FileUtils.listFiles(new File(path), EXT, true);
		for(File file : fileList)
		{
			String fileBaseName = FilenameUtils.getBaseName(file.getName());
			classNameSet.add(fileBaseName);
		}
		return classNameSet;
	}
	
	public void buildIndex(String indexFileName,Set<String> classNameSet)
	{
		File indexFile = new File(indexFileName);
		Document doc = null;
		try
		{
			doc = Jsoup.parse(indexFile, "UTF-8");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		Elements links = doc.select("a[href]");
		for(Element link :links)
		{
			if(!classNameSet.contains(link.html().trim()))
			{
				link.parent().remove();
			}
		}
		try
		{
			FileUtils.write(indexFile, doc.html());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
//		System.out.println(doc.html());
	}

	public static void main(String[] args)
	{
		String path = "D:\\tmp\\javadocs\\1.0.2\\api\\java\\";
		String indexFileName = "D:\\tmp\\javadocs\\1.0.2\\api\\allclasses-noframe.html";
		IndexBuilder builder = new IndexBuilder();
//		Set<String> classNameSet = builder.getAllClassNames(path);
//		builder.buildIndex(indexFileName, classNameSet);
		builder.execute("D:\\tmp\\javadocs\\1.02\\");
	}
}
