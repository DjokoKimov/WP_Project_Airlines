package com.wpfinki.airlinesproject.service.serviceImpl;

import com.wpfinki.airlinesproject.Exception.FlightNotFound;
import com.wpfinki.airlinesproject.dto.Reservationrequest;
import com.wpfinki.airlinesproject.model.Flight;
import com.wpfinki.airlinesproject.model.Passenger;
import com.wpfinki.airlinesproject.model.Reservation;
import com.wpfinki.airlinesproject.repository.FlightRepository;
import com.wpfinki.airlinesproject.repository.PassengerRepository;
import com.wpfinki.airlinesproject.repository.ReservationRepository;
import com.wpfinki.airlinesproject.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;


    public ReservationServiceImpl(FlightRepository flightRepository,
                                  PassengerRepository passengerRepository,
                                  ReservationRepository reservationRepository) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.reservationRepository = reservationRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Override
    public Reservation bookFlight(Reservationrequest reservationrequest){
        // will do the payment here
        // if payment successful proceed
        LOGGER.info("Inside bookFlight()");
        Long flightId=reservationrequest.getFlightId();
        Optional<Flight> flightOptional=flightRepository.findById(flightId);
        if(!flightOptional.isPresent()){
            throw new FlightNotFound("No flight found with"+flightId);
        }
        LOGGER.info("Flight found with id: {}"+flightId);
        Flight flight=flightOptional.get();
        Passenger passenger= new Passenger();
        passenger.setFirstName(reservationrequest.getPassengerFirstName());
        passenger.setMiddleName(reservationrequest.getPassengerMiddleName());
        passenger.setLastName(reservationrequest.getPassengerLastName());
        passenger.setEmail(reservationrequest.getPassengerEmail());
        passenger.setPhone(reservationrequest.getPassengerPhone());

        passengerRepository.save(passenger);
        LOGGER.info("Saved the passenger: "+ passenger);

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setCheckedin(false);

        final Reservation savedReservation =reservationRepository.save(reservation);
        LOGGER.info("Saving the reservation: "+reservation);

        return  savedReservation;
    }
}
