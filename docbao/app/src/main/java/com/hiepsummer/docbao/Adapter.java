package com.hiepsummer.docbao;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends ArrayAdapter<New> {
    Activity context;
    int resource;
    List<New> objects;

    public Adapter(Activity context, int resource, List<New> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource, null);
        ImageView ivThumbnail = convertView.findViewById(R.id.imageViewThumb);
        TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
        TextView tvPubDate = convertView.findViewById(R.id.textViewPublicDate);

        New news = this.objects.get(position);
        tvTitle.setText(news.getTitle());
        tvPubDate.setText(news.getPubDate());
        Picasso.get().load(news.getImg()).into(ivThumbnail);

        return convertView;
    }

}