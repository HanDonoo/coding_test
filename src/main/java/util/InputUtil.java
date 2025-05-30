package util;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class InputUtil {

    // parse price value
    public static Double parsePrice(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()){
                return null;
            }

            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Price cannot be negative. Please enter again.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter again.");
            }
        }
    }

    // parse date value
    public static Date parseDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }

            // validate date use regex：MM/dd/yyyy
            if (!input.matches("^(0[1-9]|1[0-2])/([0][1-9]|[12][0-9]|3[01])/((19[7-9][1-9])|(19[8-9][0-9])|(20\\d{2}))$")) {
                System.out.println("Invalid format. Please enter as MM/dd/yyyy, year > 1970.");
                continue;
            }

            try {
                return DateUtil.parseDate(input); // parseDate里必须禁用宽容模式
            } catch (ParseException e) {
                System.out.println("Invalid date. Please enter a valid calendar date.");
            }
        }
    }

    // validate price range (max >= min)
    public static boolean validatePriceRange(Double min, Double max) {
        return min == null || max == null || max >= min;
    }

    // validate date range (to >= from)
    public static boolean validateDateRange(Date from, Date to) {
        return from == null || to == null || !from.after(to);
    }
}

