package app.logfile.service;

import app.logfile.resource.FileUtil;
import app.logfile.resource.ParseLogFile;
import app.logfile.util.PatternMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static app.logfile.testdata.TestDataFactory.TEST_FILE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggerContentServiceTest {

    @Mock
    private PatternMatcher patternMatcher;
    @Mock
    private FileUtil fileUtil;
    @Mock
    private ParseLogFile parseLogFile;

    @Mock
    private LogFileContentService logFileContentService;

    @InjectMocks
    private LoggerContentService loggerContentService;

    @Before
    public void setup() throws IOException {


    }

    @Test
    public void shouldReturntoUniqueIPAddresses() throws IOException {
        mockUniqueIPAddressMap();
        String uniqueAddresses = "50.112.00.11\n" +"168.41.191.34\n" + "168.41.191.41\n";
        Assert.assertThat(loggerContentService.uniqueIPAddresses(),is(uniqueAddresses)) ;
    }

    @Test
    public void shouldReturntoTop3ActiveIps() throws IOException {
        mockUniqueIPAddressMap();
        String top3ActiveIps = "168.41.191.41\n" +"168.41.191.34\n" + "50.112.00.11\n";
        Assert.assertThat(loggerContentService.top3MostActiveIpAddresses(),is(top3ActiveIps)) ;
    }

    @Test
    public void shouldReturntoTop3ActiveUrls() throws IOException {
        mockTopMostActiveUrls();
        String top3ActiveUrls = "/docs/manage-websites/\n" +"/blog/2018/08/survey-your-opinion-matters/\n" + "/newsletter/\n";
        Assert.assertThat(loggerContentService.top3MostActiveIpAddresses(),is(top3ActiveUrls)) ;
    }

    private void mockTopMostActiveUrls() throws IOException {
        HashMap<String, Integer> mostActiveUrls = new HashMap<>();
        mostActiveUrls.put("/docs/manage-websites/", 2);
        mostActiveUrls.put("/blog/2018/08/survey-your-opinion-matters/", 1);
        mostActiveUrls.put("/newsletter/", 1);
        mocks(mostActiveUrls);
    }

    private void mocks(HashMap<String, Integer> mostActiveUrls) throws IOException {
        when(parseLogFile.parseFile()).thenReturn(TEST_FILE);
        when(patternMatcher.getMatcher(TEST_FILE)).thenReturn(getMatcher());

        when(logFileContentService.getLoggerContentCountMap(any(), anyBoolean(), anyInt())).thenReturn(mostActiveUrls);
    }


    private void mockUniqueIPAddressMap() throws IOException {
        HashMap<String, Integer> uniqueIPAddressMap = new HashMap<>();
        uniqueIPAddressMap.put("50.112.00.11", 2);
        uniqueIPAddressMap.put("168.41.191.34", 3);
        uniqueIPAddressMap.put("168.41.191.41", 5);
        mocks(uniqueIPAddressMap);
    }

    private Matcher getMatcher () {
       String regex = "^(\\S+) (\\S+) (\\S+) " +
                "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
                " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+)";
        return Pattern.compile(regex, Pattern.MULTILINE).matcher(TEST_FILE);
    }

}
