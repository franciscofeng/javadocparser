package com.feng.demo.JavaDocParser;

public class WriteJob
{
	private String content;
	private String version;
	private String packageName;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("WriteJob [version=");
		builder.append(version);
		builder.append(", packageName=");
		builder.append(packageName);
		builder.append("]");
		return builder.toString();
	}

}
