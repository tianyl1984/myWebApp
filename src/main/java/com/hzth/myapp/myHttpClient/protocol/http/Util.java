/**
 * $Id: Util.java 31 2012-10-03 13:14:24Z k42b3.x@googlemail.com $
 * 
 * aletheia
 * A browser like application to send raw http requests. It is designed for 
 * debugging and finding security issues in web applications. For the current 
 * version and more informations visit <http://code.google.com/p/aletheia>
 * 
 * Copyright (c) 2010-2012 Christoph Kappestein <k42b3.x@gmail.com>
 * 
 * This file is part of Aletheia. Aletheia is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or at any later version.
 * 
 * Aletheia is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Aletheia. If not, see <http://www.gnu.org/licenses/>.
 */

package com.hzth.myapp.myHttpClient.protocol.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * Util
 * 
 * @author Christoph Kappestein <k42b3.x@gmail.com>
 * @license http://www.gnu.org/licenses/gpl.html GPLv3
 * @link http://aletheia.k42b3.com
 * @version $Revision: 31 $
 */
public class Util
{
	public final static String[] types = { "HTTP/1.0", "HTTP/1.1" };
	public final static String[] methods = { "OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "CONNECT" };

	public static boolean isValidMethod(String method)
	{
		for (int i = 0; i < Util.methods.length; i++)
		{
			if (Util.methods[i].equals(method))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean isValidType(String type)
	{
		for (int i = 0; i < Util.types.length; i++)
		{
			if (Util.types[i].equals(type))
			{
				return true;
			}
		}

		return false;
	}

	public static Charset getContentTypeCharset(String contentType)
	{
		// default charset
		String charset = "UTF-8";

		// we look in the content-type header for an charset
		if (contentType != null)
		{
			int pos = contentType.indexOf("charset=");

			if (pos != -1)
			{
				charset = contentType.substring(pos + 8).trim();
			}
		}

		return Charset.forName(charset);
	}

	public static Map<String, String> parseHeader(String rawHeader, String delimiter)
	{
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();

		String[] lines = rawHeader.split(delimiter);

		if (lines.length > 0)
		{
			// headers
			for (int i = 0; i < lines.length; i++)
			{
				int pos = lines[i].indexOf(':');

				if (pos != -1)
				{
					String key = lines[i].substring(0, pos).trim();
					String value = lines[i].substring(pos + 1).trim();

					if (!key.isEmpty() && !value.isEmpty())
					{
						headers.put(key, value);
					}
				}
			}
		}

		return headers;
	}

	public static String buildMessage(String statusLine, Map<String, String> header, String body, String delimter)
	{
		StringBuilder str = new StringBuilder();

		Iterator<Entry<String, String>> itr = header.entrySet().iterator();

		str.append(statusLine + delimter);

		while (itr.hasNext())
		{
			Entry<String, String> e = itr.next();

			str.append(e.getKey() + ": " + e.getValue() + delimter);
		}

		str.append(delimter);

		if (body != null && !body.isEmpty())
		{
			str.append(body);
		}

		return str.toString();
	}

	public static String urlEncode(String content)
	{
		try
		{
			return URLEncoder.encode(content, "UTF-8");
		} catch (Exception e)
		{
			return null;
		}
	}

	public static String appendQuery(String url, String params)
	{
		if (url.indexOf('?') == -1)
		{
			return url + '?' + params;
		}
		else
		{
			return url + '&' + params;
		}
	}

	/**
	 * This method takes an base url and resolves the href to an url
	 * 
	 * @param baseUrl
	 * @param href
	 * @return
	 * @throws MalformedURLException
	 */
	public static String resolveHref(String baseUrl, String href) throws MalformedURLException
	{
		if (href.startsWith("http://") || href.startsWith("https://"))
		{
			// we have an absolute url
			return href;
		}
		else if (href.startsWith("//"))
		{
			return "http:" + href;
		}
		else
		{
			// we have an path wich must be resolved to the base url
			URL currentUrl = new URL(baseUrl);
			String completePath;

			if (href.startsWith("/"))
			{
				completePath = href;
			}
			else
			{
				completePath = currentUrl.getPath() + "/" + href;
			}

			// remove dot segments from path
			String path = removeDotSegments(completePath);

			// build url
			String url = currentUrl.getProtocol() + "://" + currentUrl.getHost() + path;

			// add query params
			int sPos, ePos;
			sPos = href.indexOf('?');

			if (sPos != -1)
			{
				String query;
				ePos = href.indexOf('#');

				if (ePos == -1)
				{
					query = href.substring(sPos + 1);
				}
				else
				{
					query = href.substring(sPos + 1, ePos);
				}

				if (!query.isEmpty())
				{
					url += "?" + query;
				}
			}

			// add fragment
			sPos = href.indexOf('#');

			if (sPos != -1)
			{
				String fragment = href.substring(sPos + 1);

				if (!fragment.isEmpty())
				{
					url += "#" + fragment;
				}
			}

			return url;
		}
	}

	public static String removeDotSegments(String relativePath)
	{
		// remove query or fragment part if any
		int pos = relativePath.indexOf('?');

		if (pos != -1)
		{
			relativePath = relativePath.substring(0, pos);
		}

		pos = relativePath.indexOf('#');

		if (pos != -1)
		{
			relativePath = relativePath.substring(0, pos);
		}

		// resolve absolute url
		if (relativePath.indexOf('/') == -1)
		{
			return relativePath;
		}

		String[] parts = relativePath.split("/");
		Stack<String> path = new Stack<String>();
		String part;

		for (int i = 0; i < parts.length; i++)
		{
			part = parts[i].trim();

			if (part.isEmpty() || part == ".")
			{
			}
			else if (part == "..")
			{
				path.pop();
			}
			else
			{
				path.add(part);
			}
		}

		// build absolute url
		String absoluteUrl = "";

		if (path.size() > 0)
		{
			for (int i = 0; i < path.size(); i++)
			{
				absoluteUrl += "/" + path.get(i);
			}
		}
		else
		{
			absoluteUrl = "/";
		}

		return absoluteUrl;
	}
}
