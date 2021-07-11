package com.example.lvtn_babershop.Interface;

import com.example.lvtn_babershop.Model.Banner;

import java.util.List;

public interface IBannerLoadListener {
    void onBannerLoadSuccess(List<Banner> banners);
    void onBannerLoadFailed(String message);
}
