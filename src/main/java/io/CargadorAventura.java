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
import java.util.Properties;

public class CargadorAventura {

    private Gson gson;
    private Path directorioBase;
    private Path archivoAventura;

    public void cargarConfiguracion() throws IOException {
        Properties props = new Properties();

        // usamos nio 2 para abrir el fichero
        try (var reader = Files.newBufferedReader(Paths.get("config.properties"))) {
            props.load(reader);
        }

        directorioBase = Paths.get(props.getProperty("juego.directorio.base"));
        // el path resolve nos va a combinar el directorio
        archivoAventura = directorioBase.resolve(props.getProperty("juego.archivo.base"));

        // Construimos el Gson con el adapter registrado
        gson = new GsonBuilder()
                .registerTypeAdapter(Objeto.class, new ObjetoAdapter())
                .setPrettyPrinting()
                .create();
    }


    /**
     * Lee el archivo JSON de la aventura y devuelve la configuración del juego,
     * incluyendo la descripción general y el mapa de habitaciones.
     *
     * @throws IOException Si hay un problema al acceder al archivo JSON.
     */
    public AventuraConfig cargarMundoBase() throws IOException {
        try {
            return gson.fromJson(Files.newBufferedReader(archivoAventura), AventuraConfig.class);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}