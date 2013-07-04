package com.hzth.myapp.xml;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserTest {
	public static void main(String[] args) throws Exception{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parser=factory.newSAXParser();
		parser.parse(new File("./src/xml/test01.xml"), new DefaultHandler(){

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				System.out.println(new String(ch,start,length));
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				System.out.println("</"+qName+">");
			}

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				System.out.print("<"+qName);
				for(int i=0;i<attributes.getLength();i++){
					System.out.print(" "+attributes.getQName(i)+"=\""+attributes.getValue(i)+"\"");
				}
				System.out.print(">");
			}
			
		});
	}
}
