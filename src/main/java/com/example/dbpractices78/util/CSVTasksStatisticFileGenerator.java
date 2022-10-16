package com.example.dbpractices78.util;

import com.example.dbpractices78.dto.StatisticDTO;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Vlad Utts
 */
@Component
public class CSVTasksStatisticFileGenerator {
    public void writeTasksStatisticToCsv(StatisticDTO statistics, Writer writer) {
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"All tasks count", "Success completed tasks count", "Fail completed tasks count",
                    "Not completed tasks before deadline", "Not completed tasks after deadline"};
            String[] nameMapping = {"allTasksCount", "successCompletedTasksCount", "failCompletedTasksCount",
                    "notCompletedTasksBeforeDeadline", "notCompletedTasksAfterDeadline"};
            csvWriter.writeHeader(csvHeader);
            csvWriter.write(statistics, nameMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
