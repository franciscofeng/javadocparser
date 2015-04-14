package com.feng.demo.JavaDocParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App 
{
	public List<String> getAllLinks(String rootURL)
	{
		List<String> urls = new ArrayList<String>();
        Document rootDoc = null;
		try {
			rootDoc = Jsoup.connect(rootURL).get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Elements links = rootDoc.select("a[href]");
        String baseURL = this.getBaseURL(rootURL);
        
        for(Element e:links)
        {
        	urls.add(baseURL+e.attr("href"));
        }
		return urls;
	}
	private String getBaseURL(String rootURL)
	{
		return rootURL.substring(0, rootURL.lastIndexOf("/")+1);
	}
    public static void main( String[] args )
    {
        String rootURL = "http://docs.oracle.com/javase/8/docs/api/allclasses-noframe.html";
        App app = new App();
        List<String> urls = app.getAllLinks(rootURL);
        for(String url:urls)
        {
        	System.out.println(url);
        }

    }
}
