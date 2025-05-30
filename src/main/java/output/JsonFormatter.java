package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Car;

import java.text.SimpleDateFormat;
import java.util.List;

public class JsonFormatter implements OutputFormatter {

    private static final String DATE_FORMAT_STR = "MM/dd/yyyy";

    private final ObjectMapper mapper;

    public JsonFormatter() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_STR);
        mapper.setDateFormat(df);
    }

    @Override
    public void print(List<Car> cars) {
        try {
            String json = mapper.writeValueAsString(cars);
            System.out.println(json);
        } catch (Exception e) {
            System.err.println("output json error: " + e.getMessage());
        }
    }
}
