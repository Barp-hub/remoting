package io.github.riwcwt;

import io.github.riwcwt.entity.Feed;
import io.github.riwcwt.service.FeedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlTest {

    private static final Logger logger = LoggerFactory.getLogger(XmlTest.class);

    @Autowired
    private FeedService feedService = null;

    @Test
    public void xml() {
        // students的内容为上面所示XML代码内容
        File file = new File("src/main/resources/rss/feeds.xml");

        try {
            // 创建文档解析的对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 解析文档，形成文档树，也就是生成Document对象
            Document document = builder.parse(file);

            // 获得根节点
            Element rootElement = document.getDocumentElement();
            System.out.printf("Root Element: %s\n", rootElement.getNodeName());

            // 获得根节点下的所有子节点
            NodeList nodes = rootElement.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                // 获得第i个子节点
                Node child = nodes.item(i);

                if (child.getNodeType() == Node.ELEMENT_NODE) {

                    System.out.printf("Element: %s\n", child.getNodeName());

                    if (child.getNodeName().equalsIgnoreCase("body")) {
                        NodeList catalog = child.getChildNodes();
                        for (int j = 0; j < catalog.getLength(); j++) {
                            if (catalog.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                Element outline = Element.class.cast(catalog.item(j));
                                System.out.println(outline.getTagName() + " " + outline.getAttribute("text"));

                                NodeList list = outline.getChildNodes();

                                for (int k = 0; k < list.getLength(); k++) {
                                    if (list.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                        Element line = Element.class.cast(list.item(k));

                                        System.out.println(line.getTagName() + " " + line.getAttribute("title") + " " + line.getAttribute("xmlUrl"));

                                        Feed feed = new Feed();
                                        feed.setTitle(line.getAttribute("title"));
                                        feed.setUrl(line.getAttribute("xmlUrl"));
                                        feed.setType(line.getAttribute("type"));

                                        this.feedService.addFeed(feed);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
