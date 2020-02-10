package com.hiepsummer.docbao;

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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource, null);

        try {
            New news = getItem(position);
            if (news != null) {
                ImageView ivThumbnail = convertView.findViewById(R.id.imageViewThumb);
                TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
                TextView tvPubDate = convertView.findViewById(R.id.textViewPublicDate);

                tvTitle.setText(Html.fromHtml(news.getTitle()));
                tvPubDate.setText(news.getPubDate());
                if (news.getImg().isEmpty()) {
                    ivThumbnail.setImageResource(R.drawable.ic_launcher_background);
                } else {
                    Picasso.get().load(news.getImg()).into(ivThumbnail);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

}