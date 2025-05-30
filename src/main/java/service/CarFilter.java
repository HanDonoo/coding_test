package service;

import model.Car;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CarFilter {

    public static List<Car> filterByBrandAndPrice(List<Car> cars, String brand, Double minPrice, Double maxPrice, String currency) {
        return cars.stream()
                .filter(car -> brand == null || brand.equalsIgnoreCase(car.getBrand()))
                .filter(car -> {
                    Double price = car.getPriceByCurrency(currency);
                    if (price == null) return false;
                    if (minPrice != null && price < minPrice) return false;
                    if (maxPrice != null && price > maxPrice) return false;
                    return true;
                })
                .collect(Collectors.toList());
    }

    public static List<Car> filterByBrandAndReleaseDate(List<Car> cars, String brand, Date fromDate, Date toDate) {
        return cars.stream()
                .filter(car -> brand == null || brand.equalsIgnoreCase(car.getBrand()))
                .filter(car -> {
                    Date rd = car.getReleaseDate();
                    if (rd == null) return false;
                    if (fromDate != null && rd.before(fromDate)) return false;
                    if (toDate != null && rd.after(toDate)) return false;
                    return true;
                })
                .collect(Collectors.toList());
    }


}
