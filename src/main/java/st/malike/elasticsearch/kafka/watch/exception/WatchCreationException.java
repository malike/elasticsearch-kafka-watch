package st.malike.elasticsearch.kafka.watch.exception;

/**
 * @autor malike_st
 */
public class WatchCreationException extends Exception {

    public WatchCreationException() {
        super();
    }

    public WatchCreationException(String message) {
        super(message);
    }

    public WatchCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WatchCreationException(Throwable cause) {
        super(cause);
    }

    public WatchCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
