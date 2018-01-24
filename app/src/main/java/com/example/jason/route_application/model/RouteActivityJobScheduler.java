package com.example.jason.route_application.model;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.example.jason.route_application.controller.RouteActivityController;
import com.example.jason.route_application.model.pojos.ApiResponse;


public class RouteActivityJobScheduler extends JobService{

    static InformationFetcher informationFetcher;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        informationFetcher = new InformationFetcher(){

            @Override
            protected void onPostExecute(ApiResponse apiResponse) {
                RouteActivityController.apiResponse = apiResponse;
                jobFinished(jobParameters, false);
            }

        };

        informationFetcher.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        informationFetcher.cancel(true);
        return false;
    }
}
