package com.feng.demo.JavaDocParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

public class ZipUtil
{
	public static void decompress(String srcFile, String destPath)
	{
		try
		{
			File outFile = new File(destPath);
			if (!outFile.exists())
			{
				outFile.mkdirs();
			}
			ZipFile zipFile = new ZipFile(srcFile);
			Enumeration<?> en = zipFile.getEntries();
			ZipArchiveEntry zipEntry = null;
			while (en.hasMoreElements())
			{
				zipEntry = (ZipArchiveEntry) en.nextElement();
				if (zipEntry.isDirectory())
				{
					// mkdir directory
					String dirName = zipEntry.getName();
					dirName = dirName.substring(0, dirName.length() - 1);
					File f = new File(outFile.getPath() + File.separator
							+ dirName);
					f.mkdirs();
				} else
				{
					// unzip file
					File f = new File(outFile.getPath() + File.separator
							+ zipEntry.getName());
					if (!f.getParentFile().exists())
					{
						f.getParentFile().mkdirs();
					}
					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					OutputStream out = new FileOutputStream(f);
					IOUtils.copy(in, out);
					out.close();
					in.close();
				}
			}
			zipFile.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		String src = "/Users/feng/dev/gitrepos/javadocparser/JavaDocParser/target/classes/api.zip";
		String dest = "/Users/feng/dev/tmp/";
		ZipUtil.decompress(src, dest);
	}

}
