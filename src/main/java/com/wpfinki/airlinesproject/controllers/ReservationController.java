package com.wpfinki.airlinesproject.controllers;

import com.wpfinki.airlinesproject.Exception.FlightNotFound;
import com.wpfinki.airlinesproject.dto.Reservationrequest;
import com.wpfinki.airlinesproject.model.Flight;
import com.wpfinki.airlinesproject.model.Reservation;
import com.wpfinki.airlinesproject.repository.FlightRepository;
import com.wpfinki.airlinesproject.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ReservationController {
    private final FlightRepository flightRepository;

    public ReservationController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Autowired
    public ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @RequestMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightid") Long flightid, Model model) {
        LOGGER.info("ShowCompleteReservation() called with Flight id:" + flightid);
        Optional<Flight> flight = flightRepository.findById(flightid);
        //check for and handle error here
        if (!flight.isPresent()) {
            LOGGER.error("Flight not found{} ", flight.toString());
            throw new FlightNotFound("flightid" + flightid);
        }
        LOGGER.info("Flight found" + flight.toString());
        model.addAttribute("flight", flight.get());
        return "completeReservation";
    }

    @PostMapping("/completeReservation")
    public String completeReservation(Reservationrequest reservationrequest, Model model) {
        LOGGER.info("completeReservation() invoked with the Reservation" + reservationrequest.toString());
        Reservation reservation = reservationService.bookFlight(reservationrequest);
        model.addAttribute("msg","Reservation created successfully and reservation id is:"+reservation.getId());
        return "reservationConfirmation";
    }
}
