package com.information.center.quizservice.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "feign.hystrix.enabled=true"
})
public class CourseServiceClientFallBackTest {


    @Autowired
    CourseServiceClient courseServiceClient;

    @Rule
    public final OutputCapture outputCapture = new OutputCapture();
    private String topicId;

    @Before
    public void setUp() {
        outputCapture.reset();

        topicId = "topicId";
    }

    @Test
    public void getTopicName_expectFallback() {
        String response = courseServiceClient.getTopicNameByTopicId(topicId);

        assertThat(response, is(""));
        outputCapture.expect(
                containsString(String.format("Error getting topic name by topicId: %s", topicId)));
    }
}
