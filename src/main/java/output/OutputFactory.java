package output;

import exception.UnsupportedOutputFormatException;

public class OutputFactory {

    public static OutputFormatter getFormatter(String type) {
        switch (type.toLowerCase()) {
            case "json":
                return new JsonFormatter();
            case "xml":
                return new XmlFormatter();
            case "table":
                return new TableFormatter();
            default:
                throw new UnsupportedOutputFormatException(type);
        }
    }
}
