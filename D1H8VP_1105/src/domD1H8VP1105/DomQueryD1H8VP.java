package domD1H8VP1105;

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

public class DomQueryD1H8VP {

    public static void main(String[] args) {
        String xmlFile = "HallgatoD1H8VP.xml";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new File(xmlFile));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            System.out.println("Gyökér elem: " + root.getNodeName());
            System.out.println("---");


            NodeList hallgatoList = doc.getElementsByTagName("hallgato");

            for (int i = 0; i < hallgatoList.getLength(); i++) {
                Node hallgatoNode = hallgatoList.item(i);

                if (hallgatoNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element hallgatoElement = (Element) hallgatoNode;

                    String vezeteknev = hallgatoElement.getElementsByTagName("vezeteknev")
                            .item(0)
                            .getTextContent();

                    System.out.println("Vezetéknév: " + vezeteknev);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}