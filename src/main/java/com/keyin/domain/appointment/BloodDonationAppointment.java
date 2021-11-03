package com.keyin.domain.appointment;

import java.time.LocalDate;

public class BloodDonationAppointment {
    public int appointmentID;
    public LocalDate appointmentDateAndTime;
    public int appointmentDuration;
    public String location;
    public String bloodType;
    public boolean firstTimeDonor;
    public int donorId;

    public int getAppointmentID() {
        return this.appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public LocalDate getAppointmentDateAndTime() {
        return this.appointmentDateAndTime;
    }

    public void setAppointmentDateAndTime(LocalDate appointmentDateAndTime) {
        this.appointmentDateAndTime = appointmentDateAndTime;
    }

    public int getAppointmentDuration() {
        return this.appointmentDuration;
    }

    public void setAppointmentDuration(int appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean getFirstTimeDonor() {
        return firstTimeDonor;
    }

    public void setFirstTimeDonor(boolean firstTimeDonor) {
        this.firstTimeDonor = firstTimeDonor;
    }

    public int getDonorID() {
        return this.donorId;
    }

    public void setDonorID(int donorID) {
        this.donorId = donorID;
    }
}
