package com.alanlyne.Covid19Tracker.Services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alanlyne.Covid19Tracker.Models.CountyStats;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service

public class CovidDataService {

    private static String CovidDataURL = "https://opendata-geohive.hub.arcgis.com/datasets/d9be85b30d7748b5b7c09450b8aede63_0.csv?outSR=%7B%22latestWkid%22%3A3857%2C%22wkid%22%3A102100%7D";

    private List<Object> stats = new ArrayList<>();

    public List<Object> getStats() {
        return stats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getCovidData() throws IOException, InterruptedException {

        List<Object> newStats = new ArrayList<>();

        // Create a client
        HttpClient client = HttpClient.newHttpClient();
        // Create a request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CovidDataURL)).build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        Object[] countyNum = new Object[26];
        int count = 0;
        int dirt = 0;
        for (CSVRecord record : records) {

            if (count == 26) {
                count = 0;
                dirt++;
            }

            CountyStats countyStat = new CountyStats();

            int casesNum = Integer.parseInt(record.get("ConfirmedCovidCases"));
            if (dirt > 0) {
                countyStat = (CountyStats) countyNum[count];

                countyStat.setTodaysTotal(casesNum - countyStat.getLatestTotal());
            }

            countyStat.setLatestTotal(casesNum);

            countyStat.setCounty(record.get("CountyName"));

            countyNum[count] = countyStat;

            count++;

        }
        for (int i = 0; i < 26; i++) {
            System.out.println(countyNum[i]);
            newStats.add(countyNum[i]);
        }
        this.stats = newStats;
    }

}