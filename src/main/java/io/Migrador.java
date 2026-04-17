package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Habitacion;
import domain.Objeto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Migrador {
    private static final String RUTA_SALIDA = "src/main/resources/aventura.json";

    public static void migrar(Map<String, Habitacion> habitaciones) {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Objeto.class, new ObjetoAdapter())
                .setPrettyPrinting()
                .create();

        AventuraConfig config = new AventuraConfig(
                "Matapiojos DLC - Aventura de cuchillos", habitaciones
        );

        String json = gson.toJson(config);

        try {
            Path ruta = Paths.get(RUTA_SALIDA);

            Files.createDirectories(ruta.getParent());
            Files.writeString(ruta, json);

            System.out.println("Archivo aventura.json generado correctamente");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
