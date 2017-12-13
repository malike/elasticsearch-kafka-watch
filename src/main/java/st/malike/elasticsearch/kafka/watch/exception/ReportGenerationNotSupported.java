package st.malike.elasticsearch.kafka.watch.exception;

/**
 * @autor malike_st
 */
public class ReportGenerationNotSupported extends Exception {

    public ReportGenerationNotSupported() {
        super();
    }

    public ReportGenerationNotSupported(String message) {
        super(message);
    }

    public ReportGenerationNotSupported(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportGenerationNotSupported(Throwable cause) {
        super(cause);
    }

    protected ReportGenerationNotSupported(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
