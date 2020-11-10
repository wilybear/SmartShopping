package com.example.smartshopping.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartshopping.R;
import com.example.smartshopping.adapter.ItemListAdapter;
import com.example.smartshopping.model.ItemModel;
import com.example.smartshopping.viewmodel.ItemListViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ItemListFragment extends Fragment implements ItemListAdapter.ItemClickListener {

    private List<ItemModel> itemModelList;
    private ItemListAdapter itemListAdapter;
    private ItemListViewModel itemListViewModel;
    private RecyclerView recyclerView;
    private Button logoutBtt;
    private TextView loggedUserTextView;
    private TextView tvNoResult;
    private Button changer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemListViewModel = ViewModelProviders.of(this).get(ItemListViewModel.class);
        itemListViewModel.getItemsListObserver().observe(this, new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> itemModels) {
                if (itemModels != null) {
                    itemModelList = itemModels;
                    itemListAdapter.setItemList(itemModelList);
                    tvNoResult.setVisibility(View.GONE);
                } else {
                    tvNoResult.setVisibility(View.VISIBLE);
                }
            }
        });
        itemListViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null) {
                    loggedUserTextView.setText("Logged In User: " + firebaseUser.getEmail());
                }
            }
        });

        itemListViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if(loggedOut){
                    Navigation.findNavController(getView()).navigate(R.id.action_itemListFragment_to_signInFragment);
                }
            }
        });

        itemListViewModel.makeApiCall();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        itemListAdapter = new ItemListAdapter(getContext(), itemModelList, this);
        recyclerView.setAdapter(itemListAdapter);

        logoutBtt = view.findViewById(R.id.logoutBtt);
        loggedUserTextView = view.findViewById(R.id.userIdView);
        tvNoResult = view.findViewById(R.id.noResultView);
        changer = view.findViewById(R.id.layoutChanger);
        logoutBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListViewModel.logOut();
            }
        });

        changer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemListAdapter.isGridOption()){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    itemListAdapter.setGridOption(false);
                }else{
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                    itemListAdapter.setGridOption(true);
                }
                recyclerView.setAdapter(itemListAdapter);
            }
        });
        return view;
    }

    @Override
    public void onItemClick(ItemModel model) {
        Toast.makeText(getContext(), "Clicked Title :" + model.getImageUrl(), Toast.LENGTH_LONG).show();
        Bundle args = new Bundle();
        args.putString("title", model.getTitle());
        args.putString("url", model.getImageUrl());
        Navigation.findNavController(getView()).navigate(R.id.action_itemListFragment_to_itemFragement,args);
    }
}
