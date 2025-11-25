package D1H8VP.domparse.hu;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class D1H8VPDomWrite {

    public static void main(String[] args) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            //  yökérelem létrehozása
            Element root = doc.createElement("Etterem_D1H8VP");
            root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaD1H8VP.xsd");
            doc.appendChild(root);

            //ADATOK HOZZÁADÁSA


            addPizzazo(doc, root, "P1", "10:00-22:00", "Miskolc Pizza", "06301234567", "miskolcpizza.hu");


            addBeszallito(doc, root, "B1", "info@beszal1.hu", "Beszallito1", "Kazincbarcika", "3700", "Joszerencset ut", "2");


            addFutar(doc, root, "F1", "P1", "James Bond", "70115452");


            addVevo(doc, root, "V1", "Vásár Ló", "20198772", "Kazincbarcika", "3700", "József attila utca", "7");


            addBankkartya(doc, root, "17846573498579", "V1", "OTP Bank", "2026-12-31", "Mastercard");


            addPizza(doc, root, "PIZ2", "P1", "3200", "Son goku",
                    new String[]{"Paradicsom", "Sajt", "Sonka", "Kukorica"},
                    new String[]{"32cm", "45cm"});


            addRendeles(doc, root, "R1", "V1", "PIZ2");


            // MENTÉS FÁJLBA ÉS KIÍRÁS

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Szép formázás beállítása (indentálás)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);

            // Kiírás konzolra
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            // Kiírás fájlba
            StreamResult fileResult = new StreamResult(new File("etterem_output.xml"));
            transformer.transform(source, fileResult);

            System.out.println("\n\nSikeres mentés: etterem_output.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SEGÉDFÜGGVÉNYEK A CSOMÓPONTOK LÉTREHOZÁSÁHOZ

    private static void addPizzazo(Document doc, Element root, String id, String nyitva, String nev, String tel, String web) {
        Element pizzazo = doc.createElement("Pizzazo");
        pizzazo.setAttribute("PizzazoID", id);

        pizzazo.appendChild(createElement(doc, "Nyitvatartas", nyitva));
        pizzazo.appendChild(createElement(doc, "Nev", nev));
        pizzazo.appendChild(createElement(doc, "Telefonszam", tel));
        pizzazo.appendChild(createElement(doc, "Weboldal", web));

        root.appendChild(pizzazo);
    }

    private static void addBeszallito(Document doc, Element root, String id, String email, String nev, String varos, String irsz, String utca, String hsz) {
        Element beszallito = doc.createElement("Beszallito");
        beszallito.setAttribute("BeszallitoID", id);

        beszallito.appendChild(createElement(doc, "Elerhetoseg", email));
        beszallito.appendChild(createElement(doc, "Nev", nev));

        // Cím struktúra
        Element cim = doc.createElement("Cim");
        cim.appendChild(createElement(doc, "Varos", varos));
        cim.appendChild(createElement(doc, "IRSZ", irsz));
        cim.appendChild(createElement(doc, "Utca", utca));
        cim.appendChild(createElement(doc, "Hazszam", hsz));
        beszallito.appendChild(cim);

        root.appendChild(beszallito);
    }

    private static void addFutar(Document doc, Element root, String id, String pid, String nev, String tel) {
        Element futar = doc.createElement("Futar");
        futar.setAttribute("FutarID", id);
        futar.setAttribute("PizzazoID", pid);

        futar.appendChild(createElement(doc, "Nev", nev));
        futar.appendChild(createElement(doc, "Telefonszam", tel));

        root.appendChild(futar);
    }

    private static void addVevo(Document doc, Element root, String id, String nev, String tel, String varos, String irsz, String utca, String hsz) {
        Element vevo = doc.createElement("Vevo");
        vevo.setAttribute("VevoID", id);

        vevo.appendChild(createElement(doc, "Nev", nev));
        vevo.appendChild(createElement(doc, "Telefonszam", tel));

        Element cim = doc.createElement("Cim");
        cim.appendChild(createElement(doc, "Varos", varos));
        cim.appendChild(createElement(doc, "IRSZ", irsz));
        cim.appendChild(createElement(doc, "Utca", utca));
        cim.appendChild(createElement(doc, "Hazszam", hsz));
        vevo.appendChild(cim);

        root.appendChild(vevo);
    }

    private static void addBankkartya(Document doc, Element root, String szam, String vid, String bank, String lejarat, String tipus) {
        Element kartya = doc.createElement("Bankkartya");
        kartya.setAttribute("Kartyaszam", szam);
        kartya.setAttribute("VevoID", vid);

        kartya.appendChild(createElement(doc, "Bank", bank));
        kartya.appendChild(createElement(doc, "Lejarati_datum", lejarat));
        kartya.appendChild(createElement(doc, "Tipus", tipus));

        root.appendChild(kartya);
    }

    private static void addPizza(Document doc, Element root, String id, String pid, String ar, String nev, String[] feltetek, String[] meretek) {
        Element pizza = doc.createElement("Pizza");
        pizza.setAttribute("PizzaID", id);
        pizza.setAttribute("PizzazoID", pid);

        pizza.appendChild(createElement(doc, "Teljes_ar", ar));
        pizza.appendChild(createElement(doc, "Pizza_neve", nev));

        // Feltétek lista
        Element feltetekNode = doc.createElement("Feltetek");
        for (String f : feltetek) {
            feltetekNode.appendChild(createElement(doc, "Feltet", f));
        }
        pizza.appendChild(feltetekNode);

        // Méretek lista
        Element meretekNode = doc.createElement("Meretek");
        for (String m : meretek) {
            meretekNode.appendChild(createElement(doc, "Meret", m));
        }
        pizza.appendChild(meretekNode);

        root.appendChild(pizza);
    }

    private static void addRendeles(Document doc, Element root, String rid, String vid, String pid) {
        Element rendeles = doc.createElement("Rendeles");
        rendeles.setAttribute("RendelesID", rid);
        rendeles.setAttribute("VevoID", vid);
        rendeles.setAttribute("PizzaID", pid);
        root.appendChild(rendeles);
    }

    // segédfüggvény szöveges elemek létrehozásához
    private static Element createElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}