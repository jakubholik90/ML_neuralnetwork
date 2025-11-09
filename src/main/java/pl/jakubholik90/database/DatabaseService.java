package pl.jakubholik90.database;

import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.UI;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseService {

    private final UI actualUI;
    private final App app;
    private final String createTableSQL = """
            CREATE TABLE IF NOT EXISTS training_data (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            set_name TEXT NOT NULL,
            input_array TEXT NOT NULL,
            output_array TEXT NOT NULL
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

    private void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


    private void createTable() throws SQLException {
        Connection connection = setConnection();
        connection.createStatement().executeUpdate(createTableSQL);
        closeConnection(connection);
    }

    public void setData(String query) throws SQLException {
        Connection connection = setConnection();
        connection.createStatement().executeUpdate(query);
        closeConnection(connection);
    }

    public ArrayList<DatabaseRecord> getData(String query) throws SQLException {
        ArrayList<DatabaseRecord> dbRecordList = new ArrayList<>();

        try (Connection connection = setConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(query);

        ) {
            while (resultSet.next()) {
                String setName = resultSet.getString("set_name");
                String inputArray = resultSet.getString("input_array");
                String outputArray = resultSet.getString("output_array");
                DatabaseRecord dbRecord = new DatabaseRecord(setName,inputArray, outputArray);
                dbRecordList.add(dbRecord);
            }
        }
        return dbRecordList;
    }

    public void previewData(ArrayList<DatabaseRecord> dbRecordList) {
        for (DatabaseRecord dbRecord : dbRecordList) {
            actualUI.displayMessage("set: " + dbRecord.setName() + ", input:" + dbRecord.inputData() + ", output: " + dbRecord.outputData());
        }
    }






}
