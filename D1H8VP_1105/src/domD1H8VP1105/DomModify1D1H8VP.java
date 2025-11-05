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
import java.io.StringWriter;

public class DomModify1D1H8VP {

    public static void main(String[] args) {
        String inputXmlFile = "D1H8VP_orarend.xml";

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc1 = builder.parse(new File(inputXmlFile));
            doc1.getDocumentElement().normalize();

            NodeList oraList1 = doc1.getElementsByTagName("ora");

            Node oraToModify = oraList1.item(0);

            if (oraToModify != null && oraToModify.getNodeType() == Node.ELEMENT_NODE) {
                Element oraadoElement = doc1.createElement("oraado");
                oraadoElement.setTextContent("Bodnar Laszlo");

                oraToModify.appendChild(oraadoElement);

                System.out.println("Az 'o1' órához <oraado> elem hozzáadva.");
            }

            String outputXmlFile = "orarendModify1Neptunkod.xml";
            writeXml(doc1, new File(outputXmlFile));

            System.out.println("\nMódosított XML konzolra írva ");
            writeXmlToConsole(doc1);


            Document doc2 = builder.parse(new File(inputXmlFile));
            doc2.getDocumentElement().normalize();

            NodeList oraList2 = doc2.getElementsByTagName("ora");

            for (int i = 0; i < oraList2.getLength(); i++) {
                Node oraNode = oraList2.item(i);

                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;

                    String tipus = oraElement.getAttribute("tipus");

                    if ("Gyakorlat".equals(tipus)) {
                        oraElement.setAttribute("tipus", "Eloadas");
                    }
                }
            }

            System.out.println("Minden 'Gyakorlat' típus 'Eloadas'-ra.");
            System.out.println("\n Módosított XML konzolra  ");


            writeXmlToConsole(doc2);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }


    private static void writeXmlToConsole(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();


        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult consoleResult = new StreamResult(System.out);

        transformer.transform(source, consoleResult);
    }


    private static void writeXml(Document doc, File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();


        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult fileResult = new StreamResult(file);

        transformer.transform(source, fileResult);
    }
}