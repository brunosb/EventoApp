package com.developer.barbosa.eventoapp;

import java.io.Serializable;

/**
 * Created by Bruno Barbosa on 30/09/2016.
 */
public class ItemVideo implements Serializable{

    private String titulo;
    private String data;
    private String url;

    public ItemVideo(String titulo, String data, String url) {
        this.titulo = titulo;
        this.data = data;
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
