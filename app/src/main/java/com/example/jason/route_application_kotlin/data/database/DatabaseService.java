package com.example.jason.route_application_kotlin.data.database;

import com.example.jason.route_application_kotlin.data.pojos.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.DatabaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Jason on 19-Feb-18.
 */

public interface DatabaseService {

    @FormUrlEncoded
    @POST("getAddressInfo.php")
    Call<DatabaseResponse> getAddressInformation(
            @Field("street_name") String street,
            @Field("post_code") String postCode,
            @Field("city") String city
    );

    @FormUrlEncoded
    @POST("addAddressComment.php")
    Call<DatabaseResponse> addCommentToAddress(
            @Field("street_name") String street,
            @Field("post_code") String postCode,
            @Field("city") String city,
            @Field("employee_id") String employeeId,
            @Field("message") String message,
            @Field("date") String date
    );
}
