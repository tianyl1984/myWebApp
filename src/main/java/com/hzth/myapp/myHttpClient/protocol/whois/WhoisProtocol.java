/**
 * $Id: WhoisProtocol.java 27 2012-05-28 10:52:28Z k42b3.x@googlemail.com $
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

package com.hzth.myapp.myHttpClient.protocol.whois;

import java.io.IOException;
import java.net.URL;
import java.net.URLStreamHandler;

import org.apache.commons.net.whois.WhoisClient;

import com.hzth.myapp.myHttpClient.Aletheia;
import com.hzth.myapp.myHttpClient.protocol.ProtocolAbstract;

/**
 * FtpProtocol
 * 
 * @author Christoph Kappestein <k42b3.x@gmail.com>
 * @license http://www.gnu.org/licenses/gpl.html GPLv3
 * @link http://aletheia.k42b3.com
 * @version $Revision: 27 $
 */
public class WhoisProtocol extends ProtocolAbstract
{
	protected WhoisClient whois;

	public WhoisProtocol()
	{
		whois = new WhoisClient();
	}

	public void run()
	{
		try
		{
			// connect
			int port = request.getUrl().getPort() == -1 ? 43 : request.getUrl().getPort();

			whois.connect(request.getUrl().getHost(), port);

			// send query
			String response = whois.query(this.getRequest().getContent());

			// create response
			this.response = new Response(response);

			// call callback
			callback.onResponse(this.request, this.response);
		} catch (Exception e)
		{
			Aletheia.handleException(e);
		} finally
		{
			// disconnect
			try
			{
				whois.disconnect();
			} catch (IOException e)
			{
				Aletheia.handleException(e);
			}
		}
	}

	public Request buildRequest(URL url, String content) throws Exception
	{
		return new Request(url, content);
	}

	public Request getRequest()
	{
		return (Request) this.request;
	}

	public Response getResponse()
	{
		return (Response) this.response;
	}

	public URLStreamHandler getStreamHandler()
	{
		return new WhoisURLStreamHandler();
	}
}
