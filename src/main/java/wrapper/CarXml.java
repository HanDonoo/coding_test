package wrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

public class CarXml {
    private String brand;
    private String type;
    private String model;
    private Date releaseDate;

    private PriceXml mainPrice;

    private List<PriceXml> prices;

    public CarXml() {}

    public CarXml(String brand, String type, String model, Date releaseDate, PriceXml mainPrice, List<PriceXml> prices) {
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.releaseDate = releaseDate;
        this.mainPrice = mainPrice;
        this.prices = prices;
    }

    @XmlElement
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    // price currency="USD"
    @XmlElement(name = "price")
    public PriceXml getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(PriceXml mainPrice) {
        this.mainPrice = mainPrice;
    }

    // other prices
    @XmlElementWrapper(name = "prices")
    @XmlElement(name = "price")
    public List<PriceXml> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceXml> prices) {
        this.prices = prices;
    }
}
