package com.developer.barbosa.eventoapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno Barbosa on 30/09/2016.
 */
public class JsonUtil {

    public static List<ItemVideo> fromJson(String json) throws JSONException {

        List<ItemVideo> lista = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(json);
        for(int i=0;i<jsonArray.length();i++){
            JSONObject object = (JSONObject) jsonArray.get(i);
            String titulo = object.getString("titulo");
            String data = object.getString("data");
            String url = object.getString("url");

            lista.add(new ItemVideo(titulo,data,url));
        }

        return lista;
    }
}
