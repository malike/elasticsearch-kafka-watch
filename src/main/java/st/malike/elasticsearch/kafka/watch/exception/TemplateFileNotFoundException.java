package st.malike.elasticsearch.kafka.watch.exception;

/**
 * @autor malike_st
 */
public class TemplateFileNotFoundException extends Exception {
    public TemplateFileNotFoundException() {super();
    }

    public TemplateFileNotFoundException(String message) {
        super(message);
    }

    public TemplateFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public TemplateFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
