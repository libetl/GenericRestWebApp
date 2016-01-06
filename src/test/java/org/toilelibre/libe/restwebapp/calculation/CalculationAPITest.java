package org.toilelibre.libe.restwebapp.calculation;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import org.toilelibre.libe.restwebapp.ioc.LuceneSpellCheckerConfig;
import org.toilelibre.libe.restwebapp.ioc.webapp.WebAppConfig;
import org.toilelibre.libe.restwebapp.testutils.LogbackConfigRule;
import org.toilelibre.libe.restwebapp.testutils.SmartLogRule;
import org.toilelibre.libe.restwebapp.testutils.TestConfig;

@ContextConfiguration (classes = { WebAppConfig.class, LuceneSpellCheckerConfig.class, TestConfig.class })
@WebAppConfiguration
public class CalculationAPITest {

    // The test does not need to know what impl of the account value object it
    // uses
    @ClassRule
    public static final LogbackConfigRule LOGBACK_CONFIG_RULE = new LogbackConfigRule ();

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule ();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule ();

    @Rule
    public SmartLogRule smartLogRule = new SmartLogRule ();

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    public void calculateShouldAnswerSuccessfully () throws Exception {
        this.mockMvc
                .perform (MockMvcRequestBuilders.post ("/calculation/").content ("{\"int1\":1,\"int2\":2}").contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                .andExpect (MockMvcResultMatchers.status ().is2xxSuccessful ())
                .andExpect (MockMvcResultMatchers.content ().contentType (MediaType.parseMediaType ("application/json;charset=UTF-8")))
                .andExpect (MockMvcResultMatchers.content ().json ("{\"self\":{\"rel\":\"CalculationCalculate\"," + "         \"href\":\"http://localhost/calculation/\","
                        + "         \"methods\":[\"POST\"],\"params\":" + "                [{\"binding\":\"int1\",\"type\":\"int\"},{\"binding\":\"int2\",\"type\":\"int\"}]},"
                        + "\"type\":\"ComplexObjectNode\"," + "\"ok\":1," + "\"content\":{\"result\":\"3\",\"links\":[{\"methods\":[\"POST\"],"
                        + "                                       \"params\":[{\"binding\":\"int1\",\"type\":\"int\"},"
                        + "                                       {\"binding\":\"int2\",\"type\":\"int\"},{\"binding\":\"result\",\"type\":\"int\"}],"
                        + "                                       \"rel\":\"CalculationGuessSum\",\"href\":\"http://localhost/calculation/guess\"}],"
                        + "                                       \"int1\":\"1\",\"int2\":\"2\"}}"));
    }

    @Test
    public void guessCorrectlyShouldAnswerSuccessfully () throws Exception {
        this.mockMvc
                .perform (MockMvcRequestBuilders.post ("/calculation/guess").content ("{\"int1\":1,\"int2\":2,\"result\":3}").contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                .andExpect (MockMvcResultMatchers.status ().is2xxSuccessful ())
                .andExpect (MockMvcResultMatchers.content ().contentType (MediaType.parseMediaType ("application/json;charset=UTF-8")))
                .andExpect (MockMvcResultMatchers.content ()
                        .json ("{\"self\":{\"rel\":\"CalculationGuessSum\"," + "\"href\":\"http://localhost/calculation/guess\"," + "\"methods\":[\"POST\"],\"params\":"
                                + "[{\"binding\":\"int1\",\"type\":\"int\"},{\"binding\":\"int2\",\"type\":\"int\"}," + "{\"binding\":\"result\",\"type\":\"int\"}]},"
                                + "\"type\":\"Integer\"," + "\"ok\":1," + "\"content\":3}"));
    }

    @Test
    public void guessNotCorrectlyShouldAnswerError () throws Exception {
        try {
            this.mockMvc.perform (MockMvcRequestBuilders.post ("/calculation/guess").content ("{\"int1\":1,\"int2\":2,\"result\":4}").contentType (MediaType.APPLICATION_JSON)
                    .accept (MediaType.APPLICATION_JSON)).andExpect (MockMvcResultMatchers.status ().is4xxClientError ());
        } catch (final NestedServletException nse) {
        }
    }

    @Before
    public void startMockMvc () {
        this.mockMvc = MockMvcBuilders.webAppContextSetup (this.webApplicationContext).build ();
    }

}
