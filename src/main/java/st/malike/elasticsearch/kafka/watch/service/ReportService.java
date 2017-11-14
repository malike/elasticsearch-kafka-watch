package st.malike.elasticsearch.kafka.watch.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import st.malike.elasticsearch.kafka.watch.ElasticKafkaWatchPlugin;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor malike_st
 */
public class ReportService {

    private static Logger log = Logger.getLogger(ReportService.class);

    public String getReport(KafkaWatch kafkaWatch) {
        return "";
    }

    public String executeService(String index,String query,
               String format,String templateFile) {

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(ElasticKafkaWatchPlugin.getReportEngineEndpoint());

            post.setHeader("Content-Type", "application/json");

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("format", format));
            urlParameters.add(new BasicNameValuePair("index", index));
            urlParameters.add(new BasicNameValuePair("template", templateFile));
            urlParameters.add(new BasicNameValuePair("query", query));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == 200){

            }else{
                log.error("Error generating report. Status Code is "+response.getStatusLine().getStatusCode());
                response.getEntity().getContent()
            }


        }catch(Exception e){

        }
        return null;
    }
}
