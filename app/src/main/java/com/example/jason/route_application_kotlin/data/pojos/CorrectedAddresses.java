package com.example.jason.route_application_kotlin.data.pojos;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Jason on 02-Mar-18.
 */

public class CorrectedAddresses {

    private String routeCode;
    private ArrayList<String> invalidAddressesList;
    private ArrayList<String> correctedAddressesList;

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public ArrayList<String> getInvalidAddressesList() {
        return invalidAddressesList;
    }

    public void setInvalidAddressesList(ArrayList<String> invalidAddressesList) {
        this.invalidAddressesList = invalidAddressesList;
    }

    public ArrayList<String> getCorrectedAddressesList() {
        return correctedAddressesList;
    }

    public void setCorrectedAddressesList(ArrayList<String> correctedAddressesList) {
        this.correctedAddressesList = correctedAddressesList;
    }

}
