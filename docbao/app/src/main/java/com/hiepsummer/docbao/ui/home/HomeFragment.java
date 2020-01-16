package com.hiepsummer.docbao.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hiepsummer.docbao.Adapter;
import com.hiepsummer.docbao.DetailsActivity;
import com.hiepsummer.docbao.New;
import com.hiepsummer.docbao.R;
import com.hiepsummer.docbao.XMLDOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    Adapter adapter;
    ArrayList<New> mangDocBao;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listView = root.findViewById(R.id.listViewHome);
        mangDocBao = new ArrayList<New>();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runOnUIThread();
    }

    class ReadData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return readContentfromURL(strings[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String title = "";
            String link = "";
            String pubDate = "";
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String img = "";

            for (int i = 0; i < nodeList.getLength(); i++) {
                String cdata = nodeListdescription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                while (matcher.find()) {
                    img = matcher.group(1);
                    Log.d("hinhanh", img + " ..." + i);
                }
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                pubDate = parser.getValue(element, "pubDate");

                mangDocBao.add(new New(title, link, img, pubDate));
            }
            adapter = new Adapter(getActivity(), android.R.layout.simple_list_item_1, mangDocBao);
            listView.setAdapter(adapter);

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
            listView.setOnScrollChangeListener(new AbsListView.OnScrollChangeListener() {

                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                }
            });

        }
    }

    private String readContentfromURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    void runOnUIThread() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
            }
        });

    }

}