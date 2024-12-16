/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.buscaminasmdpf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Matteo
 */
public class AñadirModificarController{

    

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
        persona.setNombre(nombrepersona.getText());
        persona.setPathImagen(imagenpersona.getText());

        Stage stage = (Stage) salvar.getScene().getWindow();
        stage.close();
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

 

}
