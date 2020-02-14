package com.hiepsummer.docbao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hiepsummer.docbao.R;

public class OptionWebsiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_website);

        animation();
        onClick();

    }

    private void animation() {
//        findViewById(R.id.tv_VietNamnet).setSelected(true);
//        findViewById(R.id.tv_DanViet).setSelected(true);
//        findViewById(R.id.tv_DanTri).setSelected(true);
//        findViewById(R.id.tv_VNEX).setSelected(true);
//        findViewById(R.id.tv_24H).setSelected(true);
    }

    private void onClick() {
        findViewById(R.id.vnexpress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionWebsiteActivity.this, ListContentActivity.class);
                startActivity(intent);
            }
        });
    }
}
