/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.buscaminasmdpf;

import static com.mycompany.buscaminasmdpf.SecondaryController.datosObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Matteo
 */
public class ModificarController implements Initializable {

    @FXML
    private TextField nombrepersona;
    @FXML
    private Button salvar;
    @FXML
    private Button buttoncancel;

    private Persona persona;

    @FXML
    private TextField imagenpersona;

    @FXML
    private void salvarAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getId().equals("salvar")) {
            if (nombrepersona.getText().isEmpty() && imagenpersona.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo vacio");
                alert.setHeaderText(null);
                alert.setContentText("No pueden haber campos vacios");

                alert.showAndWait();
                return;

            }
            
            
            persona.setNombre(nombrepersona.getText());
            persona.setPathImagen(imagenpersona.getText());

            Stage stage = (Stage) salvar.getScene().getWindow();
            stage.close();

             int indice = datosObservableList.indexOf(persona);
        if (indice >= 0) {
            datosObservableList.set(indice, persona);
        }


        }
    }

    @FXML
    private void cancelarAction(ActionEvent event) {

        Stage stage = (Stage) buttoncancel.getScene().getWindow();
        stage.close();
    }

    public void initPersona(Persona persona) {
        this.persona = persona;
        nombrepersona.setText(persona.getNombre());
        imagenpersona.setText(persona.getImagen());
    }

    public Persona getPersona() {
        return persona;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
