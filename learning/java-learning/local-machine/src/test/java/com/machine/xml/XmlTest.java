package com.machine.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by michael on 2017-07-17.
 */
public class XmlTest {

    @Test
    public void xml() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read("E:\\repository\\github\\remoting\\learning\\java-learning\\local-machine\\pom.xml");

        Element root = document.getRootElement();
        for(Iterator<Element> iterator = root.elementIterator(); iterator.hasNext();){
            System.out.println(iterator.next());
        }
    }

    @Test
    public void jackson(){
        XmlMapper mapper = new XmlMapper();
    }
}
