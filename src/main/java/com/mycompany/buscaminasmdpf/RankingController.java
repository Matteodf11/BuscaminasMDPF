/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.buscaminasmdpf;

import static com.mycompany.buscaminasmdpf.SecondaryController.datosObservableList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Matteo
 */
public class RankingController implements Initializable {

    @FXML
    private Button botonfacil;
    @FXML
    private Button botonmedia;
    @FXML
    private Button botondificil;
    @FXML
    private Button botonpersonalizado;
    @FXML
    private Button botonsalir;
    @FXML
    private TableView<Persona> tableviewjugadores;
    @FXML
    private TableColumn<Persona, String> nombrecolumna;

    static ArrayList<Persona> datosArrayList = new ArrayList<>();

    static ObservableList<Persona> datosObservableListRanking;
    @FXML
    private TableColumn<Persona, Integer> tiempocolumna;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        nombrecolumna.setCellValueFactory(new PropertyValueFactory<Persona, String>("Nombre"));
        tiempocolumna.setCellValueFactory(new PropertyValueFactory<Persona, Integer>("Tiempo"));

    }

    @FXML
    private void facilAction(ActionEvent event) {
        datosArrayList = Auxiliar.leerPersonasJugadoresRanking("./Facil.txt");
        Collections.sort(datosArrayList, Comparator.comparingInt(Persona::getTiempo));
        datosObservableListRanking = FXCollections.observableArrayList(datosArrayList);
        tableviewjugadores.setItems(datosObservableListRanking);
        tableviewjugadores.refresh();
        tiempocolumna.setSortable(false);
        nombrecolumna.setSortable(false);

    }

    @FXML
    private void mediaAction(ActionEvent event) {
        datosArrayList = Auxiliar.leerPersonasJugadoresRanking("./Media.txt");
        Collections.sort(datosArrayList, Comparator.comparingInt(Persona::getTiempo));
        datosObservableListRanking = FXCollections.observableArrayList(datosArrayList);
        tableviewjugadores.setItems(datosObservableListRanking);
        tableviewjugadores.refresh();
        tiempocolumna.setSortable(false);
        nombrecolumna.setSortable(false);

    }

    @FXML
    private void dificilAction(ActionEvent event) {
        datosArrayList = Auxiliar.leerPersonasJugadoresRanking("./Dificil.txt");
        Collections.sort(datosArrayList, Comparator.comparingInt(Persona::getTiempo));
        datosObservableListRanking = FXCollections.observableArrayList(datosArrayList);
        tableviewjugadores.setItems(datosObservableListRanking);
        tableviewjugadores.refresh();
        tiempocolumna.setSortable(false);
        nombrecolumna.setSortable(false);

    }

    @FXML
    private void personalizadoAction(ActionEvent event) {
        datosArrayList = Auxiliar.leerPersonasJugadoresRanking("./Personalizada.txt");
        Collections.sort(datosArrayList, Comparator.comparingInt(Persona::getTiempo));
        datosObservableListRanking = FXCollections.observableArrayList(datosArrayList);
        tableviewjugadores.setItems(datosObservableListRanking);
        tableviewjugadores.refresh();
        tiempocolumna.setSortable(false);
        nombrecolumna.setSortable(false);

    }

    @FXML
    private void salirAction(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Buscaminas MDPF 1º DAM");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
