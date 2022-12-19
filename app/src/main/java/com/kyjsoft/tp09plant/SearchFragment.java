package com.kyjsoft.tp09plant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    String apikey = "4wlFPuoi69Pc78kZpfF7GpieaLhqRkeSrKZs9jk5ZqbKSSh4vfl2VXk36YbHSOSipfsuVFbBZk9wVLg%2BubgvHw%3D%3D";
    EditText etRecyclerSearch;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    ArrayList<SearchFragmentRecyclerItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    SearchFragmentRecyclerAdapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_search);
        adapter = new SearchFragmentRecyclerAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);

        ((MainActivity)getActivity()).bnv.setVisibility(View.VISIBLE);

        etRecyclerSearch = getActivity().findViewById(R.id.recycler_search_et);


        getActivity().findViewById(R.id.recycler_search_btn).setOnClickListener( v->{

            loadData(etRecyclerSearch.getText().toString());
        });


    }

    void loadData(String input){

        Thread thread = new Thread(){
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        items.clear();
                        adapter.notifyDataSetChanged();
                    }
                });

                try {

                    String address = "http://openapi.nature.go.kr/openapi/service/rest/PlantService/plntIlstrSearch"
                            + "?serviceKey=" + apikey
                            + "&st=1"
                            + "&sw=" + input
                            + "&numOfRows=100"
                            + "&pageNo=1";

                    URL url = new URL(address);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(isr);

                    int eventType = xpp.getEventType();

                    SearchFragmentRecyclerItem item = null;



                    while(eventType != XmlPullParser.END_DOCUMENT){

                        switch (eventType){
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                String tagName = xpp.getName();
                                if(tagName.equals("item")){
                                    item = new SearchFragmentRecyclerItem();
                                }
                                else if(tagName.equals("plantGnrlNm")){
                                    xpp.next();
                                    item.title = xpp.getText();
                                }
                                else if(tagName.equals("imgUrl")){
                                    xpp.next();
                                    item.imgUrl = xpp.getText();
                                }
                                break;
                            case XmlPullParser.TEXT:
                                break;
                            case XmlPullParser.END_TAG:
                                if(xpp.getName().equals("item")){
                                    items.add(item);
                                }
                                break;
                        }
                        eventType = xpp.next();

                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });




                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();



    }
}
