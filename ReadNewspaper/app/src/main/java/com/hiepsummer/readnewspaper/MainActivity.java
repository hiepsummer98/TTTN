package com.hiepsummer.readnewspaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Adapter adapter;
    ArrayList<News> arrNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewTieuDe);
        MyTask myTask = new MyTask();
        myTask.execute("https://vnexpress.net/rss/so-hoa.rss");

    }

    class MyTask extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            arrNews = new ArrayList<>();
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements elements = document.select("item");
                News news = null;

                for (Element element : elements) {
                    news = new News();
                    news.setTitle(element.select("title").text());
                    news.setThumbnail(Jsoup.parse(element.select("description").text()).select("img").attr("src"));
                    news.setLink(element.select("link").text());
                    news.setPubDate(element.select("pubDate").text().replace("+0700", ""));

                    arrNews.add(news);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrNews;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            super.onPostExecute(news);

            adapter = new Adapter(MainActivity.this, R.layout.items, arrNews);
            listView.setAdapter(adapter);
        }
    }

}