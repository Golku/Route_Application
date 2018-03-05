package com.example.jason.route_application_kotlin.data.pojos;

import java.util.ArrayList;

/**
 * Created by Jason on 3/3/2018.
 */

public class CorrectedAddresses {

    private String routeCode;
    private ArrayList<String> correctedAddressesList;

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public ArrayList<String> getCorrectedAddressesList() {
        return correctedAddressesList;
    }

    public void setCorrectedAddressesList(ArrayList<String> correctedAddressesList) {
        this.correctedAddressesList = correctedAddressesList;
    }
}
