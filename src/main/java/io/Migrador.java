package io;

// Importa Gson, una librería para convertir objetos Java a JSON y viceversa
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Importa las clases del dominio del proyecto
import domain.Habitacion;
import domain.Objeto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Migrador {

    // Ruta donde se guardará el archivo JSON generado
    private static final String RUTA_SALIDA = "src/main/resources/aventura.json";

    // Método que recibe un mapa de habitaciones y lo convierte a JSON
    public static void migrar(Map<String, Habitacion> habitaciones) {

        // Crea el objeto Gson configurado para:
        // - usar un adaptador personalizado para Objeto
        // - formatear el JSON con sangría para que sea legible
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Objeto.class, new ObjetoAdapter())
                .setPrettyPrinting()
                .create();

        // Crea la configuración principal de la aventura
        AventuraConfig config = new AventuraConfig(
                "Matapiojos DLC - Aventura de cuchillos", habitaciones
        );

        // Convierte el objeto config a una cadena JSON
        String json = gson.toJson(config);

        try {
            // Convierte la ruta en un objeto Path
            Path ruta = Paths.get(RUTA_SALIDA);

            // Crea los directorios necesarios si no existen
            Files.createDirectories(ruta.getParent());

            // Escribe el JSON en el archivo indicado
            Files.writeString(ruta, json);

            // Mensaje de exito
            System.out.println("Archivo aventura.json generado correctamente");
        } catch (IOException e) {
            // Si ocurre un error al escribir el archivo, muestra el mensaje
            System.out.println(e.getMessage());
        }
    }
}