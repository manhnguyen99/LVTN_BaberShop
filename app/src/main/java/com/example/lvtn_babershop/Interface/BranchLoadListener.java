package com.example.lvtn_babershop.Interface;

import com.example.lvtn_babershop.Model.Salon;

import java.util.List;

public interface BranchLoadListener {
    void onBranchLoadSuccess(List<Salon> cityNameList);
    void onBranchFailed(String message);
}
