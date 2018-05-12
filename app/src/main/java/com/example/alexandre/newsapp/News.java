package com.example.alexandre.newsapp;

import android.graphics.Bitmap;

public class News {

    private String mTitulo;
    private String mSecao;
    private String mData;
    private Bitmap mImagem;
    private String mUrl;

    public News(String titulo, String secao, String data, String urls, Bitmap imagem) {
        mTitulo = titulo;
        mSecao = secao;
        mData = data;
        mUrl = urls;
        mImagem = imagem;
    }

    public String getTitulo() {
        return mTitulo;
    }

    public String getSecao() {
        return mSecao;
    }

    public String getData() {
        return mData;
    }

    public String getUrl() {
        return mUrl;
    }

    public Bitmap getImagem() {
        return mImagem;
    }
}
