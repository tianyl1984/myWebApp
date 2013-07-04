package com.hzth.myapp.myHttpClient;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hzth.myapp.myHttpClient.protocol.Response;
import com.hzth.myapp.myHttpClient.sidebar.Link;

public class Sidebar extends JPanel
{
	private Link link;

	public Sidebar()
	{
		super();

		// settings
		this.setPreferredSize(new Dimension(240, 400));
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(0, 4, 0, 0));

		this.link = new Link();

		this.add(link, BorderLayout.CENTER);
	}
	
	public void update(Response response)
	{
		try
		{
			this.link.process(response);
		}
		catch(Exception e)
		{
			Aletheia.handleException(e);
		}
	}
}
