package com.alanlyne.Covid19Tracker.Models;

public class CountyStats {

    private String county;
    private String latestTotal;

    public String getCounty() {
        return county;
    }

    public String getLatestTotal() {
        return latestTotal;
    }

    public void setLatestTotal(String latestTotal) {
        this.latestTotal = latestTotal;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "CountyStats{" + 
        "County = " + county + '\'' +
        ", Total Cases = " + latestTotal + '\'' +
        "}";
        
    }

}
