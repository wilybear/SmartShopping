package com.example.smartshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartshopping.adapter.ItemListAdapter;
import com.example.smartshopping.model.ItemModel;
import com.example.smartshopping.viewmodel.ItemListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemListAdapter.ItemClickListener {

    private List<ItemModel> itemModelList;
    private ItemListAdapter itemListAdapter;
    private ItemListViewModel itemListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        itemListAdapter = new ItemListAdapter(this,itemModelList,this);
        recyclerView.setAdapter(itemListAdapter);

        final TextView tvNoResult = findViewById(R.id.noResultView);

        itemListViewModel = ViewModelProviders.of(this).get(ItemListViewModel.class);
        itemListViewModel.getItemsListObserver().observe(this, new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> itemModels) {
                if(itemModels != null){
                    itemModelList = itemModels;
                    itemListAdapter.setItemList(itemModelList);
                    tvNoResult.setVisibility(View.GONE);
                }else{
                    tvNoResult.setVisibility(View.VISIBLE);
                }
            }
        });
        itemListViewModel.makeApiCall();
    }

    @Override
    public void onItemClick(ItemModel model) {
        Toast.makeText(this,"Clicked Title :"+model.getImageUrl(),Toast.LENGTH_LONG).show();
    }
}