package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopCategoryApiInterface {
    @GET("categories/featured?page=1")
    Call<CategoryResponse> getTopCategories();

    @GET("categories/featured?page=2")
    Call<CategoryResponse> getTopSecondPageCategories();
}
