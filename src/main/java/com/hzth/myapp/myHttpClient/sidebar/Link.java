/**
 * $Id: Link.java 27 2012-05-28 10:52:28Z k42b3.x@googlemail.com $
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

package com.hzth.myapp.myHttpClient.sidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.hzth.myapp.myHttpClient.Aletheia;
import com.hzth.myapp.myHttpClient.Parser;
import com.hzth.myapp.myHttpClient.TextFieldUrl;
import com.hzth.myapp.myHttpClient.processor.ProcessorFactory;
import com.hzth.myapp.myHttpClient.protocol.Response;
import com.hzth.myapp.myHttpClient.protocol.http.Util;

/**
 * Link
 * 
 * @author Christoph Kappestein <k42b3.x@gmail.com>
 * @license http://www.gnu.org/licenses/gpl.html GPLv3
 * @link http://aletheia.k42b3.com
 * @version $Revision: 27 $
 */
public class Link extends JPanel
{
	private ArrayList<String> links;
	private DefaultListModel lm;
	private JList list;
	private JTextField search;

	private String baseUrl;

	public Link()
	{
		super();

		// settings
		this.setLayout(new BorderLayout());

		// search
		JPanel panelSearch = new JPanel();
		// panelSearch.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		panelSearch.setLayout(new BorderLayout());

		this.search = new TextFieldUrl();
		this.search.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e)
			{
			}

			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					if (!list.hasFocus())
					{
						list.requestFocus();
						list.setSelectedIndex(0);
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					setVisible(false);
				}
				else
				{
					filter(search.getText());
				}
			}

			public void keyPressed(KeyEvent e)
			{
			}

		});
		this.search.setPreferredSize(new Dimension(200, 24));

		panelSearch.add(this.search, BorderLayout.CENTER);

		this.add(panelSearch, BorderLayout.SOUTH);

		// link list
		links = new ArrayList<String>();
		lm = new DefaultListModel();
		list = new JList(lm);
		list.setFont(new Font("Courier New", Font.PLAIN, 12));
		list.setBackground(new Color(255, 255, 255));
		list.setForeground(new Color(0, 0, 0));
		// list.addListSelectionListener(new LinkListener());
		list.addKeyListener(new LinkKeyListener());
		list.addMouseListener(new LinkMouseListener());

		JScrollPane scp = new JScrollPane(list);
		scp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// scp.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));

		this.add(scp, BorderLayout.CENTER);
	}

	public String getName()
	{
		return "Link Parser";
	}

	public void process(Response response) throws Exception
	{
		// get content
		String html = ProcessorFactory.getResponseContent(response);

		if (html != null)
		{
			this.reset();

			// set base url
			this.baseUrl = Aletheia.getInstance().getActiveUrl().getText();

			// parse html
			this.parseLinks(html);
		}
	}

	private void reset()
	{
		this.lm.clear();
	}

	private void parseLinks(String html)
	{
		lm.clear();
		links.clear();

		// parse links
		for (int i = 0; i < html.length(); i++)
		{
			if (Parser.startsWith("<a", i, html))
			{
				String aTag = Parser.getTag(i, html);
				String href = Parser.getAttribute("href", aTag);

				if (href != null && !href.isEmpty())
				{
					links.add(href);
				}
			}
		}

		// add links to list
		for (int i = 0; i < links.size(); i++)
		{
			lm.addElement(links.get(i));
		}
	}

	private void filter(String text)
	{
		if (text != null && !text.isEmpty())
		{
			for (int i = 0; i < lm.size(); i++)
			{
				if (lm.get(i).toString().indexOf(text) == -1)
				{
					lm.remove(i);
				}
			}
		}
		else
		{
			lm.clear();

			for (int i = 0; i < links.size(); i++)
			{
				lm.addElement(links.get(i));
			}
		}
	}

	private void callSelectedLink(boolean newTab)
	{
		String selectedUrl = list.getSelectedValue().toString();

		if (selectedUrl != null)
		{
			try
			{
				String url = Util.resolveHref(baseUrl, selectedUrl);

				if (newTab)
				{
					Aletheia.getInstance().newTab(true);
				}

				Aletheia.getInstance().run(url);
			} catch (Exception e)
			{
				Aletheia.handleException(e);
			}
		}
	}

	class LinkMouseListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{
		}

		public void mouseEntered(MouseEvent e)
		{
		}

		public void mouseExited(MouseEvent e)
		{
		}

		public void mousePressed(MouseEvent e)
		{
			callSelectedLink(e.isControlDown());
		}

		public void mouseReleased(MouseEvent e)
		{
		}
	}

	class LinkKeyListener implements KeyListener
	{
		public void keyTyped(KeyEvent e)
		{
		}

		public void keyReleased(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				callSelectedLink(e.isControlDown());
			}
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				setVisible(false);
			}
		}

		public void keyPressed(KeyEvent e)
		{
		}
	}
}
