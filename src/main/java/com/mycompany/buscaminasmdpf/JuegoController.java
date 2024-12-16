package com.mycompany.buscaminasmdpf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JuegoController implements Initializable {

    @FXML
    private MenuItem menuSalir;
    @FXML
    private MenuItem menuVerInfo;
    @FXML
    private MenuButton menuDificultad;
    @FXML
    private static RadioMenuItem menuFacil = new RadioMenuItem();
    @FXML
    private static RadioMenuItem menuMedio = new RadioMenuItem();
    @FXML
    private static RadioMenuItem menuDificil = new RadioMenuItem();
    @FXML
    private static RadioMenuItem menuPersonalizado = new RadioMenuItem();
    @FXML
    private StackPane stackpane;

    private static int contador = 0;

    static Button[][] buttons;
    private Stage primaryStage;
    static TableroBuscaminas tableroBuscaminas;

    private static Timeline timeline;
    private static int tiempo;
    @FXML
    private Label menuJuego;

    private static String nombrepersona;
    @FXML
    private ToggleGroup Dificultad;

    public JuegoController() {
        tiempo = 0;
    }

  

    @FXML
    private void salirAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            stage.setScene(scene);
            stage.setTitle("Buscaminas MDPF 1º DAM");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para iniciar un nuevo juego de buscaminas
private void iniciarJuego(int filas, int columnas, int minas) {
    contador = 0; // Reiniciar el contador que he creado para limitar la salida de las alertas perder y ganar
    stackpane.getChildren().clear(); // Eliminar el tablero actual de la interfaz

    // Crear un nuevo tablero de buscaminas con las dimensiones y el número de minas especificadas
    tableroBuscaminas = new TableroBuscaminas(filas, columnas, minas);
    GridPane gridTablero = new GridPane(); // Crear un nuevo GridPane para el tablero de botones
    gridTablero.setAlignment(Pos.CENTER); // Centrar el GridPane en el contenedor

    buttons = new Button[filas][columnas]; // Iniciar la matriz de botones con las dimensiones del tablero

    // Recorrer todas las filas y columnas para crear los botones del tablero
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            Button button = new Button(); // Crear un nuevo botón
            buttons[i][j] = button; // Almacenar el botón en la matriz
            button.setMinSize(30, 30); // Establecer el tamaño mínimo del botón

            final int posFila = i; // Guardar la posición de la fila
            final int posColumna = j; // Guardar la posición de la columna

            // Establecer el manejador de eventos de clic para el botón
            button.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) { // Si se hace clic con el botón izquierdo del ratón
                    tableroBuscaminas.seleccionarCasilla(posFila, posColumna); // Seleccionar la casilla en el tablero de buscaminas
                    actualizarBoton(button, posFila, posColumna); // Actualizar la interfaz del botón

                } else if (e.getButton() == MouseButton.SECONDARY) { // Si se hace clic con el botón derecho del ratón
                    tableroBuscaminas.alternarBandera(posFila, posColumna); // Alternar la bandera en la casilla
                    actualizarBotonBandera(button, posFila, posColumna); // Actualizar la interfaz del botón para mostrar la bandera
                }
            });

            // Establecer el estilo del botón utilizando CSS
            button.setStyle(
                "-fx-background-color: rgba(0, 51, 102, 0.7); " // Color de fondo del botón
                + "-fx-text-fill: #e0f7fa; " // Color del texto
                + "-fx-font-weight: bold; " // Peso de la fuente en negrita
                + "-fx-border-color: #004080; " // Color del borde del botón
                + "-fx-border-width: 2px; " // Ancho del borde
                + "-fx-border-radius: 5px; " // Radio de los bordes (esquinas redondeadas)
                + "-fx-background-radius: 5px; " // Radio del fondo (esquinas redondeadas)
                + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0, 0);" // Efecto de sombra
            );

            gridTablero.add(button, j, i); // Añadir el botón al GridPane
        }
    }

    stackpane.getChildren().add(gridTablero); // Añadir el GridPane con los botones al StackPane principal
}

    static void mostrarAlertaPerdida() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Perdiste");
        alert.setHeaderText("Has perdido el juego");
        alert.setContentText("¡Lo siento! Has tocado una mina y has perdido.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Mostrar el contenido de todos los botones y deshabilitarlos
            for (Button[] buttonRow : buttons) {
                for (Button button : buttonRow) {
                    button.setDisable(true);
                }
            }
        }
    }

    static void actualizarBoton(Button button, int fila, int columna) {
    TableroBuscaminas.Casilla casilla = tableroBuscaminas.casillas[fila][columna];
    // Verifica si la casilla está revelada
    if (casilla.isRevelada()) {
        // Verifica si la casilla es una mina
        if (casilla.isMina()) {
            // Si es una mina, muestra '*' en el botón y lo resalta en rojo
            button.setText("*");
            button.setStyle("-fx-background-color: red;");
            // Muestra un mensaje de derrota si se detecta una mina
            if (contador < 1) {
                mostrarAlertaPerdida();
                detenerContador();
                contador++;
            }
            return; // Finaliza la ejecución del método
        } else {
            // Si no es una mina, muestra el número de minas adyacentes en el botón y lo desactiva
            button.setText(String.valueOf(casilla.getNumMinasAlrededor()));
            button.setDisable(true);
            button.setStyle(
                    "-fx-background-color: linear-gradient(to right, #00bfff, #00ffff); " // Gradiente de colores neon azules
                    + "-fx-text-fill: #ba00b3; " // Color de texto dorado
                    + "-fx-font-weight: bold; " // Texto en negrita
                    + "-fx-border-color: #00bfff; " // Color del borde neon azul
                    + "-fx-border-width: 2px; " // Ancho del borde
                    + "-fx-border-radius: 5px; " // Radio del borde
                    + "-fx-background-radius: 5px; " // Radio del fondo
            );
        }
    }
    // Si no hay minas alrededor, revela las casillas adyacentes
    if (casilla.getNumMinasAlrededor() == 0) {
        tableroBuscaminas.revelarCasillasAdyacentes(fila, columna);
    }

    // Verifica si todas las casillas sin mina han sido reveladas
    if (tableroBuscaminas.todasCasillasSinMinaReveladas()) {
        // Muestra un mensaje de victoria si es la primera vez que se detecta que todas las casillas sin mina han sido reveladas
        if (contador < 1) {
            detenerContador();
            mostrarAlertaVictoria();
            contador++;
        }
    }
}

    private void actualizarBotonBandera(Button button, int fila, int columna) {
        TableroBuscaminas.Casilla casilla = tableroBuscaminas.casillas[fila][columna];
        if (casilla.isBandera()) {
            button.setText("⚑");
        } else {
            button.setText("");
        }
    }

    @FXML
    private void ayudaAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ayuda");
        alert.setHeaderText("Cómo jugar Buscaminas");
        alert.setContentText("El objetivo del juego es revelar todas las casillas que no contienen minas. "
                + "Si revelas una mina, pierdes. Las casillas muestran el número de minas adyacentes. "
                + "Usa clic derecho para colocar o quitar banderas en las casillas sospechosas.");
        alert.showAndWait();
    }

    static void mostrarAlertaVictoria() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("¡Has ganado!");
    dialog.setHeaderText("¡Felicidades, has revelado todas las casillas seguras!");
    dialog.setContentText("Por favor, ingresa tu nombre:");

    Optional<String> result;
    String nombre = "";

    do {
        result = dialog.showAndWait();
        if (result.isPresent()) {
            nombre = result.get().trim();
            if (nombre.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Nombre no válido");
                alert.setContentText("El campo de nombre no puede estar vacío. Por favor, ingrese su nombre.");
                alert.showAndWait();
            }
        }
    } while (nombre.isEmpty());

    for (Button[] buttonRow : buttons) {
        for (Button button : buttonRow) {
            button.setDisable(true);
        }
    }

   

    // Guardar el nombre y el tiempo en el archivo correspondiente según la dificultad
    if (menuFacil.isSelected()) {
        System.out.println("Dificultad: Fácil");
        guardarDatos(nombre, tiempo, "./Facil.txt");
    } else if (menuMedio.isSelected()) {
        System.out.println("Dificultad: Medio");
        guardarDatos(nombre, tiempo, "./Media.txt");
    } else if (menuDificil.isSelected()) {
        System.out.println("Dificultad: Difícil");
        guardarDatos(nombre, tiempo, "./Dificil.txt");
    } else if (menuPersonalizado.isSelected()) {
        guardarDatos(nombre, tiempo, "./Personalizada.txt");
    }

    // Guardar el jugador en el archivo de jugadores
    guardarJugador(new Persona(nombre, "./src/main/resources/Imagenes/defecto.png"));
}

    private static void guardarJugador(Persona persona) {
        boolean exists = false;
        try ( BufferedReader br = new BufferedReader(new FileReader("./Jugadores.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(persona.getNombre())) {
                    exists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!exists) {
            try ( BufferedWriter bw = new BufferedWriter(new FileWriter("./Jugadores.txt", true))) { // El parámetro 'true' indica que se agregará al final del archivo si ya existe
                bw.write(persona.getNombre() + "," + persona.getImagen());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void guardarDatos(String nombre, int tiempo, String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath, true); // El parámetro 'true' indica que se agregará al final del archivo si ya existe
            PrintWriter pw = new PrintWriter(fw);

            pw.println(nombre + "," + tiempo);
            pw.close();
            System.out.println("Datos guardados exitosamente en el archivo: " + filePath);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en el archivo: " + filePath);
            e.printStackTrace();
        }
    }

    @FXML
    private void iniciarFacilAction(ActionEvent event) {
        iniciarContador();
        iniciarJuego(8, 8, 10);
        menuFacil.setSelected(true); // Asegurarse de que la opción esté seleccionada
        menuMedio.setSelected(false);
        menuDificil.setSelected(false);
        menuPersonalizado.setSelected(false);
    }

    @FXML
    private void iniciarMedioAction(ActionEvent event) {
        iniciarContador();
        iniciarJuego(16, 16, 40);
        menuMedio.setSelected(true); // Asegurarse de que la opción esté seleccionada
        menuFacil.setSelected(false);
        menuDificil.setSelected(false);
        menuPersonalizado.setSelected(false);

    }

    @FXML
    private void iniciarDificilAction(ActionEvent event) {
        iniciarContador();
        iniciarJuego(16, 30, 99);
        menuDificil.setSelected(true); // Asegurarse de que la opción esté seleccionada
        menuMedio.setSelected(false);
        menuFacil.setSelected(false);
        menuPersonalizado.setSelected(false);
    }

    @FXML
    private void iniciarPersonalizadoAction(ActionEvent event) {
    int maxFilas = 20;
    int maxColumnas = 32;

    while (true) { // Bucle hasta que la configuración sea válida
        String mensajeLimites = String.format("Ingrese filas, columnas, minas (Ejemplo: 10,10,15). Límites: filas <= %d, columnas <= %d, minas <= tamaño del tablero - 1 y > 0", maxFilas, maxColumnas);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Dificultad personalizada");
        dialog.setHeaderText("Configuración de dificultad personalizada");
        dialog.setContentText(mensajeLimites);

        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return; // Si el usuario cancela el diálogo, salir del método
        }

        String config = result.get();
        String[] partes = config.split(",");
        if (partes.length == 3) {
            try {
                int filas = Integer.parseInt(partes[0]);
                int columnas = Integer.parseInt(partes[1]);
                int maxMinas = filas * columnas - 1;

                int minas = Integer.parseInt(partes[2]);

                // Verifica si la configuración está dentro de los límites y es válida
                if (filas <= maxFilas && columnas <= maxColumnas && minas <= maxMinas && minas > 0) {
                    iniciarContador();
                    iniciarJuego(filas, columnas, minas);
                    break; // Configuración válida, salir del bucle
                } else {
                    mostrarErrorConfiguracionConLimites(maxFilas, maxColumnas, maxMinas);
                }
            } catch (NumberFormatException e) {
                mostrarErrorConfiguracion();
            }
        } else {
            mostrarErrorConfiguracion();
        }
    }
    // Asegura que la opción de juego personalizado esté seleccionada en el menú
    menuPersonalizado.setSelected(true);
    menuMedio.setSelected(false);
    menuFacil.setSelected(false);
    menuDificil.setSelected(false);
}

    private void mostrarErrorConfiguracionConLimites(int maxFilas, int maxColumnas, int maxMinas) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de configuración");
        alert.setHeaderText("Configuración inválida");
        alert.setContentText(String.format("Las filas deben ser <= %d, las columnas <= %d y las minas <= tamaño del tablero - 1", maxFilas, maxColumnas));
        alert.showAndWait();
    }

    private void mostrarErrorConfiguracion() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error de configuración");
        alert.setHeaderText("Configuración inválida");
        alert.setContentText("Por favor, ingrese una configuración válida en el formato correcto (Ejemplo: 10,10,15).");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempo++;
            menuJuego.setText("Tiempo: " + tiempo + "s");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        Dificultad.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                oldToggle.setSelected(true);
            }
        });
    }

    // Método para iniciar el contador
    private void iniciarContador() {
        tiempo = 0;
        timeline.play();
    }

    // Método para detener el contador
    private static void detenerContador() {
        timeline.stop();
    }

    // Método para reiniciar el contador
    private void reiniciarContador() {
        detenerContador();
        menuJuego.setText("Tiempo: 0s");
    }
}
