/**
 * $Id: ProcessorFactory.java 31 2012-10-03 13:14:24Z k42b3.x@googlemail.com $
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

package com.hzth.myapp.myHttpClient.processor;

import java.util.HashMap;

import com.hzth.myapp.myHttpClient.processor.format.Json;
import com.hzth.myapp.myHttpClient.processor.format.Xml;
import com.hzth.myapp.myHttpClient.processor.html.Form;
import com.hzth.myapp.myHttpClient.protocol.Response;

/**
 * ProcessorFactory
 * 
 * @author Christoph Kappestein <k42b3.x@gmail.com>
 * @license http://www.gnu.org/licenses/gpl.html GPLv3
 * @link http://aletheia.k42b3.com
 * @version $Revision: 31 $
 */
public class ProcessorFactory
{
	public static HashMap<String, ProcessorInterface> processors = new HashMap<String, ProcessorInterface>();

	public static ProcessorInterface factory(String name) throws Exception
	{
		if (processors.containsKey(name))
		{
			return processors.get(name);
		}

		if (name.equals("html.form"))
		{
			processors.put(name, new Form());
		}
		else if (name.equals("format.xml"))
		{
			processors.put(name, new Xml());
		}
		else if (name.equals("format.json"))
		{
			processors.put(name, new Json());
		}
		else
		{
			throw new Exception("Invalid processor");
		}

		return factory(name);
	}

	public static String getResponseContent(Response response)
	{
		if (response instanceof com.hzth.myapp.myHttpClient.protocol.http.Response)
		{
			return ((com.hzth.myapp.myHttpClient.protocol.http.Response) response).getBody();
		}

		return null;
	}
}
