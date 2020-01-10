package com.hiepsummer.docbao;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<DocBao> {
    private Context mContext;
    private List<DocBao> moviesList ;

    public Adapter(Context context, int resource, ArrayList<DocBao> items) {
        super(context, resource, items);
        mContext = context;
        moviesList = items;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.item, null);
        }
        DocBao docBao = getItem(position);
        if (docBao != null) {
            // Anh xa + Gan gia tri
            TextView txttitle = view.findViewById(R.id.textViewTitle);
            txttitle.setText(docBao.title);

            ImageView imageView = view.findViewById(R.id.imageViewThumb);
            Picasso.with(getContext()).load(docBao.img).into(imageView);

        }
        return view;
    }

}