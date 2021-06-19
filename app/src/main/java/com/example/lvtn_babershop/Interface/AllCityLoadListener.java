package com.example.lvtn_babershop.Interface;

import java.util.List;

public interface AllCityLoadListener {
    void onAllCityLoadSuccess(List<String> cityNameList);
    void onAllCityFailed(String message);
}
