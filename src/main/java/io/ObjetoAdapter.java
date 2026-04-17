package io;

import com.google.gson.*;
import domain.*;

import java.lang.reflect.Type;

public class ObjetoAdapter implements JsonSerializer<Objeto>, JsonDeserializer<Objeto> {
private static final String CAMPO_TIPO="tipo";
@Override
    public JsonElement serialize(Objeto src, Type typeOfSrc, JsonSerializationContext context){
    //Obtener el tipo del obheto
    String tipo = src.getClass().getSimpleName().toLowerCase();

    //Serializamos el objeto sin usar este adapter
    JsonObject jsonObject = context.serialize(src, src.getClass()).getAsJsonObject();

    jsonObject.addProperty(CAMPO_TIPO, tipo);
    return jsonObject;
}
    @Override
    public Objeto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        // Leemos el campo tipo
        if (!jsonObject.has(CAMPO_TIPO)) {
            throw new JsonParseException("Falta el campo '" + CAMPO_TIPO + "' en el JSON del objeto.");
        }

        String tipo = jsonObject.get(CAMPO_TIPO).getAsString();

        return switch (tipo) {
            case "llave"      -> context.deserialize(jsonObject, Llave.class);
            case "nota"       -> context.deserialize(jsonObject, Nota.class);
            case "contenedor" -> context.deserialize(jsonObject, Contenedor.class);
            case "mueble"     -> context.deserialize(jsonObject, Mueble.class);
            case "item"       -> context.deserialize(jsonObject, Item.class);
            case "mangorotollave" -> context.deserialize(jsonObject, MangoRotoLlave.class);
            case "palorotollave" -> context.deserialize(jsonObject, PaloRotoLlave.class);
            default -> throw new JsonParseException("Tipo de objeto desconocido: '" + tipo + "'");
        };
    }
}
//@author Jav8-dev|@version 1.0.