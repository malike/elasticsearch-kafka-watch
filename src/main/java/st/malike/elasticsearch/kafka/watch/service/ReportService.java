package st.malike.elasticsearch.kafka.watch.service;

import com.google.gson.Gson;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import st.malike.elasticsearch.kafka.watch.ElasticKafkaWatchPlugin;
import st.malike.elasticsearch.kafka.watch.exception.ReportGenerationNotSupported;
import st.malike.elasticsearch.kafka.watch.exception.TemplateFileNotFoundException;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor malike_st
 */
public class ReportService {

    private static Logger log = Logger.getLogger(ReportService.class);
    HttpClient client = HttpClientBuilder.create().build();
    Gson gson = new Gson();


    public String getReport(KafkaWatch kafkaWatch) throws TemplateFileNotFoundException, ReportGenerationNotSupported {
        if(ElasticKafkaWatchPlugin.getReportEngineDisable()){
            throw new ReportGenerationNotSupported("Report generation not supported");
        }
        if (kafkaWatch == null) {
            return null;
        }
        if (validateReportFile(kafkaWatch)) {
            return executeService(kafkaWatch.getIndexName(),
                    kafkaWatch.getIndexOpsQuery(), kafkaWatch.getReportFormat(),
                    kafkaWatch.getReportTemplatePath());
        } else {
            throw new TemplateFileNotFoundException("Report template not found");
        }
    }

    public String executeService(String index, String query,
                                 String format, String templateFile) {

        try {
            HttpPost post = new HttpPost(ElasticKafkaWatchPlugin.getReportEngineEndpoint());

            post.setHeader("Content-Type", "application/json");

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("format", (format == null || format.isEmpty()) ? "PDF" : format));
            urlParameters.add(new BasicNameValuePair("index", index));
            urlParameters.add(new BasicNameValuePair("returnAs", "PLAIN"));
            urlParameters.add(new BasicNameValuePair("template", templateFile));
            urlParameters.add(new BasicNameValuePair("query", query));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                String responseString = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8.toString());
                JSONResponse jsonResponse = gson.fromJson(responseString, JSONResponse.class);
                if (jsonResponse.getStatus()) {
                    return (String) jsonResponse.getData();
                } else {
                    log.error("Error generating report. Response is " + gson.toJson(response.getEntity().getContent()));
                }
            } else {
                log.error("Error generating report. Status Code is " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
        }
        return null;
    }

    public boolean validateReportFile(KafkaWatch kafkaWatch) throws TemplateFileNotFoundException {
        File file = new File(kafkaWatch.getReportTemplatePath());
        if (!(file.exists() && !file.isDirectory())) {
            throw new TemplateFileNotFoundException("Report template not found");
        }
        return true;
    }

}
