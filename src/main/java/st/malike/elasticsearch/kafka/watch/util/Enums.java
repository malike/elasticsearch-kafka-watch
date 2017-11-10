package st.malike.elasticsearch.kafka.watch.util;

/**
 * @author malike_st
 */
public class Enums {

    public enum JSONResponseMessage {
        SUCCESS,
        ERROR,
        MISSING_PARAM,
        INVALID_DATA,
    }

    public enum TriggerType {
        TIME,
        INDEX_OPS
    }

    public enum QuerySymbol {
        EQUAL_TO,
        GREATER_THAN_OR_EQUAL_TO,
        LESS_THAN_OR_EQUAL_TO,
    }


}
