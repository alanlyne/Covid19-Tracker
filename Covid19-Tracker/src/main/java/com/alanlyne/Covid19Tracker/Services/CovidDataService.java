package com.alanlyne.Covid19Tracker.Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alanlyne.Covid19Tracker.Models.CountyStats;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service

public class CovidDataService {

    //private static String CovidDataURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String CovidDataURL = "https://opendata-geohive.hub.arcgis.com/datasets/d9be85b30d7748b5b7c09450b8aede63_0.csv?outSR=%7B%22latestWkid%22%3A3857%2C%22wkid%22%3A102100%7D";

    private List<CountyStats> stats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getCovidData() throws IOException, InterruptedException {


        List<CountyStats> newStats = new ArrayList<>(); 

        // Create a client
        HttpClient client = HttpClient.newHttpClient();
        // Create a request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CovidDataURL)).build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        for (CSVRecord record : records) {

            CountyStats countyStat = new CountyStats();

            countyStat.setCounty(record.get("CountyName"));
            countyStat.setLatestTotal(record.get("ConfirmedCovidCases"));

            System.out.println(countyStat);
            newStats.add(countyStat);
        }
        this.stats = newStats;
    }

}
