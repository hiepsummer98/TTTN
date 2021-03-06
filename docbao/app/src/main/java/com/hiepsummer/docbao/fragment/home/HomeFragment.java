package com.hiepsummer.docbao.fragment.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hiepsummer.docbao.R;
import com.hiepsummer.docbao.adapter.AdapterRc;
import com.hiepsummer.docbao.data.model.New;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView rc_Data;
    AdapterRc adapter;
    ArrayList<New> mangDocBao;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rc_Data = root.findViewById(R.id.rc_Home);
        rc_Data.setLayoutManager(new LinearLayoutManager(getActivity()));
        mangDocBao = new ArrayList<>();
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
                    news.setPubDate(element.select("pubDate").text().trim()
                            .replace("+0700", "")
                            .replace("Sun", "")
                            .replace("Sat", "")
                            .replace("Fri", "")
                            .replace("Thu", "")
                            .replace("Wed", "")
                            .replace("Tue", "")
                            .replace("Mon", "")
                            .replace(", ", "")

                            .replace(" Jan ", "/01/")
                            .replace(" Feb ", "/02/")
                            .replace(" Mar ", "/03/")
                            .replace(" Apr ", "/04/")
                            .replace(" May ", "/05/")
                            .replace(" June ", "/06/")
                            .replace(" July ", "/07/")
                            .replace(" Aug ", "/08/")
                            .replace(" Sept ", "/09/")
                            .replace(" Oct ", "/10/")
                            .replace(" Nov ", "/11/")
                            .replace(" Dec ", "/12/")
                    );
                    if (news.img.isEmpty() || news.title.isEmpty() || news.link.isEmpty() || news.pubDate.isEmpty()) {
                    } else {
                        mangDocBao.add(news);
                    }
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
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                    intent.putExtra("link", mangDocBao.get(position).link);
//                    startActivity(intent);
//                }
//            });
            super.onPostExecute(s);
            rc_Data.setAdapter(new AdapterRc(getActivity(), mangDocBao));
        }

    }

    void runOnUIThread() {
        // truyền đường dẫn của RSS thông qua phương thức runOnUiThread
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
            }
        });

    }

}