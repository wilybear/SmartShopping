package com.example.smartshopping.views;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import com.example.smartshopping.AreaPrediction;
import com.example.smartshopping.R;
import com.example.smartshopping.adapter.ItemListAdapter;
import com.example.smartshopping.model.AreaModel;
import com.example.smartshopping.model.Beacon;
import com.example.smartshopping.model.ItemModel;
import com.example.smartshopping.viewmodel.ItemListViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.neovisionaries.bluetooth.ble.advertising.ADPayloadParser;
import com.neovisionaries.bluetooth.ble.advertising.ADStructure;
import com.neovisionaries.bluetooth.ble.advertising.IBeacon;
import com.pedro.library.AutoPermissions;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemListFragment extends Fragment implements ItemListAdapter.ItemClickListener {

    private List<ItemModel> a;
    private List<ItemModel> b;
    private List<ItemModel> c;
    private List<ItemModel> d;


    private List<ItemModel> itemModelList;
    private ItemListAdapter itemListAdapter;
    private ItemListViewModel itemListViewModel;
    private RecyclerView recyclerView;
    private Button logoutBtt;
    private TextView loggedUserTextView;
    private TextView tvNoResult;
    private ImageButton changer;
    private TextView tvArea;
    private ImageButton refreshBtt;
    private RippleBackground rippleBackground;
    private ImageView rippleImage;
    //bluetooth
    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    String uuidFilter = "74278bda-b644-4520-8f0c-720eaf059935";
    Boolean btScanning = false;
    public Map<String, String> uuids = new HashMap<String, String>();
    private Handler mHandler = new Handler();
    private static final long SCAN_PERIOD = 5000;
    private static final long LIMIT_RSSI = -85;
    private final static int REQUEST_ENABLE_BT = 1;
    private Map<Pair<Integer, Integer>, Beacon> beacons = new HashMap<>();
    BleThread bleThread;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        a = new ArrayList<>();
        b = new ArrayList<>();
        c = new ArrayList<>();
        d = new ArrayList<>();

        a.add(new ItemModel("과자1","2222",R.drawable.a_1));
        a.add(new ItemModel("과자2","2222",R.drawable.a_2));
        a.add(new ItemModel("과자3","2222",R.drawable.a_3));
        a.add(new ItemModel("과자4","2222",R.drawable.a_4));

        b.add(new ItemModel("의류1","2222",R.drawable.b_1));
        b.add(new ItemModel("의류2","2222",R.drawable.b_2));
        b.add(new ItemModel("의류3","2222",R.drawable.b_3));
        b.add(new ItemModel("의류4","2222",R.drawable.b_4));

        c.add(new ItemModel("장난감1","2222",R.drawable.c_1));
        c.add(new ItemModel("장난감2","2222",R.drawable.c_2));
        c.add(new ItemModel("장난감3","2222",R.drawable.c_3));
        c.add(new ItemModel("장난감4","2222",R.drawable.c_4));

        d.add(new ItemModel("여행용품1","2222",R.drawable.d_1));
        d.add(new ItemModel("여행용품2","2222",R.drawable.d_2));
        d.add(new ItemModel("여행용품3","2222",R.drawable.d_3));
        d.add(new ItemModel("여행용품4","2222",R.drawable.d_4));



        itemListViewModel = ViewModelProviders.of(this).get(ItemListViewModel.class);

        AutoPermissions.Companion.loadAllPermissions(getActivity(), 101); // AutoPermissions

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
                if (firebaseUser != null) {
//                    loggedUserTextView.setText("Logged In User: " + firebaseUser.getEmail());
                }
            }
        });

        itemListViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
                    Navigation.findNavController(getView()).navigate(R.id.action_itemListFragment_to_signInFragment);
                }
            }
        });

        itemListViewModel.getAreaModelMutableLiveData().observe(this, new Observer<AreaModel>() {
            @Override
            public void onChanged(AreaModel areaModel) {
                if (areaModel != null) {
                    if (areaModel.getId() == 0) {
                        itemListAdapter.clearData();
                        tvArea.setText("NO BEACONS");
                        tvNoResult.setVisibility(View.VISIBLE);
                    } else {
                        //itemListViewModel.makeApiCall();
                        switch (areaModel.getArea()){
                            case 'A':
                                itemListViewModel.setItemList(a);
                                break;
                            case 'B':
                                itemListViewModel.setItemList(b);
                                break;
                            case 'C':
                                itemListViewModel.setItemList(c);
                                break;
                            case 'D':
                                itemListViewModel.setItemList(d);
                                break;
                        }
                        tvNoResult.setVisibility(View.GONE);
                        //TODO: 구역 및 유저 정보 보내고 RecyclerView에 데이터 뿌리기
                        tvArea.setText(areaModel.getArea() + " Area");
                    }
                    itemListViewModel.getAutoThread().postValue(true);
                } else {
                    tvArea.setText("Searching...");
                }
            }
        });

        itemListViewModel.getAutoThread().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    rippleBackground.stopRippleAnimation();
                    rippleBackground.setVisibility(View.GONE);
                    rippleImage.setVisibility(View.GONE);
                } else {
                    rippleBackground.setVisibility(View.VISIBLE);
                    rippleImage.setVisibility(View.VISIBLE);
                    rippleBackground.startRippleAnimation();
                }
            }
        });


        btManager = (BluetoothManager) getContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();
        bleThread = new BleThread();

    }

    @Override
    public void onResume() {
        super.onResume();
        //check if bluetooth is available
        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        btManager = (BluetoothManager) getContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();
        bleThread.running = true;

        //if thread is alive, start() may throw threadStateException
        if (!bleThread.isAlive()) {
            bleThread = new BleThread();
            bleThread.running = true;
            bleThread.start();
        }

        if (itemListAdapter.getItemCount() == 0) {
            itemListViewModel.getAutoThread().postValue(false);
        }

    }

    class BleThread extends Thread {
        boolean running = true;

        @Override
        public void run() {
            while (running && !isInterrupted()) {
                startScanning();
                try {
                    Thread.sleep(5000+5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bleThread.running = false;
        bleThread.interrupt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        itemListAdapter = new ItemListAdapter(getContext(), itemModelList, this);
        recyclerView.setAdapter(itemListAdapter);
        refreshBtt = view.findViewById(R.id.refreshBtt);

        logoutBtt = view.findViewById(R.id.logoutBtt);
//        loggedUserTextView = view.findViewById(R.id.userIdView);
        tvNoResult = view.findViewById(R.id.noResultView);
        tvArea = view.findViewById(R.id.areaTextView);
        changer = view.findViewById(R.id.layoutChanger);
        rippleBackground = (RippleBackground) view.findViewById(R.id.content);
        rippleImage = view.findViewById(R.id.centerImage);

        if (itemListViewModel.getAreaModelMutableLiveData().getValue() != null) {
            tvArea.setText(itemListViewModel.getAreaModelMutableLiveData().getValue().getArea() + " Area");
        }
        logoutBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListViewModel.logOut();
            }
        });

        changer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemListAdapter.isGridOption()) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    itemListAdapter.setGridOption(false);
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    itemListAdapter.setGridOption(true);
                }
                recyclerView.setAdapter(itemListAdapter);
            }
        });

        refreshBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btScanning) {
                    Toast.makeText(getContext(), "Scanning...", Toast.LENGTH_SHORT).show();
                } else {
                    tvNoResult.setVisibility(View.GONE);
                    itemListAdapter.clearData();
                    itemListViewModel.getAutoThread().postValue(false);
                    if (bleThread.isAlive()) {
                        bleThread.running = false;
                        bleThread.interrupt();
                    }
                    bleThread = new BleThread();
                    bleThread.running = true;
                    bleThread.start();

                }
            }
        });


        return view;
    }


    @Override
    public void onItemClick(ItemModel model) {
        Toast.makeText(getContext(), "Clicked Title :" + model.getImageUrl(), Toast.LENGTH_LONG).show();
        Bundle args = new Bundle();
        args.putString("title", model.getTitle());
        args.putInt("url", model.getD());
        Navigation.findNavController(getView()).navigate(R.id.action_itemListFragment_to_itemFragement, args);
    }

    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            List<ADStructure> structures =
                    ADPayloadParser.getInstance().parse(result.getScanRecord().getBytes());
            for (ADStructure structure : structures) {
                if (structure instanceof IBeacon) {
                    IBeacon iBeacon = (IBeacon) structure;
//                    if (result.getRssi() < LIMIT_RSSI) {
//                        continue;
//                    }
                    if (!iBeacon.getUUID().toString().equals(uuidFilter)) {
                        continue;
                    }
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(result.getRssi());
                    Beacon beacon = new Beacon(iBeacon.getUUID(), result.getRssi(), iBeacon.getMajor(), iBeacon.getMinor(),
                            result.getDevice().getName(),temp);
                    Pair<Integer, Integer> key = Pair.create(iBeacon.getMajor(), iBeacon.getMinor());
                    if (beacons.containsKey(key)) {
                        Beacon prevBeacon = beacons.get(key);
                        prevBeacon.addRssi(result.getRssi());
                        prevBeacon.setRssi(beacon.getRssi());
                        beacons.put(key, prevBeacon);
                    } else {
                        beacons.put(key, beacon);
                    }
                    //getScanRecord.getServiceUUids()
                }
            }
        }
    };


    public void startScanning() {
        btScanning = true;
        beacons.clear();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(leScanCallback);
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScanning();
            }
        }, SCAN_PERIOD);
    }

    public void stopScanning() {
        btScanning = false;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
                //TODO:AREA 계산하는 함수

                AreaPrediction areaPrediction = new AreaPrediction(beacons);

              char result = areaPrediction.predictArea();
  //              char result = 'A';
                if (result != ' ') {
                    itemListViewModel.getAreaModelMutableLiveData().postValue(new AreaModel(1, result));
                } else {
                    //Error not enough available beacons
                    itemListViewModel.getAreaModelMutableLiveData().postValue(new AreaModel(0, ' '));
                }

            }
        });
    }
}
