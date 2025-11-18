package D1H8VP.domparse.hu;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class D1H8VPDomRead {

    public static void main(String[] args) {
        try {
             //DOM Parser
            File xmlFile = new File("XML_FÉLÉVES_D1H8VP/Etterem_D1H8VP.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);


            doc.getDocumentElement().normalize();

            //  StringBuilder
            StringBuilder output = new StringBuilder();

            output.append("Étterem Feldolgozás D1H8VP \n");

            //  Elemek feldolgozása
            processElements(doc, output, "Pizzazo");
            processElements(doc, output, "Beszallito");
            processElements(doc, output, "Futar");
            processElements(doc, output, "Vevo");
            processElements(doc, output, "Bankkartya");
            processElements(doc, output, "Pizza");
            processElements(doc, output, "Rendeles");

            // Kiírás a konzolra
            System.out.println(output.toString());

            //Kiírás fájlba
            try (PrintWriter writer = new PrintWriter(new FileWriter("etterem_read_output_D1H8VP.xml"))) {
                writer.print(output.toString());
            }
            System.out.println("\nAdatok sikeresen kiírva az 'etterem_read_output_D1H8VP.' fájlba.");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    // Feldolgozo metodus
    private static void processElements(Document doc, StringBuilder sb, String tagName) {
        NodeList nodeList = doc.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            sb.append("\n=== ").append(tagName.toUpperCase()).append(" (").append(nodeList.getLength()).append(" db) ===\n");
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                sb.append("--------------------------------------\n");

                // Speciális formazas
                switch (tagName) {
                    case "Pizzazo":    formatPizzazo(element, sb);    break;
                    case "Beszallito": formatBeszallito(element, sb); break;
                    case "Futar":      formatFutar(element, sb);      break;
                    case "Vevo":       formatVevo(element, sb);       break;
                    case "Bankkartya": formatBankkartya(element, sb); break;
                    case "Pizza":      formatPizza(element, sb);      break;
                    case "Rendeles":   formatRendeles(element, sb);   break;
                }
            }
        }
    }

    // Segedfuggvenyek szebb formazashoz

    private static void formatPizzazo(Element element, StringBuilder sb) {
        sb.append("Pizzázó (ID: ").append(element.getAttribute("PizzazoID")).append(")\n");
        sb.append("  Név: ").append(getElementText(element, "Nev")).append("\n");
        sb.append("  Nyitvatartás: ").append(getElementText(element, "Nyitvatartas")).append("\n");
        sb.append("  Telefon: ").append(getElementText(element, "Telefonszam")).append("\n");
        sb.append("  Web: ").append(getElementText(element, "Weboldal")).append("\n");
    }

    private static void formatBeszallito(Element element, StringBuilder sb) {
        sb.append("Beszállító (ID: ").append(element.getAttribute("BeszallitoID")).append(")\n");
        sb.append("  Név: ").append(getElementText(element, "Nev")).append("\n");
        sb.append("  Elérhetőség: ").append(getElementText(element, "Elerhetoseg")).append("\n");
        sb.append("  Cím: ").append(formatCim(element)).append("\n");
    }

    private static void formatFutar(Element element, StringBuilder sb) {
        sb.append("Futár (ID: ").append(element.getAttribute("FutarID")).append(")\n");
        sb.append("  Pizzázó ID: ").append(element.getAttribute("PizzazoID")).append("\n");
        sb.append("  Név: ").append(getElementText(element, "Nev")).append("\n");
        sb.append("  Telefon: ").append(getElementText(element, "Telefonszam")).append("\n");
    }

    private static void formatVevo(Element element, StringBuilder sb) {
        sb.append("Vevő (ID: ").append(element.getAttribute("VevoID")).append(")\n");
        sb.append("  Név: ").append(getElementText(element, "Nev")).append("\n");
        sb.append("  Telefon: ").append(getElementText(element, "Telefonszam")).append("\n");
        sb.append("  Cím: ").append(formatCim(element)).append("\n");
    }

    private static void formatBankkartya(Element element, StringBuilder sb) {
        sb.append("Bankkártya (Szám: ").append(element.getAttribute("Kartyaszam")).append(")\n");
        sb.append("  Vevő ID: ").append(element.getAttribute("VevoID")).append("\n");
        sb.append("  Bank: ").append(getElementText(element, "Bank")).append("\n");
        sb.append("  Lejárat: ").append(getElementText(element, "Lejarati_datum")).append("\n");
        sb.append("  Típus: ").append(getElementText(element, "Tipus")).append("\n");
    }

    private static void formatPizza(Element element, StringBuilder sb) {
        sb.append("Pizza (ID: ").append(element.getAttribute("PizzaID")).append(")\n");
        sb.append("  Pizzázó ID: ").append(element.getAttribute("PizzazoID")).append("\n");
        sb.append("  Név: ").append(getElementText(element, "Pizza_neve")).append("\n");
        sb.append("  Ár: ").append(getElementText(element, "Teljes_ar")).append(" Ft\n");
        sb.append("  Feltétek: ").append(formatList(element, "Feltetek", "Feltet")).append("\n");
        sb.append("  Méretek: ").append(formatList(element, "Meretek", "Meret")).append("\n");
    }

    private static void formatRendeles(Element element, StringBuilder sb) {
        sb.append("Rendelés (ID: ").append(element.getAttribute("RendelesID")).append(")\n");
        sb.append("  Vevő ID: ").append(element.getAttribute("VevoID")).append("\n");
        sb.append("  Pizza ID: ").append(element.getAttribute("PizzaID")).append("\n");
    }


    //  segédfüggvények

    //gyokerelem szoveges tartalmanak visszaadasa
    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                return node.getFirstChild().getNodeValue().trim();
            }
        }
        return "";
    }

    //Cim blokk formazasa
    private static String formatCim(Element parent) {
        NodeList cimList = parent.getElementsByTagName("Cim");
        if (cimList.getLength() > 0) {
            Element cimElement = (Element) cimList.item(0);
            String irsz = getElementText(cimElement, "IRSZ");
            String varos = getElementText(cimElement, "Varos");
            String utca = getElementText(cimElement, "Utca");
            String hazszam = getElementText(cimElement, "Hazszam");
            // Strukturáltan összerakja, felesleges szóközök nélkül
            return irsz + " " + varos + ", " + utca + " " + hazszam;
        }
        return "N/A";
    }

    //Lista adatait gyujti
    private static String formatList(Element parent, String listTagName, String itemTagName) {
        NodeList listNodes = parent.getElementsByTagName(listTagName);
        if (listNodes.getLength() > 0) {
            Element listElement = (Element) listNodes.item(0);
            NodeList itemNodes = listElement.getElementsByTagName(itemTagName);
            StringBuilder items = new StringBuilder();
            for (int i = 0; i < itemNodes.getLength(); i++) {
                items.append(itemNodes.item(i).getTextContent().trim());
                if (i < itemNodes.getLength() - 1) {
                    items.append(", ");
                }
            }
            return items.toString();
        }
        return "N/A";
    }
}