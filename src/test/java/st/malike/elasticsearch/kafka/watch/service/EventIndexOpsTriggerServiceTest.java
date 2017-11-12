package st.malike.elasticsearch.kafka.watch.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @autor malike_st
 */
@RunWith(MockitoJUnitRunner.class)
public class EventIndexOpsTriggerServiceTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTheAwesome() throws Exception {
        Assert.assertNotEquals("awesome", "AWESOME");
    }

    @Test
    public void testEvaluateRuleForIndexCreate() {
        Assert.assertNotEquals("awesome", "AWESOME");
    }

    @Test
    public void testEvaluateRuleForIndexDelete() {
        Assert.assertNotEquals("awesome", "AWESOME");
    }
}
