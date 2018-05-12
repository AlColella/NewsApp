package com.example.alexandre.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    ProgressBar pbar;
    NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbar = findViewById(R.id.indeterminateBar);
        pbar.setVisibility(View.VISIBLE);

        boolean isConnected = ConnManager.verifyConnection(MainActivity.this);

        if (isConnected) {
            mAdapter = new NewsAdapter(this, 0, new ArrayList<News>());
            ListView listView = findViewById(R.id.list);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openWebPage(mAdapter.getItem(position).getUrl());
                }
            });
            getLoaderManager().initLoader(1, null, MainActivity.this);
        } else {
            ListView listView = findViewById(R.id.list);
            TextView emptyView = findViewById(R.id.empty_list_item);
            emptyView.setText(R.string.noInternet);
            listView.setEmptyView(emptyView);
            pbar.setVisibility(View.GONE);
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            pbar.setVisibility(View.VISIBLE);
            mAdapter.clear();
            getLoaderManager().restartLoader(1, null, MainActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, final ArrayList<News> data) {
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else {
            ListView listView = findViewById(R.id.list);
            listView.setEmptyView(findViewById(R.id.empty_list_item));
        }
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        mAdapter.clear();
    }


}
