package io;


import com.google.gson.*;
import domain.Objeto;

import java.lang.reflect.Type;

/**
 * Adaptador personalizado para la librería Gson que maneja el polimorfismo de la clase Objeto.
 * Permite serializar y deserializar correctamente las subclases
 * inyectando y leyendo un campo identificador de tipo en el JSON.
 */

public class ObjetoAdapter implements JsonSerializer<Objeto>, JsonDeserializer<Objeto> {

    /**
     * Proceso de lectura.
     * Intercepta la lectura del JSON, busca qué tipo exacto de objeto era originalmente,
     * y le dice a Gson que lo reconstruya usando esa subclase específica.
     *
     * @param json    El elemento JSON que se está leyendo
     * @param typeOfT El tipo de objeto solicitado
     * @param context Contexto de deserialización de Gson
     * @return Una instancia de la subclase correcta de Objeto
     * @throws JsonParseException Si la clase especificada en el JSON no existe en el proyecto
     */
    @Override
    public Objeto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonPrimitive primitive = (JsonPrimitive) jsonObject.get("tipo");
        String className = primitive.getAsString();

        try {
            // 2. Intentamos obtener la Clase real a partir del string
            Class<?> clazz = Class.forName(className);

            // 3. Le pedimos a Gson que deserialice el JSON usando esa clase específica
            return context.deserialize(jsonObject, clazz);

        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Error al encontrar la clase para deserializar: " + className, e);
        }
    }

    /**
     * Proceso de escritura.
     * Intercepta el guardado del objeto para añadirle una etiqueta oculta
     * que indica exactamente qué subclase es.
     *
     * @param src       El objeto Java original que se va a guardar
     * @param typeOfSrc El tipo exacto del objeto en tiempo de ejecución
     * @param context   Contexto de serialización de Gson
     * @return El elemento JSON modificado y listo para escribirse en el archivo
     */

    @Override
    public JsonElement serialize(Objeto src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement element = context.serialize(src);
        JsonObject object = element.getAsJsonObject();

        object.addProperty("tipo", src.getClass().getName());

        return object;
    }
}