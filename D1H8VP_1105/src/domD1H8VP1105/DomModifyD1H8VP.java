package domD1H8VP1105;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DomModifyD1H8VP {

    public static void main(String[] args) {


        String targetId = "01";
        String ujKeresztnev = "Laszlo";
        String ujVezeteknev = "Bodnar";

        try {

            File xmlFile = new File("HallgatoD1H8VP.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList hallgatoNodeList = doc.getElementsByTagName("hallgato");

            for (int i = 0; i < hallgatoNodeList.getLength(); i++) {
                Node hallgatoNode = hallgatoNodeList.item(i);

                if (hallgatoNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element hallgatoElement = (Element) hallgatoNode;


                    String id = hallgatoElement.getAttribute("id");

                    if (id.equals(targetId)) {
                        System.out.println("Az id='" + targetId + "' elem módosítása");
                        Node keresztnevNode = hallgatoElement.getElementsByTagName("keresztnev").item(0);
                        keresztnevNode.setTextContent(ujKeresztnev);
                        Node vezeteknevNode = hallgatoElement.getElementsByTagName("vezeteknev").item(0);
                        vezeteknevNode.setTextContent(ujVezeteknev);
                        break;
                    }
                }
            }

            System.out.println("\n A módosítas kiiras console-ra");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}