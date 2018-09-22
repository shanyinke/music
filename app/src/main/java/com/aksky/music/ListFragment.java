package com.aksky.music;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListFragment extends Fragment {
    private String[] data={"Apple","Banana","Orange","Watermelon",
            "Pear","Grape","Pineapple","Strawberry","Cherry","Mango",
            "Apple","Banana","Orange","Watermelon",
            "Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};//初始化数组

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,data); //定义适配器
        ListView listView=(ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
       // View view= inflater.inflate(R.layout.home_fragment , container, false);
//        listView = (ListView)view.findViewById(R.id.list_view);
//        List<Map<String, Object>> list=getData();
//        listView.setAdapter(new ListViewAdapter(getActivity(), list));
        return view;



    }


}
