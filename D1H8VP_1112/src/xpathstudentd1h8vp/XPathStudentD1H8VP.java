package xpathstudentd1h8vp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathStudentD1H8VP {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("D1H8VP_1112/studentD1H8VP.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            cleanDocument(document.getDocumentElement());

            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression;
            //System.out.println("1. feladat");
            //expression = "class/student";

            //System.out.println("2. feladat");
            //expression = "class/student[@id=02]";

            //System.out.println("3. feladat");
            //expression = "//student";

            //System.out.println("4. feladat");
            //expression = "class/student[position()=2]";

            //System.out.println("5. feladat");
            //expression = "class/student[last()]";

            //System.out.println("6. Feladat");
            // expression = "class/student[last()-1]";

            //System.out.println("7. feladat");
            //expression = "class/student[position()<3]";

            //   System.out.println("8. feladat");
            // expression = "class/*";

            //System.out.println("9. feladat");
            //expression = "class/student[@*]";

            //System.out.println("10. feladat");
            // expression = "*";

            //System.out.println("11. feladat");
            //expression = "class/student[kor>20]";

            System.out.println("12. feladat");
            expression = "class/student/keresztnev | class/student/vezeteknev";



            NodeList result = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i< result.getLength(); i++) {
                Node node = result.item(i);
                System.out.println(formatElement(node, 0));

            }



            System.out.println("Módosítás a 01-es hallgatora:");

            String kNameExpr = "//student[@id='01']/keresztnev";
            Node kNameNode = (Node) xPath.compile(kNameExpr).evaluate(document, XPathConstants.NODE);

            if (kNameNode != null) {
                kNameNode.setTextContent("Kalman");

                Node studentNode = kNameNode.getParentNode();

                System.out.println(formatElement(studentNode, 0));

            } else {
                System.out.println("Nem található a 01-es id.");
            }



        } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }
    private static void cleanDocument(Node root) {
        NodeList nodes = root.getChildNodes();
        List<Node> toDelete = new ArrayList<>();
        for(int i=0; i<nodes.getLength(); i++) {
            if(nodes.item(i).getNodeType()==Node.TEXT_NODE && nodes.item(i).getTextContent().strip().equals("")) {
                toDelete.add(nodes.item(i));
            }else {
                cleanDocument(nodes.item(i));
            }
        }
        for(Node node: toDelete) {
            root.removeChild(node);
        }
    }
    public static String formatElement(Node node, int indent) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return "";
        }
        String output = "\n";
        output += indent(indent)+"<" + ((Element) node).getTagName();
        if (node.hasAttributes()) {
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                Node attribute = node.getAttributes().item(i);
                output += " " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"";
            }
        }
        output += ">";
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if(children.item(i).getNodeType()==Node.TEXT_NODE) return output+=node.getTextContent()+"</" + ((Element) node).getTagName() + ">";
            if(children.item(i).getNodeType()==Node.ELEMENT_NODE)output+=formatElement(children.item(i), indent+1);
        }
        output+="\n"+indent(indent)+"</" + ((Element) node).getTagName() + ">";

        return output;
    }

    private static String indent(int indent) {
        return "   ".repeat(indent);
    }
}