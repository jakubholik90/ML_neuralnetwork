package pl.jakubholik90.database;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.sql.*;
import java.util.*;

public class DatabaseService {

    private final UI actualUI;
    private final App app;
    private final String createTableSQL = """
            CREATE TABLE IF NOT EXISTS training_data (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            set_name TEXT NOT NULL,
            input_array TEXT NOT NULL,
            output_array TEXT NOT NULL,
            UNIQUE(set_name, input_array, output_array)
            );
            """;

    public DatabaseService(UI actualUI, App app) throws SQLException {
        this.actualUI = actualUI;
        this.app = app;
        createTable();
    }

    private Connection setConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        return connection;
    }

    private void createTable() throws SQLException {
        try (Connection connection = setConnection()) {
            connection.createStatement().executeUpdate(createTableSQL);
        }
    }

    public void insertDataRecord(DataRecord dbRecord) throws SQLException {
        try (Connection connection = setConnection()) {
            String query ="INSERT OR IGNORE INTO training_data ('set_name','input_array','output_array') VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, dbRecord.setName());
            preparedStatement.setString(2, Arrays.toString(dbRecord.inputData()));
            preparedStatement.setString(3, Arrays.toString(dbRecord.outputData()));

            preparedStatement.executeUpdate();
        }
    }

    public HashMap<Integer, DataRecord> getAllDataRecords() throws SQLException {
        String query = "SELECT * FROM training_data";
        HashMap<Integer, DataRecord> dbRecordMap = new HashMap<>();
        try (Connection connection = setConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(query)){
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String setName = resultSet.getString("set_name");
                String inputArrayString = resultSet.getString("input_array");
                String outputArrayString = resultSet.getString("output_array");
                Double[] inputArray = parseStringToDoubleArray(inputArrayString);
                Double[] outputArray = parseStringToDoubleArray(outputArrayString);
                DataRecord dbRecord = new DataRecord(setName,inputArray, outputArray);
                dbRecordMap.put(id,dbRecord);
            }
        }
        return dbRecordMap;
    }

    public HashMap<Integer, DataSet> getAllDataSets() throws SQLException {
        String query = "SELECT * FROM training_data";
        Set<String> dbSetNames = new HashSet<>();
        // method to be finished
        HashMap<Integer, DataSet> dbRecordMap = new HashMap<>();
        try (Connection connection = setConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(query)) {
            while (resultSet.next()) {
                String setName = resultSet.getString("set_name");
                boolean setAlreadyInMap = dbRecordMap.values()
                        .stream()
                        .map(x -> x.getName())
                        .toList()
                        .contains(setName);
                if (setAlreadyInMap) {
                    DataSet setWithName = dbRecordMap.values()
                            .stream()
                            .filter(x -> x.getName().equals(setName))
                            .findFirst()
                            .get();

                    Integer actualSize = setWithName.getSize();
                    setWithName.setSize(actualSize + 1);
                } else {
                    DataSet newSet = new DataSet(setName, 1);
                    int mapSize = dbRecordMap.size();
                    dbRecordMap.put(mapSize + 1, newSet);
                }
            }
        }
        return dbRecordMap;
    }

    public void previewDataRecords(HashMap<Integer, DataRecord> dbRecordMap) {
        Set<Integer> dbRecordIds = dbRecordMap.keySet();
        for (int dbRecordId : dbRecordIds) {
            DataRecord dbRecord = dbRecordMap.get(dbRecordId);
            actualUI.displayMessage("id: " + dbRecordId + ", set: " + dbRecord.setName() + ", input: " + Arrays.toString(dbRecord.inputData()) + ", output: " + Arrays.toString(dbRecord.outputData()));
        }
    }

    public void previewDataSets(HashMap<Integer, DataSet> dbRecordMap) {
        Set<Integer> dbSetIds = dbRecordMap.keySet();
        for (int dbSetId : dbSetIds) {
            DataSet dbSet = dbRecordMap.get(dbSetId);
            actualUI.displayMessage("id: " + dbSetId + ", set: " + dbSet.getName() + ", size: " + dbSet.getSize());
        }
    }

    public Double[] parseStringToDoubleArray(String arrayString) {
        String trimmed = arrayString.replaceAll("[\\[\\]\\s]", ""); // Remove brackets and whitespace
        String[] stringValues = trimmed.split(","); // Split by comma
        Double[] doubleValues = new Double[stringValues.length];
        for (int i = 0; i < stringValues.length; i++) {
            doubleValues[i] = Double.parseDouble(stringValues[i]);
        }
        return doubleValues;
    }








}
