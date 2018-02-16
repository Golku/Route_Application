package com.example.jason.route_application_kotlin.data.api;

import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface PresenterCallBack {
    void processApiResponse(ApiResponse apiResponse);
}
