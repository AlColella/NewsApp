package com.example.alexandre.newsapp;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader {

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> news = NewsQuery.extractNews();
        return news;
    }
}
