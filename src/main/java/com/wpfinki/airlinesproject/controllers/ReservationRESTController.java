package com.wpfinki.airlinesproject.controllers;

import com.wpfinki.airlinesproject.Exception.ReservationNotFound;
import com.wpfinki.airlinesproject.dto.ReservationUpdateRequest;
import com.wpfinki.airlinesproject.model.Reservation;
import com.wpfinki.airlinesproject.repository.ReservationRepository;
import org.aspectj.weaver.reflect.IReflectionWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ReservationRESTController {
    private final ReservationRepository reservationRepository;

    public ReservationRESTController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRESTController.class);

    @GetMapping("/reservations/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
        //check and handle error here in case of no reservation found
        LOGGER.info("Inside findReservation() for id:" + id);
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (!reservation.isPresent()) {
            LOGGER.error("msg", "No reservation with id: " + id);
            throw new ReservationNotFound("Reservation not found with id:" + id);
        }
        return reservation.get();
    }

    @PostMapping("/reservations")
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest reservationUpdateRequest) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationUpdateRequest.getId());
        // check and handle error if no reservation with that id found
        LOGGER.info("Inside updateReservation() for" +reservationUpdateRequest);
        if(!reservation.isPresent()){
            LOGGER.error("No reservation exists with id: "+reservationUpdateRequest.getId());
            throw new ReservationNotFound("No reservation exists with id: "+reservationUpdateRequest.getId());
        }
        reservation.get().setNumberOfBags(reservationUpdateRequest.getNumberOfBags());
        reservation.get().setCheckedin(reservationUpdateRequest.isCheckedIn());
        LOGGER.info("Saving Reservation");
        return reservationRepository.save(reservation.get());
    }
}
