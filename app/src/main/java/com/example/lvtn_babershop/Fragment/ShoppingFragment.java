package com.example.lvtn_babershop.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lvtn_babershop.Comon.SpacesItemDecoration;
import com.example.lvtn_babershop.Interface.IShoppingDataLoadListener;
import com.example.lvtn_babershop.Interface.MyShoppingItemAdapter;
import com.example.lvtn_babershop.Model.ShoppingItem;
import com.example.lvtn_babershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment implements IShoppingDataLoadListener {

    ChipGroup chipGroup;
    Chip chipWax, chipSpray, chipHaircare, chipHairdryer, chipCob;
    DatabaseReference shoppingItemRef;
    RecyclerView recyclerItem;

    IShoppingDataLoadListener iShoppingDataLoad;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setSelectChip(Chip chip)
    {
        for (int i = 0; i < chipGroup.getChildCount(); i++)
        {
            Chip chipItem = (Chip) chipGroup.getChildAt(i);
            if (chipItem.getId() != chip.getId())
            {
                chipItem.setChipBackgroundColorResource(android.R.color.darker_gray);
                chipItem.setTextColor(getResources().getColor(android.R.color.white));
            }
            else // neu chip duoc chon
            {
                chipItem.setChipBackgroundColorResource(android.R.color.holo_orange_dark);
                chipItem.setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }


    private void loadingShoppingItem(String itemMenu) {
        shoppingItemRef = FirebaseDatabase.getInstance().getReference("Shopping")
                .child(itemMenu)
                .child("Items");
        shoppingItemRef.get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        iShoppingDataLoad.onShoppingDataLoadFailed(e.getMessage());
                    }
                }).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    List<ShoppingItem> shoppingItems = new ArrayList<>();

                    for (DataSnapshot itemSnapshot:task.getResult().getChildren())
                    {
                        ShoppingItem shoppingItem = itemSnapshot.getValue(ShoppingItem.class);
                        shoppingItems.add(shoppingItem);
                    }
                    iShoppingDataLoad.onShoppingDataLoadSuccess(shoppingItems);

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_shopping, container, false);

        chipWax = itemView.findViewById(R.id.chip_wax);
        chipCob = itemView.findViewById(R.id.chip_comb);
        chipSpray = itemView.findViewById(R.id.chip_spray);
        chipHaircare = itemView.findViewById(R.id.chip_haircare);
        chipHairdryer = itemView.findViewById(R.id.chip_Hairdryer);
        recyclerItem = itemView.findViewById(R.id.recyclerItems);
        chipGroup = itemView.findViewById(R.id.chip_group);

        iShoppingDataLoad = this;




        //Default load chip
        loadingShoppingItem("Wax");
        waxChipClick();
        sprayChipClick();
        hairDryerChipClick();
        hairCareChipClick();


        innitView();
        return itemView;
    }

    void waxChipClick(){
        chipWax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectChip(chipWax);
                loadingShoppingItem("Wax");
            }
        });
    }
    void sprayChipClick() {
        chipSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectChip(chipSpray);
                loadingShoppingItem("Spray");
            }
        });
    }
    void hairDryerChipClick() {
        chipHairdryer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectChip(chipHairdryer);
                loadingShoppingItem("Hairdryer");
            }
        });
    }
    void hairCareChipClick() {
        chipHaircare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectChip(chipHaircare);
                loadingShoppingItem("Haircare");
            }
        });
    }


    private void innitView() {
        recyclerItem.setHasFixedSize(true);
        recyclerItem.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerItem.addItemDecoration(new SpacesItemDecoration(8));
    }

    @Override
    public void onShoppingDataLoadSuccess(List<ShoppingItem> shoppingItemList) {
        MyShoppingItemAdapter adapter = new MyShoppingItemAdapter(getContext(),shoppingItemList);
        recyclerItem.setAdapter(adapter);

    }

    @Override
    public void onShoppingDataLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}