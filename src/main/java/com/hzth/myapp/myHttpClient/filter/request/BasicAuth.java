/**
 * $Id: BasicAuth.java 19 2012-05-27 15:16:14Z k42b3.x@googlemail.com $
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

package com.hzth.myapp.myHttpClient.filter.request;

import org.apache.commons.codec.binary.Base64;

import com.hzth.myapp.myHttpClient.filter.RequestFilterAbstract;
import com.hzth.myapp.myHttpClient.protocol.Request;

/**
 * BasicAuth
 * 
 * @author Christoph Kappestein <k42b3.x@gmail.com>
 * @license http://www.gnu.org/licenses/gpl.html GPLv3
 * @link http://aletheia.k42b3.com
 * @version $Revision: 19 $
 */
public class BasicAuth extends RequestFilterAbstract
{
	public void exec(Request request)
	{
		if (request instanceof com.hzth.myapp.myHttpClient.protocol.http.Request)
		{
			com.hzth.myapp.myHttpClient.protocol.http.Request httpRequest = (com.hzth.myapp.myHttpClient.protocol.http.Request) request;

			// get config
			String user = getConfig().getProperty("user");
			String pw = getConfig().getProperty("pw");

			httpRequest.setHeader("Authorization", "Basic " + this.computeAuth(user + ":" + pw));
		}
	}

	protected String computeAuth(String key)
	{
		return Base64.encodeBase64String(key.getBytes());
	}
}
