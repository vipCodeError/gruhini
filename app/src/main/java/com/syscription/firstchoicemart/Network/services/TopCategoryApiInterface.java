package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopCategoryApiInterface {
    @GET("categories/featured")
    Call<CategoryResponse> getTopCategories();
}
