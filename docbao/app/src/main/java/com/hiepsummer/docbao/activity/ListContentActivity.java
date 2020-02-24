package com.hiepsummer.docbao.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hiepsummer.docbao.R;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ListContentActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);
        setupMeoBottom();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_thegioi, R.id.nav_kinhdoanh,
                R.id.nav_phapluat, R.id.nav_thethao, R.id.nav_dulich)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Add HeaderView into Drawer
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_list_content, navigationView, false);
        navigationView.addHeaderView(headerView);
        TextView tv = headerView.findViewById(R.id.textViewIntro);
        tv.setSelected(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#00bfa5'>VietNamNews</font>"));
        builder.setMessage(Html.fromHtml("<font color='#00bfa5'>Phiên bản ứng dụng: 1.2.0-beta</font>"))
                .setPositiveButton(Html.fromHtml("<font color='#00bfa5'>OK</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();

    }

    private void setupMeoBottom() {
        MeowBottomNavigation meo = findViewById(R.id.bottomNavigation);

        meo.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        meo.add(new MeowBottomNavigation.Model(2, R.drawable.ic_account));
        meo.add(new MeowBottomNavigation.Model(3, R.drawable.ic_explore));
        meo.add(new MeowBottomNavigation.Model(4, R.drawable.ic_message));
        meo.add(new MeowBottomNavigation.Model(5, R.drawable.ic_notification));

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "" + item.getId(), Toast.LENGTH_LONG).show();
            }
        });
        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment select_fragment = null;

            }
        });
    }
}
