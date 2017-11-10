package st.malike.elasticsearch.kafka.watch.exception;

/**
 * @author malike_st
 */
public class InvalidCronExpression extends Exception {
    public InvalidCronExpression() {
    }

    public InvalidCronExpression(String message) {
        super(message);
    }

    public InvalidCronExpression(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCronExpression(Throwable cause) {
        super(cause);
    }

    public InvalidCronExpression(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

