package app.logfile.util;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PatternMatcher {

    public Matcher getMatcher(String record) {
        String regex = "^(\\S+) (\\S+) (\\S+) " +
                "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
                " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+)";

        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        return pattern.matcher(record);
    }

}
