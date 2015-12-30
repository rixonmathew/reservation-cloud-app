package com.rixon.learn.springcloud;

public class Reservation {

  private Long id;
  private String reservationName;

  public void setId(Long id) {
    this.id = id;
  }

  public void setReservationName(String reservationName) {
    this.reservationName = reservationName;
  }

  public Long getId() {
    return id;
  }

  public String getReservationName() {
    return reservationName;
  }
}
