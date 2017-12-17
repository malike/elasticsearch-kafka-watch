package st.malike.elasticsearch.kafka.watch.service;

import org.apache.kafka.clients.producer.Producer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import st.malike.elasticsearch.kafka.watch.model.KafkaEvent;

/**
 * @autor malike_st
 */
@RunWith(MockitoJUnitRunner.class)
public class KafkaProducerServiceTest {

    @InjectMocks
    @Spy
    private KafkaProducerService kafkaProducerService;
    @Mock
    private Producer<String, String> producer;
    @Mock
    private KafkaEvent kafkaEvent;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSend() {
        kafkaProducerService.send(kafkaEvent);
        Mockito.verify(kafkaProducerService, Mockito.times(1)).send(Mockito.any());

    }
}
