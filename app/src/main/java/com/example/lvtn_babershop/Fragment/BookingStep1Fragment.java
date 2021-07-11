package com.example.lvtn_babershop.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Adapter.MySalonAdapter;
import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Comon.SpacesItemDecoration;
import com.example.lvtn_babershop.Interface.AllCityLoadListener;
import com.example.lvtn_babershop.Interface.BranchLoadListener;
import com.example.lvtn_babershop.Interface.ITimeSlotLoadListener;
import com.example.lvtn_babershop.Model.Salon;
import com.example.lvtn_babershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class BookingStep1Fragment  extends Fragment implements AllCityLoadListener, BranchLoadListener {
    DatabaseReference allCityRef;
    DatabaseReference branchRef;

    AllCityLoadListener allCityLoadListener;
    BranchLoadListener branchLoadListener;
    MaterialSpinner spinner;
    RecyclerView recyclerView;
    AlertDialog alertDialog;

    ITimeSlotLoadListener iTimeSlotLoadListener;

    static BookingStep1Fragment instance;
    public static BookingStep1Fragment getInstance()
    {
        if (instance == null)
            instance = new BookingStep1Fragment();
        return instance;
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allCityRef  = FirebaseDatabase.getInstance().getReference("Salon");

        allCityLoadListener = this;
        branchLoadListener = this;

        alertDialog = new SpotsDialog.Builder().setContext(getActivity()).setCancelable(false).build();

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        View itemView =  inflater.inflate(R.layout.fragment_booking_step_one, container, false);
        spinner = itemView.findViewById(R.id.spiner);
        recyclerView = itemView.findViewById(R.id.recycler_salon);

        initView();
        LoadAllCity();
        return  itemView;
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(4));
    }
    private void LoadAllCity() {
        allCityRef.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<String> list = new ArrayList<>();
                            list.add("Please choose city");
                            for (DataSnapshot dataSnapshot:task.getResult().getChildren()){
                                list.add(dataSnapshot.getKey());
                                allCityLoadListener.onAllCityLoadSuccess(list);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                allCityLoadListener.onAllCityLoadSuccess(Collections.singletonList(e.getMessage()));
            }
        });
    }

    @Override
    public void onAllCityLoadSuccess(List<String> cityNameList) {
        spinner.setItems(cityNameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position > 0){
                    loadBranchOfCity(item.toString());
                }
                else
                    recyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void loadBranchOfCity(String cityName) {
        alertDialog.show();
        Common.city = cityName;
        branchRef = FirebaseDatabase.getInstance()
                .getReference("Salon")
                .child(cityName)
                .child("Branch");
        branchRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                List<Salon> list = new ArrayList<>();
                if(task.isSuccessful())
                {
                    for (DataSnapshot dataSnapshot:task.getResult().getChildren()){
                        {
                            Salon salon =  dataSnapshot.getValue(Salon.class);
                            salon.setSalonID(dataSnapshot.getKey());
                            list.add(salon);
                        }
                        branchLoadListener.onBranchLoadSuccess(list);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                branchLoadListener.onBranchFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllCityFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBranchLoadSuccess(List<Salon> salonList) {
        MySalonAdapter adapter = new MySalonAdapter(getActivity(),salonList);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        alertDialog.dismiss();
    }

    @Override
    public void onBranchFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        alertDialog.dismiss();
    }
}
