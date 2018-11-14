package com.aksky.music;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;


public class ListFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private String[] data={"Apple","Banana","Orange","Watermelon",
            "Pear","Grape","Pineapple","Strawberry","Cherry","Mango",
            "Apple","Banana","Orange","Watermelon",
            "Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};//初始化数组
    private List<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(    getActivity(),android.R.layout.simple_list_item_1,dataList); //定义适配器
        initArticle();
        adapter =  new ArrayAdapter<>( getActivity(),android.R.layout.simple_list_item_1,dataList);
        
        listView=(ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //ArticleList articlelist = articlelist.get(position);
                Toast.makeText(getContext(), dataList.get(position),Toast.LENGTH_SHORT).show();
            }
        });
       // View view= inflater.inflate(R.layout.home_fragment , container, false);
//        listView = (ListView)view.findViewById(R.id.list_view);
//        List<Map<String, Object>> list=getData();
//        listView.setAdapter(new ListViewAdapter(getActivity(), list));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshArticle();
                //sendRequestWithokHttp();
            }
        });
        return view;
    }

//    private void initFruits(){
//        fruitList.clear();
//        for(int i=0;i<50;i++){
//            Random random= new Random();
//            int index = random.nextInt(fruits.length);
//            fruitList.add(fruits[index]);
//        }
//    }

    private void  initArticle(){
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
        dataList.clear();
        for(ArticleList articleList:appList){
            Log.d("Fragment","id is "+ articleList.getId());
            Log.d("Fragment","TITLE is "+ articleList.getTitle());
            Log.d("Fragment","TYPEID is "+ articleList.getTypeid());
            Log.d("Fragment","Writer is "+articleList.getWriter());
            Log.d("Fragment","source is "+articleList.getSource());
            dataList.add(articleList.getTitle());
        }



    }


    private void refreshArticle(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (getActivity() == null)
                    return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                                       initArticle();
                        adapter.notifyDataSetChanged();
                       swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


}
