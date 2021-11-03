package com.keyin.domain;

import com.keyin.domain.appointment.AppointmentSlot;
import com.keyin.domain.donor.BloodDonor;

import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;

public class Database {
    public BloodDonor bloodDonor;

    public List<AppointmentSlot> getAppointmentSlots() {
        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();

        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setBloodType("O+");
        appointmentSlot.setDate(LocalDate.of(2022, Month.FEBRUARY, 12));
        appointmentSlot.setStartTime(Time.valueOf("12:30:00"));
        appointmentSlot.setEndTime(Time.valueOf("14:00:00"));
        appointmentSlot.setLocation("123 Street");


        return appointmentSlots;
    }

    public BloodDonor getDonor(int id) {
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setId(id);
        bloodDonor.setBloodType("O+");
        bloodDonor.setFirstName("John");
        bloodDonor.setLastName("Doe");
        bloodDonor.setLastDonationDate(LocalDate.of(2021, Month.OCTOBER, 21));
        bloodDonor.setNextAppointmentDate(null);
        bloodDonor.setDateOfBirth(LocalDate.of(1990, Month.APRIL, 12));
        return bloodDonor;
    }

}
