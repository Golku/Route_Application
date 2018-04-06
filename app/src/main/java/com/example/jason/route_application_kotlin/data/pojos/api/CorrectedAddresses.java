package com.example.jason.route_application_kotlin.data.pojos.api;

import java.util.List;

/**
 * Created by Jason on 3/3/2018.
 */

public class CorrectedAddresses {

    private String routeCode;
    private List<String> correctedAddressesList;

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public List<String> getCorrectedAddressesList() {
        return correctedAddressesList;
    }

    public void setCorrectedAddressesList(List<String> correctedAddressesList) {
        this.correctedAddressesList = correctedAddressesList;
    }
}
