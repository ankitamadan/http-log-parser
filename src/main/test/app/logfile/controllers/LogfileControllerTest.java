package app.logfile.controllers;

import app.logfile.service.LoggerContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LogfileController.class)
public class LogfileControllerTest {

    private final String MOST_ACTIVE_ADDRESSES = "168.41.191.40\n" +"50.112.00.11\n" +"177.71.128.21";
    private final String TOP_THREE_MOST_VISITED_URLS = "/docs/manage-websites/\n" + "/blog/2018/08/survey-your-opinion-matters/\n" +  "/newsletter/";
    private final String UNIQUE_IP_ADDRESSES = "50.112.00.11\n" + "168.41.191.34\n" + "168.41.191.41\n" + "168.41.191.43\n" +  "79.125.00.21\n" + "72.44.32.11\n" +  "177.71.128.21\n" + "168.41.191.40\n" + "50.112.00.28\n" + "168.41.191.9\n" + "72.44.32.10";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoggerContentService loggerContentService;

    @Test
    public void shouldReturnTopThreeMostActiveIPAddresses() throws Exception {
        when(loggerContentService.top3MostActiveIpAddresses()).thenReturn(MOST_ACTIVE_ADDRESSES);
        mvc.perform(get("/topThreeMostActiveIPAddresses"))
                .andExpect(status().isOk())
                .andExpect(content().string(MOST_ACTIVE_ADDRESSES));
    }

    @Test
    public void shouldReturnTop3VisitedUrlsTest() throws Exception {
        when(loggerContentService.top3VisitedUrls()).thenReturn(TOP_THREE_MOST_VISITED_URLS);
        mvc.perform(get("/topThreeVisitedUrls"))
                .andExpect(status().isOk())
                .andExpect(content().string(TOP_THREE_MOST_VISITED_URLS));
    }

    @Test
    public void shouldReturnUniqueIPAddressesTest() throws Exception {
        when(loggerContentService.uniqueIPAddresses()).thenReturn(UNIQUE_IP_ADDRESSES);
        mvc.perform(get("/uniqueIPAddresses"))
                .andExpect(status().isOk())
                .andExpect(content().string(UNIQUE_IP_ADDRESSES));
    }
}
