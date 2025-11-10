package pl.jakubholik90.others;

import pl.jakubholik90.database.DatabaseRecord;
import pl.jakubholik90.exceptions.WrongInputSizeException;
import pl.jakubholik90.database.DatabaseService;
import pl.jakubholik90.ui.App;
import pl.jakubholik90.ui.ConsoleUI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JhTests
{
        public static void main(String[] args) throws SQLException {
//            Double[][] aaa = new Double[3][4];
//            aaa[0][0] = 1.0;
//            aaa[0][1] = 2.0;
//            aaa[0][2] = 3.0;
//            aaa[0][3] = 4.0;
//
//            aaa[1][0] = 5.0;
//            aaa[1][1] = 6.0;
//            aaa[1][2] = 7.0;
//            aaa[1][3] = 8.0;
//
//            aaa[2][0] = 9.0;
//            aaa[2][1] = 10.0;
//            aaa[2][2] = 11.0;
//            aaa[2][3] = 12.0;
//
//            List<Double [][]> testList = new ArrayList<>();
//            testList.add(aaa);
//            System.out.println("aaa:");
//            Utils.displayArrayMatrix2(testList);
//
//            Double[][] bbb = JhTests.slice2DArray(aaa,0,2,0,3);
//            List<Double [][]> testList2 = new ArrayList<>();
//            testList2.add(bbb);
//            System.out.println("bbb:");
//            Utils.displayArrayMatrix2(testList2);
//
//            Double[][] ccc = NumPyLike.add2Arrays(aaa, bbb);
//            List<Double [][]> testList3 = new ArrayList<>();
//            testList3.add(ccc);
//            System.out.println("ccc:");
//            Utils.displayArrayMatrix2(testList3);
//
//            Double[] ddd = new Double[] {2.0,3.0,4.0};
//            List<Double []> testList6 = new ArrayList<>();
//            testList6.add(ddd);
//            System.out.println("ddd:");
//            System.out.println(ddd.length);
//            Utils.displayArrayMatrix(testList6);
//            Double[][] eee = NumPyLike.transposeVector(new Double[] {5.0,6.0});
//            List<Double [][]> testList5 = new ArrayList<>();
//            testList5.add(eee);
//            System.out.println("eee:");
//            Utils.displayArrayMatrix2(testList5);
            // Double [][] fff = NumPyLike.vectorTimesTransposedVector(ddd,eee);
            // List<Double [][]> testList4 = new ArrayList<>();
            // testList4.add(fff);
            // System.out.println("fff:");
            // Utils.displayArrayMatrix2(testList4);

            ConsoleUI testUI = new ConsoleUI();
            Double[] doubles = {1.0, 2.0, 3.0};
            String string = Arrays.toString(doubles);

            DatabaseService databaseService = new DatabaseService(testUI, new App(testUI));

            Double[] doubles1 = databaseService.parseStringToDoubleArray(string);


            DatabaseRecord testDbRecord = new DatabaseRecord(
                    "newSet",
                    new Double[] {1.0,2.0,3.0},
                    new Double[] {0.0,1.0});
            DatabaseRecord testDbRecord2 = new DatabaseRecord(
                    "newSet",
                    new Double[] {1.0,2.5,3.0},
                    new Double[] {0.0,1.0});
            DatabaseRecord testDbRecord3 = new DatabaseRecord(
                    "newSet2",
                    new Double[] {1.5,2.0,3.0},
                    new Double[] {0.0,1.0});
            databaseService.insertDataRecord(testDbRecord);
            databaseService.insertDataRecord(testDbRecord2);
            databaseService.insertDataRecord(testDbRecord3);
            HashMap<Integer, DatabaseRecord> data = databaseService.getData();
            databaseService.previewData(data);
        }

        public static Double[][] slice2DArray(Double[][] inputArray, int rowStart, int rowEnd, int colStart, int colEnd) {
            boolean rowStartPos = (rowStart>=0);
            boolean colStartPos = (colStart>=0);
            boolean rowEndOK = (rowEnd>=rowStart);
            boolean colEndOK = (colEnd>=colStart);

            if (!rowStartPos) {
                throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. rowStart is negative");
            }
            if (!colStartPos) {
                throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. colStart is negative");
            }
            if (!rowEndOK) {
                throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. rowEnd<rowStart");
            }
            if (!colEndOK) {
                throw new WrongInputSizeException("Incorrect input, slice2DArray cannot be performed. colEnd<colStart");
            }
            int rowSize = rowEnd-rowStart+1;
            int colSize = colEnd-colStart+1;
            Double[][] returnArray = new Double[rowSize][colSize];

            int currentOldRow = rowStart;
            for (int newRow = 0; newRow < rowSize; newRow++) {
                int currentOldCol = colStart;
                for (int newCol = 0; newCol < colSize; newCol++) {
                    returnArray[newRow][newCol] = inputArray[currentOldRow][currentOldCol];
                    currentOldCol++;
                }
                currentOldRow++;
            }
            return returnArray;
        }

}

