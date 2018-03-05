package com.mudra.utils;

import com.mudra.model.DashboardResponse;
import com.mudra.model.ImageUploadResponse;
import com.mudra.model.LoginRequest;
import com.mudra.model.LoginResponse;
import com.mudra.model.OrderListResponse;
import com.mudra.model.OrderRequest;
import com.mudra.model.ProductListResponse;
import com.mudra.model.RegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Lalit on 20-01-2017.
 */

public interface EndPointInterface {

    @POST
    Call<LoginResponse> LoginRequest(
            @Url String url, @Body LoginRequest user
    );

    @POST
    Call<RegisterResponse> RegisterRequest(
            @Url String url, @Body LoginRequest user
    );

    @POST
    Call<LoginResponse> UpdateProfile(
            @Url String url, @Body LoginRequest user
    );

    @GET
    Call<ProductListResponse> ProductList(
            @Url String url
    );

    @POST
    Call<OrderListResponse> OrderList(
            @Url String url
    );

    @Multipart
    @POST
    Call<ImageUploadResponse> UploadImage(@Url String url, @Part MultipartBody.Part image);

    @GET
    Call<DashboardResponse> DashboardBrand(
            @Url String url
    );

    @POST
    Call<LoginResponse> OrderRequest(
            @Url String url, @Body OrderRequest user
    );

    @GET
    Call<ProductListResponse> ForgotPassword(
            @Url String url
    );

    @GET
    Call<ProductListResponse> CheckSignup(
            @Url String url, @Query("email") String email, @Query("phone") String phone
    );

    @GET
    Call<ProductListResponse> CheckSignupPhone(
            @Url String url, @Query("phone") String phone
    );


}
