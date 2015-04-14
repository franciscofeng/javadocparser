package com.feng.demo.JavaDocParser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlReader
{

	public static void main(String[] args)
	{
		// String url =
		// "http://docs.oracle.com/javase/7/docs/api/java/util/Observer.html";
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
		Elements methods = htmlReader.getMethods(methodBlock);

		for (Element method : methods)
		{
			System.out.println(method);
			System.out.println("==============================");
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
				System.out.println("class or method version is :" + e.html());
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
			return methodDetail.parent().parent();
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
			return null;
		}
		return rawVersion.substring(rawVersion.indexOf("1"),
				rawVersion.length());
	}

}
