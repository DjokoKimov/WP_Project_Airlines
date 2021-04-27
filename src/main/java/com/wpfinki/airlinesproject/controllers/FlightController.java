package com.wpfinki.airlinesproject.controllers;

import com.wpfinki.airlinesproject.model.Flight;
import com.wpfinki.airlinesproject.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class FlightController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);

    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/findFlights")
    public String findFlights(@RequestParam("source") String source,
                              @RequestParam("destination") String destination,
                              @RequestParam("departDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departDate, Model model) {
        LOGGER.info("Inside findFlights() from" + source + "to " + destination + "Departure Date: " + departDate);
        List<Flight> flights = flightRepository.findFlight(source, destination, departDate);
        model.addAttribute("flights",flights);
        LOGGER.info("Flights found are:"+flights.toString());
        return "displayFlights";
    }

    @GetMapping("/admin/showAddFlight")
    public String showAddFlightPage(){
        return "addFlight";
    }

    @GetMapping("/admin/addFlight")
    public String addFlight(@ModelAttribute("flight") Flight flight, Model model){
    flightRepository.save(flight);
    model.addAttribute("msg","Flight added successfully");
    return   "addFlight";
    }
}
