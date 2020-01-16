package com.hiepsummer.docbao.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
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

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

    //Asyntask có 3 đối số: đường dẫn(String), quá trình thực hiện(Integer) và kết quả trả về(String)
    class ReadData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            //đọc từ phần tử đầu tiên của mảng
             return readContentfromURL(strings[0]);
        }

        // trả về kết quả ở onPostExcute
        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            String title = "";
            String link = "";
            String pubDate = "";
            String img = "";

            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");

            for (int i = 0; i < nodeList.getLength(); i++) {
                String cdata = nodeListdescription.item(i + 1).getTextContent();

                //đọc ảnh từ thẻ img trong thẻ <![CDATA]/>
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                while (matcher.find()) {
                    //tìm được trùng khớp, nhóm vào cùng 1 nhóm
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


        }
    }

    private String readContentfromURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // tạo đối tượng url
            URL url = new URL(theUrl);
            // tạo đường dẫn kết nối đối tượng URL
            URLConnection urlConnection = url.openConnection();
            // ghi nội dung vào BufferedReader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // độc nội dung từ url thông qua BufferedReader
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
        // truyền đường dẫn của RSS thông qua phương thức runOnUiThread
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
            }
        });

    }

}