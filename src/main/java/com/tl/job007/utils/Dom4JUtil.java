package com.tl.job007.utils;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4JUtil {
	public static String getElementTextByRoot(String xmlString,
			String elementName) throws DocumentException {
		SAXReader reader = new SAXReader();

		StringReader sr = new StringReader(xmlString);

		Document document = reader.read(sr);
		Element root = document.getRootElement();
		return root.elementText(elementName);
	}

}
