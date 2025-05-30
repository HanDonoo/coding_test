package parser;

import model.Car;
import util.DateUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;

public class CsvParser {

    /**
     * parse the csv data
     * @param csvFile
     * @param cars
     * @throws Exception
     */
    public void parseBrandAndReleaseDateFromCsv(File csvFile, List<Car> cars) throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean firstLine = true;
            int index = 0;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String brand = parts[0].replaceAll("^\"|\"$", "").trim();
                String releaseDateStr = parts[1].trim();
                Date releaseDate = DateUtil.parseDate(releaseDateStr);

                if (index < cars.size()) {
                    Car car = cars.get(index);
                    car.setBrand(brand);
                    car.setReleaseDate(releaseDate);
                    index++;
                } else {
                    break;
                }
            }
        }
    }

}
