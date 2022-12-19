package com.kyjsoft.tp09plant;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeFragment extends Fragment {




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homefragment, container, false);
    }

    ArrayList<HomeFragmentRecyclerItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    HomeFragmentRecyclerAdapter adapter;




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).bnv.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.recycler_home);
        adapter = new HomeFragmentRecyclerAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);
        ImageView iv;

        iv = view.findViewById(R.id.iv_add);
        Glide.with(getActivity()).load(R.drawable.home_add).into(iv);

        view.findViewById(R.id.iv_add).setOnClickListener(view1 -> {
            ((MainActivity)getActivity()).bnv.setSelectedItemId(R.id.search);
        });

        // TODO DB 불러오기
        // TODO 카메라 접근






    }


}
