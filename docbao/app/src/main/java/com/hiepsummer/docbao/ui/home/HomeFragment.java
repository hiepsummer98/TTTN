package com.hiepsummer.docbao.ui.home;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hiepsummer.docbao.Adapter;
import com.hiepsummer.docbao.DocBao;
import com.hiepsummer.docbao.ListContentActivity;
import com.hiepsummer.docbao.MainActivity;
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
    ArrayList<DocBao> mangDocBao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listView = root.findViewById(R.id.listViewHome);
        mangDocBao = new ArrayList<DocBao>();
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
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String title = "";
            String link = "";
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String img = "";

            for (int i = 0; i < nodeList.getLength(); i++) {
                String cdata = nodeListdescription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if (matcher.find()) {
                    img = matcher.group(1);
                    Log.d("hinhanh", img + " ..." + i);
                }
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                mangDocBao.add(new DocBao(title, link, img));
            }
            adapter = new Adapter(getActivity(), android.R.layout.simple_list_item_1, mangDocBao);
            listView.setAdapter(adapter);

            super.onPostExecute(s);

        }
    }

    private String docNoiDung_Tu_URL(String theUrl) {
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