package st.malike.elasticsearch.kafka.watch.model;

import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import st.malike.elasticsearch.kafka.watch.util.Enums;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author malike_st
 */
public class KafkaWatch implements ToXContent {

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
    private String indexName;
    private boolean generateReport;
    private String reportFormat;
    private String reportTemplatePath;
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

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public String getReportTemplatePath() {
        return reportTemplatePath;
    }

    public void setReportTemplatePath(String reportTemplatePath) {
        this.reportTemplatePath = reportTemplatePath;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }



    public XContentBuilder toXContent(XContentBuilder xContentBuilder, ToXContent.Params params)
            throws IOException {
        xContentBuilder.field("id", id);
        xContentBuilder.field("dateCreated", dateCreated);
        xContentBuilder.field("generateReport", generateReport);
        if (this.cron != null || !this.cron.isEmpty()) {
            xContentBuilder.field("cron", cron);
        }
        if (this.eventType != null || !this.eventType.isEmpty()) {
            xContentBuilder.field("eventType", eventType);
        }
        if (this.subject != null || !this.subject.isEmpty()) {
            xContentBuilder.field("subject", subject);
        }
        if (this.description != null || !this.description.isEmpty()) {
            xContentBuilder.field("description", description);
        }
        if (this.recipient != null || !this.recipient.isEmpty()) {
            xContentBuilder.field("recipient", recipient);
        }
        if (this.channel != null || !this.channel.isEmpty()) {
            xContentBuilder.field("channel", channel);
        }
        if (this.triggerType != null) {
            xContentBuilder.field("triggerType", triggerType);
        }
        if (this.indexOpsQuery != null || !this.indexOpsQuery.isEmpty()) {
            xContentBuilder.field("indexOpsQuery", indexOpsQuery);
        }

        if (this.querySymbol != null) {
            xContentBuilder.field("querySymbol", querySymbol);
        }
        if (this.indexName != null || !this.indexName.isEmpty()) {
            xContentBuilder.field("indexName", indexName);
        }
        if (this.miscData != null || !this.miscData.isEmpty()) {
            xContentBuilder.field("miscData", miscData);
        }
        if (this.reportTemplatePath != null || !this.reportTemplatePath.isEmpty()) {
            xContentBuilder.field("reportTemplatePath", reportTemplatePath);
        }
        return xContentBuilder;
    }
}
