package app;

import enums.CurrencyType;
import model.Car;
import output.OutputFactory;
import output.OutputFormatter;
import parser.CsvParser;
import parser.XmlParser;
import service.CarFilter;
import service.CarSorter;
import util.InputUtil;

import java.io.File;
import java.util.*;

public class CarApp {

    private static final String XML_PATH = "data/carsType.xml";
    private static final String CSV_PATH = "data/CarsBrand.csv";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 1. load data
            File xmlFile = new File(XML_PATH);
            File csvFile = new File(CSV_PATH);

            XmlParser xmlParser = new XmlParser();
            List<Car> cars = xmlParser.parseCarsFromXml(xmlFile);

            CsvParser csvParser = new CsvParser();
            csvParser.parseBrandAndReleaseDateFromCsv(csvFile, cars);

            // 2. run loop
            while (true) {
                System.out.println("\n=== Car Query System ===");
                System.out.println("Type 'exit' to quit or press Enter to start a new query:");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program...");
                    break;
                }

                runQuery(scanner, cars);
            }

        } catch (Exception e) {
            System.err.println("an error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runQuery(Scanner scanner, List<Car> cars) {
        // all brands
        Set<String> validBrands = new HashSet<>();
        for (Car car : cars) {
            if (car.getBrand() != null) {
                validBrands.add(car.getBrand().trim().toLowerCase());
            }
        }

        // brand input
        String brand;
        while (true) {
            System.out.println("\nAvailable brands:");
            validBrands.forEach(b -> System.out.println("- " + b));
            System.out.println("Enter brand (required):");
            brand = scanner.nextLine().trim().toLowerCase();

            if (!brand.isEmpty() && validBrands.contains(brand)) {
                break;
            }
            System.out.println("Invalid or empty brand. Please enter a valid brand.");
        }

        // price input
        Double minPrice = InputUtil.parsePrice(scanner, "Enter minimum price (optional):");
        Double maxPrice = InputUtil.parsePrice(scanner, "Enter maximum price (optional):");

        if (!InputUtil.validatePriceRange(minPrice, maxPrice)) {
            System.out.println("Maximum price must be greater than or equal to minimum price. Price filters will be ignored.");
            minPrice = null;
            maxPrice = null;
        }

        // date input
        Date fromDate = InputUtil.parseDate(scanner, "Enter start release date (MM/dd/yyyy, optional):");
        Date toDate = InputUtil.parseDate(scanner, "Enter end release date (MM/dd/yyyy, optional):");

        if (!InputUtil.validateDateRange(fromDate, toDate)) {
            System.out.println("End date must not be earlier than start date. Date filters will be ignored.");
            fromDate = null;
            toDate = null;
        }

        // sort options
        System.out.println("Sort by release date? (yes/no):");
        boolean sortByDate = scanner.nextLine().trim().equalsIgnoreCase("yes");

        System.out.println("Sort by price? (yes/no):");
        boolean sortByPrice = scanner.nextLine().trim().equalsIgnoreCase("yes");

        System.out.println("Sort by type and currency? (SUV=EUR, Sedan=JPY, Truck=USD) (yes/no):");
        boolean sortByTypeCurrency = scanner.nextLine().trim().equalsIgnoreCase("yes");

        // output format
        System.out.println("Choose output format (table/json/xml):");
        String format = scanner.nextLine().trim();

        // filtering logic
        List<Car> filtered = cars;

        boolean hasPriceFilter = (minPrice != null || maxPrice != null);
        boolean hasDateFilter = (fromDate != null || toDate != null);

        if (hasDateFilter) {
            filtered = CarFilter.filterByBrandAndReleaseDate(filtered, brand, fromDate, toDate);
        } else if (hasPriceFilter) {
            filtered = CarFilter.filterByBrandAndPrice(filtered, brand, minPrice, maxPrice, CurrencyType.USD.name());
        }

        // sort logic
        if (sortByTypeCurrency) {
            CarSorter.sortByTypeAndCurrencyPriceDesc(filtered);
        } else if (sortByDate) {
            CarSorter.sortByReleaseDateDesc(filtered);
        } else if (sortByPrice) {
            CarSorter.sortByPriceDesc(filtered, CurrencyType.USD.name());
        }

        // output result
        OutputFormatter formatter = OutputFactory.getFormatter(format);
        formatter.print(filtered);
    }
}
