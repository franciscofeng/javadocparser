package com.feng.demo.JavaDocParser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlReader
{

	public static void main(String[] args)
	{
//		 String url = "http://docs.oracle.com/javase/7/docs/api/java/util/Observer.html";
		String url = "http://docs.oracle.com/javase/8/docs/api/java/util/Locale.html";
		Document doc = null;
		try
		{
			doc = Jsoup.connect(url).get();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		HtmlReader htmlReader = new HtmlReader();
		htmlReader.getVersion(doc);
		Element methodBlock = htmlReader.getMethodsBlock(doc);
//		Elements methods = htmlReader.getMethods(methodBlock);
		Set<String> methodVersionSet = htmlReader.getMethodVersions(methodBlock);
		if(methodVersionSet.size() >=0)
		{
			//default version
//			Document defaultDoc = doc.clone();
//			htmlReader.removeOtherVersionsMethods(defaultDoc, "");
//			System.out.println(defaultDoc);
			for(String version:methodVersionSet)
			{
				Document versionDoc = doc.clone();
				htmlReader.removeOtherVersionsMethods(versionDoc, version);
				System.out.println(versionDoc);
			}			
		}
	}

	public String getVersion(Element element)
	{
		String version = "";
		Elements elements = element.getAllElements();
		boolean isFound = false;
		for (Element e : elements)
		{
			if (isFound)
			{
//				System.out.println("class or method version is :" + e.html());
				version = e.html().trim();
				break;
			}
			if ("Since:".equalsIgnoreCase(e.html().trim()))
			{
				isFound = true;
			}
		}
		return formatVersion(version);
	}

	public Element getMethodsBlock(Element element)
	{
		Element methodDetail = null;
		for (Element e : element.getAllElements())
		{
			if ("Method Detail".equalsIgnoreCase(e.html().trim()))
			{
				methodDetail = e;
				break;
			}
		}
		if (methodDetail != null)
		{
			return methodDetail.parent();
		}
		return null;
	}

	public Elements getMethods(Element methodBlock)
	{
		Elements blockList = methodBlock.select("ul.blockList");

		Elements blockListLast = methodBlock.select("ul.blockListLast");
		if (blockList != null && blockListLast != null)
		{
			blockList.add(blockListLast.first());
		}
		if (blockList == null)
		{
			return blockListLast;
		}

		return blockList;
	}

	public String formatVersion(String rawVersion)
	{
		if (rawVersion == null || "".equalsIgnoreCase(rawVersion.trim()))
		{
			return "";
		}
		return rawVersion.substring(rawVersion.indexOf("1"),
				rawVersion.length());
	}
	
	public Set<String> getMethodVersions(Element methodBlock)
	{
		Set<String> versionSet = new HashSet<String>();
		Elements elements = methodBlock.getAllElements();
		boolean isFound = false;
		for (Element e : elements)
		{
			if (isFound)
			{
				System.out.println(" method version is :" + e.html());
				String version = e.html().trim();
				versionSet.add(version);
				isFound = false;
			}
			if ("Since:".equalsIgnoreCase(e.html().trim()))
			{
				isFound = true;
			}
		}
		System.out.println("method version set:");
		for(String version:versionSet)
		{
			System.out.println(version);
		}
		return versionSet;
	}
	public Document removeOtherVersionsMethods(Document doc,String version)
	{
		Element methodBlock = getMethodsBlock(doc);
		Elements methods = getMethods(methodBlock);
		for (Element method : methods)
		{
			String v = this.getVersion(method);
			if(!version.equalsIgnoreCase(v))
			{
				method.remove();
			}
		}
		return doc;
	}
}
