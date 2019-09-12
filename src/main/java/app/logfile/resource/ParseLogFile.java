package app.logfile.resource;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ParseLogFile {

    private final FileUtil fileUtil;

    public ParseLogFile(FileUtil fielUtil) {
        this.fileUtil = fielUtil;
    }

    public String parseFile() throws IOException {

        File file = fileUtil.getFileFromResources("programming-task-example-data.log");

        if (file == null) return "";
        StringBuilder stringBuilder = new StringBuilder("");
        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");

            }
        }

        return stringBuilder.toString();

    }

}
