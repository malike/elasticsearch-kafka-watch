package st.malike.elasticsearch.kafka.watch.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author malike_st
 */
public class KafkaEvent {

    private String eventId;
    private String subject;
    private Map<String, Boolean> channel;
    private List<String> recipient;
    private Map<String, String> unmappedData;
    private String eventType;
    private String description;
    private Date dateCreated;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, Boolean> getChannel() {
        return channel;
    }

    public void setChannel(Map<String, Boolean> channel) {
        this.channel = channel;
    }

    public List<String> getRecipient() {
        return recipient;
    }

    public void setRecipient(List<String> recipient) {
        this.recipient = recipient;
    }

    public Map<String, String> getUnmappedData() {
        return unmappedData;
    }

    public void setUnmappedData(Map<String, String> unmappedData) {
        this.unmappedData = unmappedData;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
