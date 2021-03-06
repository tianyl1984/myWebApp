/**
 * $Id: Process.java 19 2012-05-27 15:16:14Z k42b3.x@googlemail.com $
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import com.hzth.myapp.myHttpClient.filter.RequestFilterAbstract;
import com.hzth.myapp.myHttpClient.protocol.Request;

/**
 * Process
 * 
 * @author Christoph Kappestein <k42b3.x@gmail.com>
 * @license http://www.gnu.org/licenses/gpl.html GPLv3
 * @link http://aletheia.k42b3.com
 * @version $Revision: 19 $
 */
public class Process extends RequestFilterAbstract
{
	private long timeout = 8000;

	private ByteArrayOutputStream baos;
	private ByteArrayOutputStream baosErr;
	private ByteArrayInputStream bais;

	public void exec(Request request)
	{
		String cmd = getConfig().getProperty("cmd");

		try
		{
			logger.info("Execute: " + cmd);

			CommandLine commandLine = CommandLine.parse(cmd);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
			DefaultExecutor executor = new DefaultExecutor();

			this.baos = new ByteArrayOutputStream();
			this.baosErr = new ByteArrayOutputStream();
			this.bais = new ByteArrayInputStream(request.getContent().getBytes());

			executor.setStreamHandler(new PumpStreamHandler(this.baos, this.baosErr, this.bais));
			executor.setWatchdog(watchdog);
			executor.execute(commandLine);

			request.setContent(this.baos.toString());
		} catch (Exception e)
		{
			logger.warning(e.getMessage());
		}
	}
}
