package com.wpfinki.airlinesproject.repository;

import com.wpfinki.airlinesproject.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
