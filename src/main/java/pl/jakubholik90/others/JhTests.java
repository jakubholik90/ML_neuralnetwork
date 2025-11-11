package pl.jakubholik90.others;

import pl.jakubholik90.database.DataRecord;
import pl.jakubholik90.database.DataSet;
import pl.jakubholik90.database.DatabaseService;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.ConsoleUI;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class JhTests
{
        public static void main(String[] args) throws SQLException {

            ConsoleUI testUI = new ConsoleUI();
            Double[] doubles = {1.0, 2.0, 3.0};
            String string = Arrays.toString(doubles);

            DatabaseService databaseService = new DatabaseService(testUI, new App(testUI));

            Double[] doubles1 = databaseService.parseStringToDoubleArray(string);


            DataRecord testDbRecord = new DataRecord(
                    "newSet2",
                    new Double[] {1.0,2.0,3.0},
                    new Double[] {0.0,1.0});
            DataRecord testDbRecord2 = new DataRecord(
                    "newSet2",
                    new Double[] {1.0,2.5,3.0},
                    new Double[] {0.0,1.0});
            DataRecord testDbRecord3 = new DataRecord(
                    "newSet1",
                    new Double[] {1.5,2.0,3.0},
                    new Double[] {0.0,1.0});
            databaseService.insertDataRecord(testDbRecord);
            databaseService.insertDataRecord(testDbRecord2);
            databaseService.insertDataRecord(testDbRecord3);
            HashMap<Integer, DataRecord> data = databaseService.getAllDataRecords();
            System.out.println("Data from database:");
            databaseService.previewDataRecords(data);
            System.out.println("Data sets from database:");
            HashMap<Integer, DataSet> allDataSets = databaseService.getAllDataSets();
            databaseService.previewDataSets(allDataSets);

        }



}

