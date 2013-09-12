package com.nbcedu.function.elec.devcore.util.sqlxml.context;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public final class XmlReader {
	private XmlReader(){}
	/**
	 * the sql-context.xml path
	 */
	private static final String file_path = "sql-xml/sql-context.xml";

	static Document read() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(file_path);
		Document document = builder.parse(stream);
		return document;
	}

}
