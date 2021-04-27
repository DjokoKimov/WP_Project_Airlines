package com.wpfinki.airlinesproject.service;

import com.wpfinki.airlinesproject.dto.Reservationrequest;
import com.wpfinki.airlinesproject.model.Reservation;

public interface ReservationService {
    public Reservation bookFlight(Reservationrequest reservationrequest);
}
