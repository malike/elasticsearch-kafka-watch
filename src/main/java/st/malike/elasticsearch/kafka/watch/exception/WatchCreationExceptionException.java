package st.malike.elasticsearch.kafka.watch.exception;

/**
 * @autor malike_st
 */
public class WatchCreationExceptionException extends Exception {

    public WatchCreationExceptionException() {
    }

    public WatchCreationExceptionException(String message) {
        super(message);
    }

    public WatchCreationExceptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WatchCreationExceptionException(Throwable cause) {
        super(cause);
    }

    public WatchCreationExceptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
