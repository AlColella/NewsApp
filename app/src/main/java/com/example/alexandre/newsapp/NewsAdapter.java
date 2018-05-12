package com.example.alexandre.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, int resource, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView titView = listItemView.findViewById(R.id.tit_item);
        titView.setText(currentNews.getTitulo());

        TextView secaoView = listItemView.findViewById(R.id.sec_item);
        secaoView.setText(currentNews.getSecao());

        TextView dateView = listItemView.findViewById(R.id.date_item);
        dateView.setText(currentNews.getData());

        ImageView imgView = listItemView.findViewById(R.id.img_item);
        imgView.setImageBitmap(currentNews.getImagem());

        return listItemView;
    }
}
