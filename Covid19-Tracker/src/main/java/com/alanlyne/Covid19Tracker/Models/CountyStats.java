package com.alanlyne.Covid19Tracker.Models;

public class CountyStats {

    private String county;
    private int latestTotal;
    private String date;
    private int todaysTotal;

    public String getCounty() {
        return county;
    }

    public int getTodaysTotal() {
        return todaysTotal;
    }

    public void setTodaysTotal(int todaysTotal) {
        this.todaysTotal = todaysTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLatestTotal() {
        return latestTotal;
    }

    public void setLatestTotal(int casesNum) {
        this.latestTotal = casesNum;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "CountyStats{" + 
        "County = " + county + '\'' +
        ", Total Cases = " + latestTotal + '\'' +
        ", Todays Cases = " + todaysTotal + '\'' +
        ", Date = " + date + '\'' +
        "}";
        
    }
}
