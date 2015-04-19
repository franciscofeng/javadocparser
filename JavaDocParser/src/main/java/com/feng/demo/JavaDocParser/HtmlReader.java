package com.feng.demo.JavaDocParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlReader
{
	private String url;

	public HtmlReader(String url)
	{
		this.url = url;
	}

	public List<WriteJob> execute()
	{
		List<WriteJob> jobs = new ArrayList<WriteJob>();
		Document doc = getDocumentByURL();
		if(doc == null)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		doc = getDocumentByURL();
		if(doc == null)
		{
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		if(doc == null)
		{
			return jobs;
		}

		String defaultVersion = getVersion(doc);
		String packageName = getPackageName(url);

		Element methodBlock = getMethodsBlock(doc);
		//no new methods
		if(methodBlock == null)
		{
			return jobs;
		}
		Set<String> methodVersionSet = getMethodVersions(methodBlock);
		if (methodVersionSet.size() >= 0)
		{
			// default version
			Document defaultDoc = doc.clone();
			removeOtherVersionsMethods(defaultDoc, "");
			WriteJob job = new WriteJob();
			job.setVersion(defaultVersion);
			job.setPackageName(packageName);
			job.setContent(defaultDoc.html());
			jobs.add(job);
			for (String version : methodVersionSet)
			{
				Document versionDoc = doc.clone();
				removeOtherVersionsMethods(versionDoc, version);
				WriteJob subJob = new WriteJob();
				subJob.setVersion(version);
				subJob.setPackageName(packageName);
				subJob.setContent(versionDoc.html());
				jobs.add(subJob);
			}
		} else
		{
			removeOtherVersionsMethods(doc, "");
			WriteJob job = new WriteJob();
			job.setVersion(defaultVersion);
			job.setPackageName(packageName);
			job.setContent(doc.html());
			jobs.add(job);
		}
		return jobs;
	}

	public Document getDocumentByURL()
	{
		Document doc = null;
		try
		{
			doc = Jsoup.connect(url).get();
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return doc;
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
				// System.out.println("class or method version is :" +
				// e.html());
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
			return "unknown";
		}
		if(rawVersion.indexOf("1") < 0)
		{
			if(rawVersion.length() == 1 && Character.isDigit(rawVersion.charAt(0)))
			{
				rawVersion = "1."+rawVersion;
			}
			else
			{
				return "unknown";
			}

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
				String version = e.html().trim();
				versionSet.add(formatVersion(version));
//				System.out.println(" method version is :" + e.html());
				isFound = false;
			}
			if ("Since:".equalsIgnoreCase(e.html().trim()))
			{
				isFound = true;
			}
		}
//		System.out.println("method version set:");
//		for (String version : versionSet)
//		{
//			System.out.println(version);
//		}
		return versionSet;
	}

	public Document removeOtherVersionsMethods(Document doc, String version)
	{
		Element methodBlock = getMethodsBlock(doc);
		Elements methods = getMethods(methodBlock);
		for (Element method : methods)
		{
			String v = this.getVersion(method);
			if (!version.equalsIgnoreCase(v))
			{
				method.remove();
			}
		}
		return doc;
	}

	public String getPackageName(String url)
	{
		return url.substring(41, url.lastIndexOf("."));
	}

	public static void main(String[] args)
	{
		// String url =
		// "http://docs.oracle.com/javase/7/docs/api/java/util/Observer.html";
//		String url = "http://docs.oracle.com/javase/8/docs/api/java/util/Locale.html";
//		String url  = "http://docs.oracle.com/javase/8/docs/api/java/lang/Double.html";
		String url ="http://docs.oracle.com/javase/8/docs/api/java/beans/Beans.html";
		HtmlReader reader = new HtmlReader(url);
		List<WriteJob> jobs = reader.execute();
		for(WriteJob job:jobs)
		{
			System.out.println(job);
		}
		
	}
}
