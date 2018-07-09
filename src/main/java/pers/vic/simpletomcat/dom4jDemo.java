package pers.vic.simpletomcat;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.XMLWriter;

/**
 * Create By Vic Xu on 7/9/2018
 */
public class dom4jDemo {

    public static void main(String[] args) throws Exception {
//        Document doc = new SAXReader().read(new File(""));
//        Element root = doc.getRootElement();
//        List<Element> booksEle = root.elements();
//        booksEle.forEach(e -> {
//            System.out.println(e.attributeValue("id"));
//            Element name = e.element("name");
//            name.attributeValue("id");
//            System.out.println(name.elementText("price"));
//        });
        Document doc = new DOMDocument();
        Element root = new DOMElement("books");
        doc.setRootElement(root);
        for (int i = 0 ; i< 10; i++) {
            Element name = new DOMElement("book");
            name.addAttribute("id", "book"  + i);
            name.setText("bookName" + i);
            Element price = new DOMElement("price");
            price.setText(i * 10 + "");
            Element author = new DOMElement("author");
            author.setText("author" + i);
            root.add(name);
            root.add(price);
            root.add(author);
        }
        XMLWriter writer = new XMLWriter(System.out);
        writer.write(doc);
    }
}
