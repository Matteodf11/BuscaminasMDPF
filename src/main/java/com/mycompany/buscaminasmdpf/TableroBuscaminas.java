package com.mycompany.buscaminasmdpf;

import static com.mycompany.buscaminasmdpf.JuegoController.actualizarBoton;
import static com.mycompany.buscaminasmdpf.JuegoController.buttons;

/**
 *
 * @author Matteo
 */
public class TableroBuscaminas {

    public class Casilla {

        private boolean esMina;
        private boolean esRevelada;
        private boolean esBandera;
        private int numMinasAlrededor;

        public Casilla() {
            this.esMina = false;
            this.esRevelada = false;
            this.esBandera = false;
            this.numMinasAlrededor = 0;
        }

        public boolean isMina() {
            return esMina;
        }

        public void setMina(boolean esMina) {
            this.esMina = esMina;
        }

        public boolean isRevelada() {
            return esRevelada;
        }

        public void setRevelada(boolean esRevelada) {
            this.esRevelada = esRevelada;
        }

        public boolean isBandera() {
            return esBandera;
        }

        public void setBandera(boolean esBandera) {
            this.esBandera = esBandera;
        }

        public int getNumMinasAlrededor() {
            return numMinasAlrededor;
        }

        public void setNumMinasAlrededor(int numMinasAlrededor) {
            this.numMinasAlrededor = numMinasAlrededor;
        }
    }

    private int filas;
    private int columnas;
    private int numMinas;
    public Casilla[][] casillas;

    public TableroBuscaminas(int filas, int columnas, int numMinas) {
        this.filas = filas;
        this.columnas = columnas;
        this.numMinas = numMinas;
        this.casillas = new Casilla[filas][columnas];

        // Inicializar casillas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla();
            }
        }

        // Colocar minas
        colocarMinas();

        // Calcular números de minas alrededor
        calcularMinasAlrededor();
    }

// Método para colocar minas aleatoriamente en el tablero
private void colocarMinas() {
    int minasColocadas = 0; // Contador de minas colocadas
    while (minasColocadas < numMinas) { // Mientras no se hayan colocado todas las minas
        int fila = (int) (Math.random() * filas); // Generar una fila aleatoria
        int columna = (int) (Math.random() * columnas); // Generar una columna aleatoria

        // Si la casilla en la posición generada no tiene una mina
        if (!casillas[fila][columna].isMina()) {
            casillas[fila][columna].setMina(true); // Colocar una mina en esa casilla
            minasColocadas++; // Incrementar el contador de minas colocadas
        }
    }
}

// Método para calcular el número de minas alrededor de cada casilla
private void calcularMinasAlrededor() {
    for (int i = 0; i < filas; i++) { // Recorrer todas las filas
        for (int j = 0; j < columnas; j++) { // Recorrer todas las columnas
            // Si la casilla no tiene una mina
            if (!casillas[i][j].isMina()) {
                // Contar las minas alrededor de la casilla
                int minasAlrededor = contarMinasAlrededor(i, j);
                // Establecer el número de minas alrededor en la casilla
                casillas[i][j].setNumMinasAlrededor(minasAlrededor);
            }
        }
    }
}

// Método para contar el número de minas alrededor de una casilla específica
private int contarMinasAlrededor(int fila, int columna) {
    int contador = 0; // Contador de minas
    // Recorrer las posiciones adyacentes (incluyendo diagonales)
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            int nuevaFila = fila + i; // Calcular la nueva fila
            int nuevaColumna = columna + j; // Calcular la nueva columna
            // Si la posición es válida y contiene una mina
            if (esPosicionValida(nuevaFila, nuevaColumna) && casillas[nuevaFila][nuevaColumna].isMina()) {
                contador++; // Incrementar el contador de minas
            }
        }
    }
    return contador; // Devolver el número de minas alrededor
}

// Método para verificar si una posición está dentro de los límites del tablero
private boolean esPosicionValida(int fila, int columna) {
    return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
}

// Método para seleccionar una casilla
public void seleccionarCasilla(int fila, int columna) {
    // Si la posición es válida, la casilla no ha sido revelada y no tiene una bandera
    if (esPosicionValida(fila, columna) && !casillas[fila][columna].isRevelada() && !casillas[fila][columna].isBandera()) {
        casillas[fila][columna].setRevelada(true); // Revelar la casilla
    }
}

// Método para revelar las casillas adyacentes a una casilla específica
/*
Se utilizan dos bucles for anidados para recorrer todas las casillas alrededor de la posición especificada por fila y columna, incluidas las diagonales.
Math.max(0, fila - 1) asegura que el índice i no sea menor que 0.
Math.min(filas - 1, fila + 1) asegura que el índice i no sea mayor que el número de filas menos uno (evitando el desbordamiento del array).
Lo mismo se aplica para j con columna.
*/
public void revelarCasillasAdyacentes(int fila, int columna) {
    // Recorrer las casillas alrededor de la posición especificada (incluyendo diagonales)
    for (int i = Math.max(0, fila - 1); i <= Math.min(filas - 1, fila + 1); i++) {
        for (int j = Math.max(0, columna - 1); j <= Math.min(columnas - 1, columna + 1); j++) {
            // Si la casilla no ha sido revelada y no tiene una bandera
            if (!casillas[i][j].isRevelada() && !casillas[i][j].isBandera()) {
                casillas[i][j].setRevelada(true); // Revelar la casilla
                actualizarBoton(buttons[i][j], i, j); // Actualizar el botón de la interfaz

                // Si la casilla revelada no tiene minas alrededor, llamar recursivamente
                if (casillas[i][j].getNumMinasAlrededor() == 0) {
                    revelarCasillasAdyacentes(i, j); // Llamar recursivamente para revelar casillas adyacentes
                }
            }
        }
    }
}

// Método para verificar si todas las casillas sin mina han sido reveladas
public boolean todasCasillasSinMinaReveladas() {
    for (int i = 0; i < filas; i++) { // Recorrer todas las filas
        for (int j = 0; j < columnas; j++) { // Recorrer todas las columnas
            // Si una casilla no tiene mina y no ha sido revelada
            if (!casillas[i][j].isMina() && !casillas[i][j].isRevelada()) {
                return false; // Devolver false si alguna casilla sin mina no ha sido revelada
            }
        }
    }
    return true; // Devolver true si todas las casillas sin mina han sido reveladas
}

// Método para alternar el estado de bandera en una casilla específica
public void alternarBandera(int fila, int columna) {
    // Si la posición es válida y la casilla no ha sido revelada
    if (esPosicionValida(fila, columna) && !casillas[fila][columna].isRevelada()) {
        // Alternar el estado de bandera de la casilla
        casillas[fila][columna].setBandera(!casillas[fila][columna].isBandera());
    }
}
}
