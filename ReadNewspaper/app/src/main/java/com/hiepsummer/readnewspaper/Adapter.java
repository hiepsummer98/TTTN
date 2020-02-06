package com.hiepsummer.readnewspaper;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.internal.Util;

public class Adapter extends ArrayAdapter<News> {
    Activity context;
    int resource;
    List<News> objects;

    public Adapter(@NonNull Activity context, int resource, List<News> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource, null);
        ImageView ivThumbnail = convertView.findViewById(R.id.iv_thumbnail);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvPubDate = convertView.findViewById(R.id.tv_pubDate);

        News news = this.objects.get(position);
        tvTitle.setText(Html.fromHtml(news.getTitle()));
        tvPubDate.setText(news.getPubDate());
        Picasso.get().load(news.getThumbnail()).into(ivThumbnail);

        return convertView;
    }
}
