/**
 * $Id: Request.java 23 2012-05-27 23:19:57Z k42b3.x@googlemail.com $
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

package com.hzth.myapp.myHttpClient.protocol;

import java.net.URL;

/**
 * Request
 *
 * @author     Christoph Kappestein <k42b3.x@gmail.com>
 * @license    http://www.gnu.org/licenses/gpl.html GPLv3
 * @link       http://aletheia.k42b3.com
 * @version    $Revision: 23 $
 */
public class Request 
{
	protected URL url;
	protected String content;

	public Request(URL url, String content)
	{
		this.setUrl(url);
		this.setContent(content);
	}

	public URL getUrl()
	{
		return this.url;
	}

	public void setUrl(URL url)
	{
		this.url = url;
	}

	public String getContent()
	{
		return this.content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}

	public String toString()
	{
		return this.getContent();
	}
}
