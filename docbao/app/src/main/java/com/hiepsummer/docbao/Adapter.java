package com.hiepsummer.docbao;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<New> {
    private Context mContext;
    private ArrayList<New> moviesList;

    public Adapter(Context context, int resource, ArrayList<New> items) {
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

        try {
            New baibao = getItem(position);
            if (baibao != null) {
                // Anh xa + Gan gia tri
                TextView txttitle = view.findViewById(R.id.textViewTitle);
                TextView txtPubdate = view.findViewById(R.id.textViewPublicDate);
                txttitle.setText(baibao.title);
                txtPubdate.setText(baibao.pubDate);

//                ImageView imageView = view.findViewById(R.id.imageViewThumb);
//                if (baibao.img.isEmpty()) {
//                    imageView.setImageResource(R.drawable.logo);
//                } else {
//                    Picasso.with(getContext()).load(baibao.img).into(imageView);
//                }

            }
        } catch (Exception e) {
            Log.e("error ", "error is: " + e);
        }
        return view;
    }


}