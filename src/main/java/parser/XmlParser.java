package parser;

import model.Car;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    /**
     * parse the xml data
     * @param xmlFile
     * @return
     * @throws Exception
     */
    public List<Car> parseCarsFromXml(File xmlFile) throws Exception {
        List<Car> cars = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList carNodes = doc.getElementsByTagName("car");

        for (int i = 0; i < carNodes.getLength(); i++) {
            Element carElem = (Element) carNodes.item(i);
            Car car = new Car();
            car.setType(getTagValue(carElem, "type"));
            car.setModel(getTagValue(carElem, "model"));

            Element priceElem = (Element) carElem.getElementsByTagName("price").item(0);
            if (priceElem != null) {
                String currency = priceElem.getAttribute("currency");
                double price = Double.parseDouble(priceElem.getTextContent());
                car.addPrice(currency, price);
            }

            NodeList pricesNodeList = carElem.getElementsByTagName("prices");
            if (pricesNodeList.getLength() > 0) {
                Element pricesElem = (Element) pricesNodeList.item(0);
                NodeList priceList = pricesElem.getElementsByTagName("price");
                for (int j = 0; j < priceList.getLength(); j++) {
                    Element pElem = (Element) priceList.item(j);
                    String currency = pElem.getAttribute("currency");
                    double price = Double.parseDouble(pElem.getTextContent());
                    car.addPrice(currency, price);
                }
            }

            car.setBrand(null);
            car.setReleaseDate(null);

            cars.add(car);
        }
        return cars;
    }

    /**
     * read data from the tag
     */
    private static String getTagValue(Element element, String tagName) {
        NodeList nlList = element.getElementsByTagName(tagName);
        if (nlList != null && nlList.getLength() > 0) {
            Node node = nlList.item(0);
            return node.getTextContent();
        }
        return null;
    }
}
