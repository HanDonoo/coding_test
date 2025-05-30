package service;

import enums.CurrencyType;
import model.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CarSorter {

    public static void sortByReleaseDateDesc(List<Car> cars) {
        cars.sort(Comparator.comparing(Car::getReleaseDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
    }

    public static void sortByPriceDesc(List<Car> cars, String currency) {
        cars.sort((c1, c2) -> {
            Double p1 = c1.getPriceByCurrency(currency);
            Double p2 = c2.getPriceByCurrency(currency);
            if (p1 == null) p1 = 0d;
            if (p2 == null) p2 = 0d;
            return p2.compareTo(p1);
        });
    }

    public static void sortByTypeAndCurrencyPriceDesc(List<Car> cars) {
        List<Car> suvList = new ArrayList<>();
        List<Car> sedanList = new ArrayList<>();
        List<Car> truckList = new ArrayList<>();

        for (Car car : cars) {
            switch (car.getType().toLowerCase()) {
                case "suv":
                    suvList.add(car);
                    break;
                case "sedan":
                    sedanList.add(car);
                    break;
                case "truck":
                    truckList.add(car);
                    break;
            }
        }

        Comparator<Car> suvComparator = Comparator.comparing(
                c -> Optional.ofNullable(c.getPriceByCurrency(CurrencyType.EUR.name())).orElse(0.0), Comparator.reverseOrder());
        Comparator<Car> sedanComparator = Comparator.comparing(
                c -> Optional.ofNullable(c.getPriceByCurrency(CurrencyType.JPY.name())).orElse(0.0), Comparator.reverseOrder());
        Comparator<Car> truckComparator = Comparator.comparing(
                c -> Optional.ofNullable(c.getPriceByCurrency(CurrencyType.USD.name())).orElse(0.0), Comparator.reverseOrder());

        suvList.sort(suvComparator);
        sedanList.sort(sedanComparator);
        truckList.sort(truckComparator);

        cars.clear();
        cars.addAll(suvList);
        cars.addAll(sedanList);
        cars.addAll(truckList);
    }

}
