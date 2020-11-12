package com.example.smartshopping.views;

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

import com.example.smartshopping.R;
import com.example.smartshopping.adapter.ItemListAdapter;
import com.example.smartshopping.model.ItemModel;
import com.example.smartshopping.viewmodel.ItemListViewModel;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoPermissions.Companion.loadAllPermissions(this,101);
    }


    @Override
    public void onDenied(int i, @NotNull String[] strings) {
        Toast.makeText(this,"앱을 실행하기 위해서는 다음 권한이 필요합니다.",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onGranted(int i, @NotNull String[] strings) {

    }
}