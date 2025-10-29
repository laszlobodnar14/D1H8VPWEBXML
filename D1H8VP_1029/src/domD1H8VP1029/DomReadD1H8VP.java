package domD1H8VP1029;

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

public class DomReadD1H8VP {
    public static void main(String[] argv) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File("HallgatoD1H8VP.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document HallgatoD1H8VP = dBuilder.parse(xmlFile);
        HallgatoD1H8VP.getDocumentElement().normalize();

        System.out.println("Root element :" + HallgatoD1H8VP.getDocumentElement().getNodeName());
        NodeList nList = HallgatoD1H8VP.getElementsByTagName("hallgato");

        System.out.println("----------------------------");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            System.out.println("\nAktualis elem:" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) nNode;

                String hid = elem.getAttribute("id");

                Node node1 = elem.getElementsByTagName("keresztnev").item(0);
                String kname = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("vezeteknev").item(0);
                String vname = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("foglalkozas").item(0);
                String fname = node3.getTextContent();

                System.out.println("Hallgato id: " + hid);
                System.out.println("Hallgato keresztneve: " + kname);
                System.out.println("Hallgato vezetekneve: " + vname);
                System.out.println("Hallgato foglalkozas: " + fname);
            }
        }
    }
}