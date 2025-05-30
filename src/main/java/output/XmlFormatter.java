package output;

import model.Car;
import wrapper.CarXml;
import wrapper.CarsXmlWrapper;
import wrapper.PriceXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XmlFormatter implements OutputFormatter {

    @Override
    public void print(List<Car> cars) {
        try {
            List<CarXml> carXmlList = cars.stream().map(car -> {
                Map<String, Double> pricesMap = car.getPrices(); // 假设你 Car 类有 getPrices() 返回 Map<String, Double>

                // main price
                Double usdPrice = pricesMap.get("USD");
                PriceXml mainPrice = new PriceXml("USD", usdPrice);

                // other price
                List<PriceXml> otherPrices = pricesMap.entrySet().stream()
                        .filter(e -> !e.getKey().equals("USD"))
                        .map(e -> new PriceXml(e.getKey(), e.getValue()))
                        .collect(Collectors.toList());

                return new CarXml(
                        car.getBrand(),
                        car.getType(),
                        car.getModel(),
                        car.getReleaseDate(),
                        mainPrice,
                        otherPrices
                );
            }).collect(Collectors.toList());

            CarsXmlWrapper wrapper = new CarsXmlWrapper();
            wrapper.setCars(carXmlList);

            JAXBContext context = JAXBContext.newInstance(CarsXmlWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, System.out);

        } catch (Exception e) {
            System.err.println("output xml error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
