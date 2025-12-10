package pl.jakubholik90.controllers;

import com.google.gson.Gson;
import pl.jakubholik90.neuralnetwork.NeuralNetworkConfig;
import pl.jakubholik90.dto.NeuralNetworkSnapshotRecord;
import pl.jakubholik90.neuralnetwork.NeuralNetwork;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkExportController {

    private static final String filePath = "src/main/resources/nn_snapshots/";

    public NeuralNetworkExportController() {
    }

    public static NeuralNetworkSnapshotRecord createSnapshot(NeuralNetworkConfig config, NeuralNetwork neuralNetwork, String nnName, String dataSetName, String userComment) {
        int[] structure = config.getStructure();
        int numberOfIterations = config.getNumberOfIterations();
        double eta = config.getEta();
        String outputLayerActivationFunction = config.getNameActivationFunction(config.getOutputLayerActivationFunction());
        String hiddenLayerActivationFunction = config.getNameActivationFunction(config.getHiddenLayerActivationFunction());


        NeuralNetworkSnapshotRecord snapshot = new NeuralNetworkSnapshotRecord(
                nnName,
                structure,
                numberOfIterations,
                eta,
                outputLayerActivationFunction,
                hiddenLayerActivationFunction,
                neuralNetwork.getActivationsMatrix(),
                neuralNetwork.getWeightedSumsMatrix(),
                neuralNetwork.getWeightsMatrix(),
                dataSetName,
                userComment,
                String.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Berlin")))
        );
        return snapshot;
    }

    public static String exportNeuralNetwork(NeuralNetworkSnapshotRecord snapshot) {
        Gson gson = new Gson();
        String jsonAsString = gson.toJson(snapshot);
        String fileName = snapshot.name() + "_" + snapshot.timeStamp() + ".json";

        try (FileWriter writer = new FileWriter(filePath + fileName)) {
            writer.write(jsonAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public static NeuralNetworkSnapshotRecord importNeuralNetwork(String fileName) {
        try (FileReader reader = new FileReader(filePath + fileName)) {
            // String jsonAsString = reader.toString();
            Gson gson = new Gson();
            NeuralNetworkSnapshotRecord snapshot = gson.fromJson(reader, NeuralNetworkSnapshotRecord.class);
            return snapshot;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
}
