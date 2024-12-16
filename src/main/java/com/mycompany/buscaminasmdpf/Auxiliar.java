/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.buscaminasmdpf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author matdipfor
 */
public class Auxiliar {

    public static ArrayList<Persona> leerPersonasJugadores(String filePath) {
        ArrayList<Persona> personas = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] datos = line.split(",");
                if (datos.length == 2) {
                    String nombre = datos[0];
                    String imagen = datos[1];

                    personas.add(new Persona(nombre, imagen));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public static void guardarPersonasJugadores(List<Persona> personas) {

        String archivo = "./Jugadores.txt";
        try {
            FileWriter fw = new FileWriter(archivo);
            for (Persona persona : personas) {
                fw.write(persona.getNombre() + "," + persona.getImagen());
                fw.write("\n"); // escribimos nueva línea
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Persona> leerPersonasJugadoresRanking(String filePath) {
        ArrayList<Persona> personas = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] datos = line.split(",");
                if (datos.length == 2) {
                    String nombre = datos[0];
                    Integer tiempo = Integer.parseInt(datos[1]);

                    personas.add(new Persona(nombre, tiempo));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public static void guardarPersonasJugadoresRankingFacil(List<Persona> personas) {

        String archivo = "Facil.txt";
        try {
            FileWriter fw = new FileWriter(archivo);
            for (Persona persona : personas) {
                fw.write(persona.getNombre() + "," + persona.getTiempo());
                fw.write("\n"); // escribimos nueva línea
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarPersonasJugadoresRankingMedia(List<Persona> personas) {

        String archivo = "Media.txt";
        try {
            FileWriter fw = new FileWriter(archivo);
            for (Persona persona : personas) {
                fw.write(persona.getNombre() + "," + persona.getTiempo());
                fw.write("\n"); // escribimos nueva línea
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarPersonasJugadoresRankingDificil(List<Persona> personas) {

        String archivo = "Dificil.txt";
        try {
            FileWriter fw = new FileWriter(archivo);
            for (Persona persona : personas) {
                fw.write(persona.getNombre() + "," + persona.getTiempo());
                fw.write("\n"); // escribimos nueva línea
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarPersonasJugadoresRankingPersonalizada(List<Persona> personas) {

        String archivo = "Personalizada.txt";
        try {
            FileWriter fw = new FileWriter(archivo);
            for (Persona persona : personas) {
                fw.write(persona.getNombre() +  persona.getTiempo());
                fw.write("\n"); // escribimos nueva línea
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
