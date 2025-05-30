package wrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
public class CarsXmlWrapper {
    private List<CarXml> cars;

    public CarsXmlWrapper() {}

    @XmlElement(name = "car")
    public List<CarXml> getCars() {
        return cars;
    }

    public void setCars(List<CarXml> cars) {
        this.cars = cars;
    }
}
