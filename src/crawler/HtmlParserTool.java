package crawler;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserTool {
	public static Set<String> extracLinks(String url, LinkFilter filter) {

		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
			//parser.setEncoding("utf8");
			// ¹ýÂË <frame >±êÇ©µÄ filter£¬ÓÃÀ´ÌáÈ¡ frame ±êÇ©ÀïµÄ src ÊôÐÔËù±íÊ¾µÄÁ´½Ó
			NodeFilter frameFilter = new NodeFilter() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public boolean accept(Node node) {
					if (node.getText().startsWith("iframe") && node.getText().contains("src=")) {
						return true;
					} else {
						return false;
					}
				}
			};
			// OrFilter À´ÉèÖÃ¹ýÂË <a> ±êÇ©ºÍ <frame> ±êÇ©
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
			// µÃµ½ËùÓÐ¾­¹ý¹ýÂËµÄ±êÇ©
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> ±êÇ©
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink(); //url¿ÉÄÜ³öÏÖÔÚsrc,hrefµÈÊôÐÔÖÐ
					if (filter.accept(linkUrl))
						links.add(linkUrl);
				} else// <frame> ±êÇ©
				{
					// ÌáÈ¡ frame Àï src ÊôÐÔµÄÁ´½ÓÈç <frame src="test.html"/>
					String frame = tag.getText();
					int start = frame.indexOf("src=\"");
					frame = frame.substring(start);
					int end = frame.indexOf("\">");
					if(end == -1) {
						end = frame.indexOf("?");
					}
					String frameUrl = frame.substring(5, end - 1);
					if (filter.accept(frameUrl))
						links.add(frameUrl);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}
}
