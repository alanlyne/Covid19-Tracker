package com.alanlyne.Covid19Tracker.Controllers;

import java.util.List;

import com.alanlyne.Covid19Tracker.Models.CountyStats;
import com.alanlyne.Covid19Tracker.Services.CovidDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String home(Model model){

        List<Object> stats = covidDataService.getStats();
        int totalReportedCases = stats.stream().mapToInt(stat -> ((CountyStats) stat).getLatestTotal()).sum();
        int totalNewCases = stats.stream().mapToInt(stat -> ((CountyStats) stat).getTodaysTotal()).sum();

        model.addAttribute("countyStats", covidDataService.getStats());
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }

}
