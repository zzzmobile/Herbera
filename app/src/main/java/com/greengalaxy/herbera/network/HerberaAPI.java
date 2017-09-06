package com.greengalaxy.herbera.network;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Wang on 9/6/2017.
 */

public interface HerberaAPI {
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("api/auth/login")
    Observable<LoginResult> login(
            @Field("user_email") String email,
            @Field("user_password") String password
    );

    @FormUrlEncoded
    @POST("api/auth/register")
    Observable<RegisterResult> register(
            @Field("user_fname") String firstName,
            @Field("user_lname") String lastName,
            @Field("user_password") String password,
            @Field("user_email") String email,
            @Field("user_created_on") String createDate,
            @Field("user_type") String type,
            @Field("user_phno") String number
    );

    @FormUrlEncoded
    @POST("api/auth/reset_password")
    Observable<ResetPasswordResult> forgotPassword(
            @Field("user_email") String email,
            @Field("user_otp") String opt,
            @Field("user_password") String password
    );
}
