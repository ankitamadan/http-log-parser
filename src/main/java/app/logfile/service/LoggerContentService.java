package app.logfile.service;

import app.logfile.resource.ParseLogFile;
import app.logfile.util.PatternMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
public class LoggerContentService {

    private final PatternMatcher patternMatcher;
    private final ParseLogFile parseLogFile;

    private final LogFileContentService logFileContentService;

    public LoggerContentService(PatternMatcher patternMatcher,
                                ParseLogFile parseLogFile,
                                LogFileContentService logFileContentService){
        this.patternMatcher = patternMatcher;
        this.parseLogFile = parseLogFile;
        this.logFileContentService = logFileContentService;
    }

    public String top3VisitedUrls() throws IOException {
        StringBuffer uniqueUrls = new StringBuffer("");
        Matcher matcher = getMatcher();

        HashMap<String, Integer> countUniqueUrl = logFileContentService.getLoggerContentCountMap(matcher, true, 6);

        Map<String, Integer> top3Urls = topThreeloggerContent(countUniqueUrl);

        for (Map.Entry entry : top3Urls.entrySet()) {
            uniqueUrls.append(entry.getKey()).append("\n");
        }
        return uniqueUrls.toString();
    }

    public String top3MostActiveIpAddresses() throws IOException {
        StringBuffer uniqueIPAddresses = new StringBuffer("");
        Matcher matcher = getMatcher();

        HashMap<String, Integer> countUniqueIPAddresses = logFileContentService.getLoggerContentCountMap(matcher, true, 1);

        Map<String, Integer> top3IPAddresses = topThreeloggerContent(countUniqueIPAddresses);

        for (Map.Entry entry : top3IPAddresses.entrySet()) {
            uniqueIPAddresses.append(entry.getKey()).append("\n");
        }
        return uniqueIPAddresses.toString();
    }

    private Map<String, Integer> topThreeloggerContent(HashMap<String, Integer> countUniqueIP) {
        Map<String, Integer> result = countUniqueIP.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result.entrySet().stream().limit(3).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public String uniqueIPAddresses() throws IOException {
        StringBuffer uniqueIpAddresses = new StringBuffer("");
        Matcher matcher = getMatcher();

        HashMap<String, Integer> countUniqueIP = logFileContentService.getLoggerContentCountMap(matcher, false, 1);
        for (Map.Entry entry : countUniqueIP.entrySet()) {
            uniqueIpAddresses.append(entry.getKey()).append("\n");
        }
        return  uniqueIpAddresses.toString();
    }

    private Matcher getMatcher() throws IOException {
        String record = parseLogFile.parseFile();
        return patternMatcher.getMatcher(record);
    }

}
