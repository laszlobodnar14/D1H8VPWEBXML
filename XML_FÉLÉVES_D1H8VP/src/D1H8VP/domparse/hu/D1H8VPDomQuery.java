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
import java.util.HashSet;
import java.util.Set;

public class D1H8VPDomQuery {

    public static void main(String[] args) {
        try {
            //DOM Parser
            File xmlFile = new File("XML_FÉLÉVES_D1H8VP/Etterem_D1H8VP.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // StringBuilder
            StringBuilder output = new StringBuilder();
            output.append("DOM LEKÉRDEZÉSEK D1H8VP \n");

            //Lekérdezések
            query1_MindenPizzazoNeve(doc, output);
            query2_KazincbarcikaiBeszallitok(doc, output);
            query3_P1Futarai(doc, output);
            query4_PizzakSajtFeltettel(doc, output);
            query5_KiRendeltMagyarosPizzat(doc, output);


            System.out.println(output.toString());

            // Kiírás fájlba
            try (PrintWriter writer = new PrintWriter(new FileWriter("etterem_lekerdezesek_D1H8VP.xml"))) {
                writer.print(output.toString());
            }
            System.out.println("\nLekérdezések sikeresen kiírva  etterem_lekerdezesek_D1H8VP.xml ");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    //LEKÉRDEZÉSEK


      //LEKÉRDEZÉS1: Listázza ki minden pizzázó nevét.

    private static void query1_MindenPizzazoNeve(Document doc, StringBuilder sb) {
        sb.append("\n1. LEKÉRDEZÉS: Minden pizzázó neve \n");
        NodeList pizzazoNodeList = doc.getElementsByTagName("Pizzazo");

        for (int i = 0; i < pizzazoNodeList.getLength(); i++) {
            Node node = pizzazoNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element pizzazo = (Element) node;
                String nev = getElementText(pizzazo, "Nev");
                sb.append("  - ").append(nev).append("\n");
            }
        }
    }


      //LEKÉRDEZÉS : Listázza ki Kazincbarcika városban beszállítók nevét.

    private static void query2_KazincbarcikaiBeszallitok(Document doc, StringBuilder sb) {
        sb.append("\n 2. LEKÉRDEZÉS: Kazincbarcikai beszállítók \n");
        NodeList beszallitoNodeList = doc.getElementsByTagName("Beszallito");

        for (int i = 0; i < beszallitoNodeList.getLength(); i++) {
            Node node = beszallitoNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element beszallito = (Element) node;

                // Cim vasros elem
                Element cim = (Element) beszallito.getElementsByTagName("Cim").item(0);
                String varos = getElementText(cim, "Varos");

                if ("Kazincbarcika".equals(varos)) {
                    String nev = getElementText(beszallito, "Nev");
                    sb.append("  - ").append(nev).append(" (").append(varos).append(")\n");
                }
            }
        }
    }


     // LEKÉRDEZÉS : Listázd ki a P1 PizzazoIDhez pizzázóhoz tartozó futárok nevét.

    private static void query3_P1Futarai(Document doc, StringBuilder sb) {
        sb.append("\n 3. LEKÉRDEZÉS: A P1 pizzázó futárai \n");
        NodeList futarNodeList = doc.getElementsByTagName("Futar");

        for (int i = 0; i < futarNodeList.getLength(); i++) {
            Node node = futarNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element futar = (Element) node;


                String pizzazoID = futar.getAttribute("PizzazoID");

                if ("P1".equals(pizzazoID)) {
                    String nev = getElementText(futar, "Nev");
                    sb.append("  - ").append(nev).append(" (ID: ").append(futar.getAttribute("FutarID")).append(")\n");
                }
            }
        }
    }


     // LEKÉRDEZÉS: Listázza ki az összes olyan pizza nevét amelyiken van sajt feltét.

    private static void query4_PizzakSajtFeltettel(Document doc, StringBuilder sb) {
        sb.append("\n 4. LEKÉRDEZÉS: Pizzák sajt feltéttel \n");
        NodeList pizzaNodeList = doc.getElementsByTagName("Pizza");

        for (int i = 0; i < pizzaNodeList.getLength(); i++) {
            Node pizzaNode = pizzaNodeList.item(i);
            if (pizzaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element pizza = (Element) pizzaNode;
                String pizzaNev = getElementText(pizza, "Pizza_neve");

                // Feltetek ellenorzese
                Element feltetekElement = (Element) pizza.getElementsByTagName("Feltetek").item(0);
                NodeList feltetList = feltetekElement.getElementsByTagName("Feltet");

                for (int j = 0; j < feltetList.getLength(); j++) {
                    String feltet = feltetList.item(j).getTextContent();
                    if ("Sajt".equals(feltet.trim())) {
                        sb.append("  - ").append(pizzaNev).append("\n");
                        break;
                    }
                }
            }
        }
    }


     // LEKÉRDEZÉS : Listázd ki a vevő nevét, aki magyaros pizzát rendel.

    private static void query5_KiRendeltMagyarosPizzat(Document doc, StringBuilder sb) {
        sb.append("\n 5. LEKÉRDEZÉS: Ki rendelt magyaros pizzát?\n");

        // MAgyaros pizzaID
        String magyarosPizzaID = null;
        NodeList pizzaList = doc.getElementsByTagName("Pizza");
        for (int i = 0; i < pizzaList.getLength(); i++) {
            Element pizza = (Element) pizzaList.item(i);
            if ("Magyaros".equals(getElementText(pizza, "Pizza_neve"))) {
                magyarosPizzaID = pizza.getAttribute("PizzaID");
                break;
            }
        }

        if (magyarosPizzaID == null) {
            sb.append("  (Nem található magyaros pizza az adatbázisban.)\n");
            return;
        }
        sb.append("  (A magyaros pizza ID-ja: ").append(magyarosPizzaID).append(")\n");

        // VEvoID aki rendelt pizazat
        Set<String> vevoIDk = new HashSet<>();
        NodeList rendelesList = doc.getElementsByTagName("Rendeles");
        for (int i = 0; i < rendelesList.getLength(); i++) {
            Element rendeles = (Element) rendelesList.item(i);
            if (magyarosPizzaID.equals(rendeles.getAttribute("PizzaID"))) {
                vevoIDk.add(rendeles.getAttribute("VevoID"));
            }
        }

        if (vevoIDk.isEmpty()) {
            sb.append("  (Senki nem rendelt magyaros pizzát.)\n");
            return;
        }

        //VevoIDhez tartozo nev
        NodeList vevoList = doc.getElementsByTagName("Vevo");
        for (int i = 0; i < vevoList.getLength(); i++) {
            Element vevo = (Element) vevoList.item(i);
            if (vevoIDk.contains(vevo.getAttribute("VevoID"))) {
                sb.append("  - ").append(getElementText(vevo, "Nev")).append("\n");
            }
        }
    }


    // SEGÉDFÜGGVÉNY

    //Adott nevu gyerekelem tartalma
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
}