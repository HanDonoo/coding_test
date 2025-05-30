package functest;

import model.Car;
import org.junit.Before;
import org.junit.Test;
import service.CarFilter;
import service.CarSorter;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarTest {

    private List<Car> cars;

    @Before
    public void setUp() {
        cars = new ArrayList<>();

        Car car1 = new Car();
        car1.setBrand("Toyota");
        car1.setPrices(new HashMap<String, Double>() {{ put("USD", 20000.0); put("EUR", 18000.0); }});
        car1.setType("SUV");
        car1.setReleaseDate(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        cars.add(car1);

        Car car2 = new Car();
        car2.setBrand("Toyota");
        car2.setPrices(new HashMap<String, Double>() {{ put("USD", 30000.0); put("EUR", 25000.0); }});
        car2.setType("Truck");
        car2.setReleaseDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        cars.add(car2);

        Car car3 = new Car();
        car3.setBrand("Honda");
        car3.setPrices(new HashMap<String, Double>() {{ put("USD", 15000.0); put("JPY", 1600000.0); }});
        car3.setType("Sedan");
        car3.setReleaseDate(new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime());
        cars.add(car3);
    }

    @Test
    public void testFilterByBrandAndPrice() {
        List<Car> result = CarFilter.filterByBrandAndPrice(cars, "toyota", 18000.0, 25000.0, "USD");

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getBrand());
        assertEquals(20000.0, result.get(0).getPrices().get("USD"), 0.01);
    }

    @Test
    public void testSortByPriceDesc() {
        CarSorter.sortByPriceDesc(cars, "USD");

        assertEquals(30000.0, cars.get(0).getPrices().get("USD"), 0.01);
        assertEquals(20000.0, cars.get(1).getPrices().get("USD"), 0.01);
        assertEquals(15000.0, cars.get(2).getPrices().get("USD"), 0.01);
    }

    @Test
    public void testSortByReleaseDateDesc() {
        CarSorter.sortByReleaseDateDesc(cars);

        assertTrue(cars.get(0).getReleaseDate().after(cars.get(1).getReleaseDate()));
        assertTrue(cars.get(1).getReleaseDate().after(cars.get(2).getReleaseDate()));
    }

    @Test
    public void testSortByTypeAndCurrencyPriceDesc() {
        // SUV=EUR, Sedan=JPY, Truck=USD
        CarSorter.sortByTypeAndCurrencyPriceDesc(cars);

        assertEquals("SUV", cars.get(0).getType());
        assertEquals("Sedan", cars.get(1).getType());
        assertEquals("Truck", cars.get(2).getType());
    }
}
