/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest_drone_distance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author argeu
 */
public class FXMLDocumentController implements Initializable {

    private City cityList[];
    private CityGraph graph;

    @FXML
    private Label readLabel, directLabel, pathDisLabel, numPathLabel;

    @FXML
    private Button button;

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    @FXML
    private ComboBox<City> startCombo = new ComboBox<>();

    @FXML
    private ComboBox<City> destCombo = new ComboBox<>();

    @FXML
    private void loadCity(ActionEvent event) {
        City adjList[] = new City[800];
        int index = 0;
        int zip;
        int timeZone;
        boolean daySav;
        double latitude;
        double longitude;
        String daylightSavings;
        String cityName;
        String stateName;
        City cTemp;

        try {
            String fileName = textField.getText();
            File input = new File(fileName);
            Scanner inputFile;
            inputFile = new Scanner(input).useDelimiter(",");

            //Reads first line of inputFile and skips.
            inputFile.skip("zip,city,state,latitude,longitude,timezone,dst");
            inputFile.nextLine();

            while (inputFile.hasNext()) {
                zip = Integer.parseInt(inputFile.next());
                cityName = inputFile.next();
                stateName = inputFile.next();
                latitude = Double.parseDouble(inputFile.next());
                longitude = Double.parseDouble(inputFile.next());
                timeZone = Integer.parseInt(inputFile.next());
                inputFile.skip(",");
                daylightSavings = inputFile.nextLine();

                if (daylightSavings.charAt(0) == '0') {
                    daySav = false;
                } else {
                    daySav = true;
                }
                cTemp = new City(zip, cityName, stateName, latitude, longitude, timeZone, daySav);
                adjList[index] = cTemp;
                index++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error file not found");
        } catch (IOException e) {
            System.out.println("Error IO exception");
        }

        cityList = adjList;
        graph = new CityGraph(adjList);
        graph.createGraph();
        startCombo.getItems().clear();
        destCombo.getItems().clear();

        for (int i = 0; i < 800; i++) {
            startCombo.getItems().add(adjList[i]);
            destCombo.getItems().add(adjList[i]);
        }

        readLabel.setText(index + " Cities Read");

    }

    @FXML
    private void handleDestCombo(ActionEvent event) {
        if (startCombo.getValue() != null) {
            Stack<City> path = graph.dijsktraAlg(startCombo.getValue(), destCombo.getValue());
            int pathSize;
            pathSize = path.size();
            City pathList[] = new City[pathSize];
            for (int n = 0; n < pathSize; n++) {
                pathList[n] = path.pop();
            }

            for (int i = 0; i < pathSize; i++) {
                if (i == 0) {
                    textArea.appendText("PATH:");
                    textArea.appendText("\n");
                }
                textArea.appendText(pathList[i].toString());
                textArea.appendText("\n");
            }

            numPathLabel.setText("Paths Considered: " + pathList[pathSize - 1].getPathNum().toString());
            pathDisLabel.setText(pathList[pathSize - 1].getPathDis().toString() + " Miles");
            directLabel.setText("Direct: " + pathList[0].getDistance(pathList[pathSize - 1]).toString() + " Miles");

        } else {
            numPathLabel.setText("Select start city first");
        }
    }

    @FXML
    private void handleStartCombo(ActionEvent event) {
        if (destCombo.getValue() != null) {
            Stack<City> path = graph.dijsktraAlg(startCombo.getValue(), destCombo.getValue());
            int pathSize;
            pathSize = path.size();
            City pathList[] = new City[pathSize];
            for (int n = 0; n < pathSize; n++) {
                pathList[n] = path.pop();
            }

            for (int i = 0; i < pathSize; i++) {
                if (i == 0) {
                    textArea.appendText("PATH:");
                    textArea.appendText("\n");
                }
                textArea.appendText(pathList[i].toString());
                textArea.appendText("\n");
            }

            numPathLabel.setText("Paths Considered: " + pathList[pathSize - 1].getPathNum().toString());
            pathDisLabel.setText(pathList[pathSize - 1].getPathDis().toString() + " Miles");
            directLabel.setText("Direct: " + pathList[0].getDistance(pathList[pathSize - 1]).toString() + " Miles");

        } else {
            numPathLabel.setText("Select destination city");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textField.setText("CityData5.csv");
    }

}
