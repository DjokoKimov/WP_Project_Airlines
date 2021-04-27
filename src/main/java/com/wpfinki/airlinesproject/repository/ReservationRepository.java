package com.wpfinki.airlinesproject.repository;

import com.wpfinki.airlinesproject.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
