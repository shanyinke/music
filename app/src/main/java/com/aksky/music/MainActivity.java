package com.aksky.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
  //  private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                   // mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_star:
                   // mTextMessage.setText(R.string.title_dashboard);
                    sendRequestWithokHttp();
                    replaceFragment(new StarFragment());
                    return true;
                case R.id.navigation_view_list:
                   // mTextMessage.setText(R.string.title_notifications);
                    replaceFragment(new ListFragment());
                    return true;

                case R.id.navigation_exlore:
                    // mTextMessage.setText(R.string.title_notifications);
                    replaceFragment(new ExploreFragment());
                    return true;
                case R.id.navigation_person:
                    // mTextMessage.setText(R.string.title_notifications);
                    replaceFragment(new PersonFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(new HomeFragment());
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
               drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"You clicked setting",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this,ButtomNavigationActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_layout,fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    private void  sendRequestWithokHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request= new Request.Builder()
                            //指定访问的服务器地址
                            .url("http://www.fanzhou.com/list.php")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseDada=  response.body().string();
                    parseJSONwithGSON3(responseDada);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONwithGSON3(String jsonData){
        Gson gson = new Gson();
        List<ArticleList> appList = gson.fromJson(jsonData,new TypeToken<List<ArticleList>>(){}.getType());
        for(ArticleList articleList:appList){
            Log.d("MainActivity","id is "+ articleList.getId());
            Log.d("MainActivity","name is "+ articleList.getTitle());
            Log.d("MainActivity","birthday is "+ articleList.getTypeid());
            Log.d("MainActivity","creditrating is "+articleList.getWriter());
            Log.d("MainActivity","creditrating is "+articleList.getTitle());
        }



    }
}
