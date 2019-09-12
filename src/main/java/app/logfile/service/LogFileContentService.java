package app.logfile.service;

import app.logfile.resource.ParseLogFile;
import app.logfile.util.PatternMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;

@Service
public class LogFileContentService {


    public HashMap<String, Integer> getLoggerContentCountMap(Matcher matcher, Boolean isActiveIp, int logger) throws IOException {
        HashMap<String, Integer> loggerContentCountMap = new HashMap<String, Integer>();

        while (matcher.find()) {

            String IP = matcher.group(logger);
            String httpStatusCode = matcher.group(8);
            int response = Integer.parseInt(httpStatusCode);

            if (response == 200 && isActiveIp) {
                loggerContentCount(loggerContentCountMap, IP);
            } else if(!isActiveIp){
                loggerContentCount(loggerContentCountMap, IP);
            }
        }

        return loggerContentCountMap;
    }

    private void loggerContentCount(HashMap<String, Integer> loggerContentCountMao, String loggerContent) {
        if (loggerContentCountMao.containsKey(loggerContent)) {
            loggerContentCountMao.put(loggerContent, loggerContentCountMao.get(loggerContent) + 1);
        } else {
            loggerContentCountMao.put(loggerContent, 1);
        }
    }
}
