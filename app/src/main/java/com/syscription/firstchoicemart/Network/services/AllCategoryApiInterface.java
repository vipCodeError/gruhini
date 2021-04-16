package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AllCategoryApiInterface {
    @GET("categories")
    Call<CategoryResponse> getAllCategories();
}
