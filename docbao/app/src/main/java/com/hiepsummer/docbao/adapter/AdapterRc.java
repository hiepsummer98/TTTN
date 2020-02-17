package com.hiepsummer.docbao.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hiepsummer.docbao.R;
import com.hiepsummer.docbao.activity.DetailsActivity;
import com.hiepsummer.docbao.data.model.New;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRc extends RecyclerView.Adapter<AdapterRc.ViewHolder> {

    private Context context;
    private ArrayList<New> newList;

    public AdapterRc(Context context, ArrayList newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(newList.get(position));

        final New news = newList.get(position);

        holder.title.setText(Html.fromHtml(news.getTitle()));
        holder.pubDate.setText(news.getPubDate());
        if (news.getImg().isEmpty()) {

            holder.img.setImageResource(R.drawable.ic_launcher_background);
        } else {
            Picasso.get().load(news.getImg()).into(holder.img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news.link != null) {
                    try {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra("link", news.link);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return newList == null ? 0 : newList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView pubDate;
        public ImageView img;

        public ViewHolder(final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);
            pubDate = itemView.findViewById(R.id.textViewPublicDate);
            img = itemView.findViewById(R.id.imageViewThumb);

        }
    }

}