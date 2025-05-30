package exception;

public class UnsupportedOutputFormatException extends RuntimeException {

    public UnsupportedOutputFormatException(String format) {
        super("unsupported output format: " + format);
    }
}
