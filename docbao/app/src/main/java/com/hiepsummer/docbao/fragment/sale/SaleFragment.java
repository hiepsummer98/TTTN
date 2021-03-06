package com.hiepsummer.docbao.fragment.sale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hiepsummer.docbao.adapter.Adapter;
import com.hiepsummer.docbao.activity.DetailsActivity;
import com.hiepsummer.docbao.data.model.New;
import com.hiepsummer.docbao.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class SaleFragment extends Fragment {

    private SaleViewModel saleViewModel;
    ListView listView;
    Adapter adapter;
    ArrayList<New> mangDocBao;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        saleViewModel =
                ViewModelProviders.of(this).get(SaleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sale, container, false);

        listView = root.findViewById(R.id.listViewSale);
        mangDocBao = new ArrayList<New>();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runOnUIThread();
    }

    class ReadData extends AsyncTask<String, Void, ArrayList<New>> {

        @Override
        protected ArrayList<New> doInBackground(String... strings) {
            mangDocBao = new ArrayList<>();
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements elements = document.select("item");
                New news = null;

                for (Element element : elements) {
                    news = new New();
                    news.setTitle(element.select("title").text());
                    news.setImg(Jsoup.parse(element.select("description").text()).select("img").attr("src"));
                    news.setLink(element.select("link").text());
                    news.setPubDate(element.select("pubDate").text().replace("+0700", ""));

                    mangDocBao.add(news);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mangDocBao;
        }

        // trả về kết quả ở onPostExcute
        @Override
        protected void onPostExecute(ArrayList<New> s) {
            ////action ClickListener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("link", mangDocBao.get(position).link);
                    startActivity(intent);
                }
            });
            super.onPostExecute(s);

            adapter = new Adapter(getActivity(), R.layout.item, mangDocBao);
            listView.setAdapter(adapter);
        }

    }
    void runOnUIThread() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/kinh-doanh.rss");
            }
        });

    }
}