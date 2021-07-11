package com.example.lvtn_babershop.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Adapter.MyBaberAdapter;
import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Comon.SpacesItemDecoration;
import com.example.lvtn_babershop.Model.Baber;
import com.example.lvtn_babershop.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    RecyclerView recycler_baber;
    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;

    private BroadcastReceiver baberDoneRecuiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Baber>  baberArrayList =  intent.getParcelableArrayListExtra(Common.KEY_BABER_LOAD_DONE);
            //
            MyBaberAdapter adapter =  new MyBaberAdapter(getContext(), baberArrayList);
            recycler_baber.setAdapter(adapter);
        }
    };
    static BookingStep2Fragment instance;
    public static BookingStep2Fragment getInstance()
    {
        if (instance == null)
            instance = new BookingStep2Fragment();
        return instance;
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(baberDoneRecuiver, new IntentFilter(Common.KEY_BABER_LOAD_DONE));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(baberDoneRecuiver);
        super.onDestroy();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      View itemView = inflater.inflate(R.layout.fragment_booking_step_two, container, false);
        unbinder = ButterKnife.bind(this, itemView);
        recycler_baber = itemView.findViewById(R.id.recycler_baber);

        initView();
        return itemView;
    }
    private void initView() {
        recycler_baber.setHasFixedSize(true);
        recycler_baber.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recycler_baber.addItemDecoration(new SpacesItemDecoration(4));
    }
}
