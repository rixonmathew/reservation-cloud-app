package com.rixon.learn.springcloud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RepositoryRestResource
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @RequestMapping(path = "by-name")
    Collection<Reservation> findByReservationName(@Param("reservationName") String reservationName);
}
