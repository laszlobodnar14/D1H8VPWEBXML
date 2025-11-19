package D1H8VP.domparse.hu;

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

public class D1H8VPDomModify {

    public static void main(String[] args) {
        File inputFile = new File("XML_FÉLÉVES_D1H8VP/Etterem_D1H8VP.xml");
        File outputFile = new File("Etterem_D1H8VP_modositasok.xml");

        try {
            // XML beolvasása
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            StringBuilder report = new StringBuilder();
            report.append("DOM MÓDOSÍTÁSOK D1H8VP\n");



            modify1_ArModositas(doc, report);
            modify2_FutarAthelyezes(doc, report);
            modify3_UjElemHozzaadasa(doc, report);
            modify4_UjListaelem(doc, report);
            modify5_ElemTorlese(doc, report);

             // kiírása konzolra
            System.out.println(report.toString());
            //modositas kiirasa
            saveXml(doc, outputFile);

            System.out.println("\nA módosított XML mentve ide: " + outputFile.getName() + "'");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }


     // Átírja a PIZ1 pizza árát 2990-re.

    private static void modify1_ArModositas(Document doc, StringBuilder report) {
        report.append("\n 1. MÓDOSÍTÁS: PIZ1 pizza árának frissítése \n");
        NodeList pizzaList = doc.getElementsByTagName("Pizza");
        for (int i = 0; i < pizzaList.getLength(); i++) {
            Element pizza = (Element) pizzaList.item(i);
            if ("PIZ1".equals(pizza.getAttribute("PizzaID"))) {
                Node arNode = pizza.getElementsByTagName("Teljes_ar").item(0);
                String regiAr = arNode.getTextContent();
                arNode.setTextContent("2990");
                report.append(" PIZ1 (Margarita) ára módosítva.\n");
                report.append("Régi ár:").append(regiAr).append(", Új ár: 2990\n");
                return;
            }
        }
        report.append("  - HIBA: PIZ1 pizza nem található.\n");
    }


     // Áthelyezi F1" futárt a P1 pizzázótól a P2-be

    private static void modify2_FutarAthelyezes(Document doc, StringBuilder report) {
        report.append("\n2. MÓDOSÍTÁS: F1 futár masik pizzazohoz \n");
        NodeList futarList = doc.getElementsByTagName("Futar");
        for (int i = 0; i < futarList.getLength(); i++) {
            Element futar = (Element) futarList.item(i);
            if ("F1".equals(futar.getAttribute("FutarID"))) {
                String regiPizzazo = futar.getAttribute("PizzazoID");
                futar.setAttribute("PizzazoID", "P2");
                report.append(" SIKER: F1 (James Bond) futár áthelyezve.\n");
                report.append("Régi PizzazoID: ").append(regiPizzazo).append(", Új PizzazoID: P2\n");
                return;
            }
        }
        report.append(" HIBA: F1 futár nem található.\n");
    }


     // Hozzáadunk egy <Email> elemet V1 vevőhöz.

    private static void modify3_UjElemHozzaadasa(Document doc, StringBuilder report) {
        report.append("\n3. MÓDOSÍTÁS: Email cím hozzáadása V1 vevőhöz \n");
        NodeList vevoList = doc.getElementsByTagName("Vevo");
        for (int i = 0; i < vevoList.getLength(); i++) {
            Element vevo = (Element) vevoList.item(i);
            if ("V1".equals(vevo.getAttribute("VevoID"))) {
                Element email = doc.createElement("Email");
                email.setTextContent("vasar.lo@email.com");
                vevo.appendChild(email);
                report.append("SIKER: '<Email>vasar.lo@email.com</Email>' hozzáadva V1 vevőhöz.\n");
                return;
            }
        }
        report.append(" HIBA: V1 vevő nem található.\n");
    }

    //gomba feletet hozzaadasa a pizzahoz
    private static void modify4_UjListaelem(Document doc, StringBuilder report) {
        report.append("\n4. MÓDOSÍTÁS: Új feltét  hozzáadása a PIZ1 pizzához \n");
        NodeList pizzaList = doc.getElementsByTagName("Pizza");
        for (int i = 0; i < pizzaList.getLength(); i++) {
            Element pizza = (Element) pizzaList.item(i);
            if ("PIZ1".equals(pizza.getAttribute("PizzaID"))) {

                Node feltetekNode = pizza.getElementsByTagName("Feltetek").item(0);

                Element ujFeltet = doc.createElement("Feltet");
                ujFeltet.setTextContent("Gomba");

                feltetekNode.appendChild(ujFeltet);
                report.append("SIKER: '<Feltet>Gomba</Feltet>' hozzáadva PIZ1 pizzához.\n");
                return;
            }
        }
        report.append(" HIBA: PIZ1 pizza nem található.\n");
    }

    //v2 kartyaszam torlese
    private static void modify5_ElemTorlese(Document doc, StringBuilder report) {
        report.append("\n 5. MÓDOSÍTÁS: 'V2' vevő bankkártyájának törlése \n");
        NodeList kartyaList = doc.getElementsByTagName("Bankkartya");


        for (int i = kartyaList.getLength() - 1; i >= 0; i--) {
            Element kartya = (Element) kartyaList.item(i);
            if ("V2".equals(kartya.getAttribute("VevoID"))) {
                String toroltKartyaSzam = kartya.getAttribute("Kartyaszam");
                Node szulo = kartya.getParentNode();
                szulo.removeChild(kartya);

                report.append("SIKER: V2 vevő (Kartyaszam: ...").append(toroltKartyaSzam.substring(toroltKartyaSzam.length() - 4)).append(") bankkártyája törölve.\n");
                return;
            }
        }
        report.append(" HIBA: V2 vevőhöz tartozó bankkártya nem található.\n");
    }

    //Segedfuggveny a menteshez
    private static void saveXml(Document doc, File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        //kimenet beallitasa
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);
    }
}