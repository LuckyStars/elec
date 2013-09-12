package com.nbcedu.function.elec.devcore.util.sqlxml.context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public final class SqlContext {
	private SqlContext(){}
	private static Map<String,ClassSqlContext> sqlContext = null;
	
	private static class ContextHolder{
		private static void init() {
			Document doc;
			try {
				doc = XmlReader.read();
				sqlContext = parseXml(doc);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private static Map<String, ClassSqlContext> parseXml(Document doc) {
			
			Map<String, ClassSqlContext> result = new HashMap<String, ClassSqlContext>();
			Node root = doc.getElementsByTagName("sqlcontext").item(0);
			
			if(root.getChildNodes().getLength()>0){
				for (int i = 0, end = root.getChildNodes().getLength(); i < end; i++) {
					if("class".equalsIgnoreCase(root.getChildNodes().item(i).getNodeName())){
						Map<String, String> queryMap = new HashMap<String, String>();
						Node clazzNode = root.getChildNodes().item(i);
						queryMap = buildQueryMap(clazzNode);
						String clazzId = StringUtils.trimToEmpty(((Element)root.getChildNodes().item(i)).getAttribute("name"));
						ClassSqlContext clazz = new ClassSqlContext(queryMap);
						result.put(clazzId, clazz);
					}
				}
			}
			return result;
		}
		
		private static Map<String,String> buildQueryMap(Node clazzNode){
			Map<String,String> queryMap = new HashMap<String, String>();
			if(clazzNode.getChildNodes().getLength()>0){
				for (int j = 0,jend = clazzNode.getChildNodes().getLength(); j < jend; j++) {
					if("query".equalsIgnoreCase(clazzNode.getChildNodes().item(j).getNodeName())){
						String queryId = StringUtils.trimToEmpty(((Element)clazzNode.getChildNodes().item(j)).getAttribute("id"));
						String queryBody = StringUtils.trimToEmpty(clazzNode.getChildNodes().item(j).getTextContent());
						queryMap.put(queryId, queryBody);
					}
				}
			}
			return queryMap;
		}

		private static final Map<String,ClassSqlContext> getSqlContext(){
			if(sqlContext==null){
				init();
			}
			return sqlContext;
		}
	}
	/**
	 * get the classSqlContext by class name
	 * @param key
	 * @return
	 * @author xuechong
	 */
	public static ClassSqlContext getByClass(String key){
		return ContextHolder.getSqlContext().get(key);
	}
	
}
