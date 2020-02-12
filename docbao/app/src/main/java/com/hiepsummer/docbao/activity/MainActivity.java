package com.hiepsummer.docbao.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.hiepsummer.docbao.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkConnect(MainActivity.this)) builder(MainActivity.this).show();
        else {
            setContentView(R.layout.activity_main);
            Thread th = new Thread(wait);
            th.start();
        }
    }

    Runnable wait = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                Intent intent = new Intent(MainActivity.this, OptionWebsiteActivity.class);
                startActivity(intent);

                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public boolean checkConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null & mobile.isConnectedOrConnecting()) || (wifi != null & wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder builder(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Không có kết nối");
        builder.setMessage("Vui lòng kiểm tra kết nối Internet để tiếp tục!");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;

    }

}

