package output;

import model.Car;
import java.util.List;
import util.DateUtil;

public class TableFormatter implements OutputFormatter{

    @Override
    public void print(List<Car> cars) {
        System.out.printf("%-10s %-15s %-15s %-12s %-15s\n", "Type", "Model", "Brand", "Release", "Price(USD)");
        System.out.println("-------------------------------------------------------------------------------");

        for (Car car : cars) {
            String releaseDateStr = DateUtil.formatDate(car.getReleaseDate());
            System.out.printf("%-10s %-15s %-15s %-12s %-15.2f\n",
                    car.getType(),
                    car.getModel(),
                    car.getBrand(),
                    releaseDateStr,
                    car.getPriceByCurrency("USD"));
        }
    }

}
