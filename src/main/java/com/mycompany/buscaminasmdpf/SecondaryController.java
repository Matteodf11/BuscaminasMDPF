package com.mycompany.buscaminasmdpf;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SecondaryController implements Initializable {

    @FXML
    private TableView<Persona> tableviewjugadores;
    @FXML
    private TableColumn<Persona, String> nombrecolumna;
    @FXML
    private TableColumn<Persona, String> imagencolumna;

    static ObservableList<Persona> datosObservableList;
    @FXML
    private Button botonañadir;
    @FXML
    private Button botonmodificar;
    
    @FXML
    private Button botoneliminar;
    @FXML
    private Button botonsalir;

    @FXML
    private void añadirAction(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        FXMLLoader miCargador = new FXMLLoader(getClass().getClassLoader().getResource("com/mycompany/buscaminasmdpf/Añadir.fxml"));
        Parent root = miCargador.load();

        AñadirController controladorPersona = miCargador.<AñadirController>getController();

        Persona persona;

        persona = new Persona("", "");
        controladorPersona.initPersona(persona);

        Stage stage = new Stage();

        stage.setTitle("Añadir Persona");

        stage.setScene(new Scene(root, 580, 350));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

       
        

        
    }

    @FXML
    private void modificarAction(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getClassLoader().getResource("com/mycompany/buscaminasmdpf/Modificar.fxml"));
        Parent root = miCargador.load();

        ModificarController controladorPersona = miCargador.<ModificarController>getController();

        Persona persona;

        persona = tableviewjugadores.getSelectionModel().getSelectedItem();
        controladorPersona.initPersona(persona);

        Stage stage = new Stage();

        stage.setTitle("Modificar Persona");

        stage.setScene(new Scene(root, 580, 350));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        
        

    }


    @FXML
    private void eliminarAction(ActionEvent event) {
        datosObservableList.remove(tableviewjugadores.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void salirAction(ActionEvent event) {
        Auxiliar.guardarPersonasJugadores(datosObservableList);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botoneliminar.disableProperty().bind(Bindings.equal(-1, tableviewjugadores.getSelectionModel().selectedIndexProperty()));
        botonmodificar.disableProperty().bind(Bindings.equal(-1, tableviewjugadores.getSelectionModel().selectedIndexProperty()));
        

        ArrayList<Persona> datosArrayList = new ArrayList<>();
        datosArrayList = Auxiliar.leerPersonasJugadores("./Jugadores.txt");

        nombrecolumna.setCellValueFactory(new PropertyValueFactory<Persona, String>("Nombre"));
        imagencolumna.setCellValueFactory(cellData -> cellData.getValue().pathImagenProperty());
        imagencolumna.setCellFactory(columna -> {
            return new TableCell<Persona, String>() {
                private ImageView view = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        File imageFile = new File(item);
                        String fileLocation = imageFile.toURI().toString();
                        Image image = new Image(fileLocation, 80, 80, true, true);
                        view.setImage(image);
                        setGraphic(view);
                    }
                }
            };
        });

        datosObservableList = FXCollections.observableArrayList(datosArrayList);
        tableviewjugadores.setItems(datosObservableList);
    }
}
