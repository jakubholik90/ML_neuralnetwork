package pl.jakubholik90.controllers;

import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pl.jakubholik90.dto.NeuralNetworkSnapshotRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVController {

    private static final String filePath = "src/main/resources/nn_csv_imports/";

    public static List<String> listAllFiles() {
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }

    public static List<Double[]> importCsvData(String fileName) {
        try (FileReader reader = new FileReader(filePath + fileName)) {
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.parse(reader);
            List<Double[]> csvRecordsList = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                Double[] currentCsv = new Double[csvRecord.size()];
                for (int i = 0; i < csvRecord.size(); i++) {
                    currentCsv[i] = Double.valueOf(csvRecord.get(i));
                }
                csvRecordsList.add(currentCsv);
            }
            return csvRecordsList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
