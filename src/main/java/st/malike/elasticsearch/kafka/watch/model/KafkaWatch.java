package st.malike.elasticsearch.kafka.watch.model;

import st.malike.elasticsearch.kafka.watch.util.Enums;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author malike_st
 */
public class KafkaWatch {

    private String id;
    private String cron;
    private String eventType;
    private String subject;
    private String description;
    private List<String> recipient;
    private List<String> channel;
    private Enums.TriggerType triggerType;
    private String indexOpsQuery;
    private Enums.QuerySymbol querySymbol;
    private int expectedHit;
    private int interval;
    private String indexName;
    private boolean generateReport;
    private Map<String, String> miscData;
    private Date dateCreated;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRecipient() {
        return recipient;
    }

    public void setRecipient(List<String> recipient) {
        this.recipient = recipient;
    }

    public List<String> getChannel() {
        return channel;
    }

    public void setChannel(List<String> channel) {
        this.channel = channel;
    }

    public Enums.TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Enums.TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public String getIndexOpsQuery() {
        return indexOpsQuery;
    }

    public void setIndexOpsQuery(String indexOpsQuery) {
        this.indexOpsQuery = indexOpsQuery;
    }

    public Enums.QuerySymbol getQuerySymbol() {
        return querySymbol;
    }

    public void setQuerySymbol(Enums.QuerySymbol querySymbol) {
        this.querySymbol = querySymbol;
    }

    public int getExpectedHit() {
        return expectedHit;
    }

    public void setExpectedHit(int expectedHit) {
        this.expectedHit = expectedHit;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isGenerateReport() {
        return generateReport;
    }

    public void setGenerateReport(boolean generateReport) {
        this.generateReport = generateReport;
    }

    public Map<String, String> getMiscData() {
        return miscData;
    }

    public void setMiscData(Map<String, String> miscData) {
        this.miscData = miscData;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
