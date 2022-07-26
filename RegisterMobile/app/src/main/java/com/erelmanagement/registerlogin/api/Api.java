/*-----------------------------------------------------------------------------
 - Developed by erwin                                                -
 - Last modified 3/17/19 5:24 AM                                              -
 - Subscribe : https://www.youtube.com/erwin                         -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.erelmanagement.registerlogin.api;

import com.erelmanagement.registerlogin.model.View;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("api/login")
    Call<View> login(
            @Field("email") String email,
            @Field("password") String unit );
    @FormUrlEncoded
    @POST("api/register")
    Call<View> register(
            @Field("first") String first,
            @Field("last") String last,
            @Field("email") String email ,
            @Field("password") String password   );
}

