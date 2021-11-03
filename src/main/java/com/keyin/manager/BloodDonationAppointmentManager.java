package com.keyin.manager;

import com.keyin.domain.Database;
import com.keyin.domain.appointment.AppointmentSlot;
import com.keyin.domain.appointment.BloodDonationAppointment;
import com.keyin.domain.exception.InvalidDonationSchedulingException;
import com.keyin.domain.donor.BloodDonor;

import java.util.List;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class BloodDonationAppointmentManager {
    private Database database;

    public BloodDonationAppointmentManager(Database database) {
        this.database = database;
    }
    
    public BloodDonationAppointment bookAppointment(int bloodDonorId) throws InvalidDonationSchedulingException {
        BloodDonationAppointment bloodDonationAppointment = new BloodDonationAppointment();

        BloodDonor bloodDonor = database.getDonor(bloodDonorId);

        // ------------ A donor can only have 1 appointment scheduled at a time ------------ \\

        if(bloodDonor.getNextAppointmentDate() == null) {

        } else {
            throw new InvalidDonationSchedulingException("Already has appointment booked");
        }

        // ------------ Donors must be 18 years old and less then 80 years old ------------ \\

        if(bloodDonor.getDateOfBirth().isBefore(LocalDate.of(2003, Month.JANUARY, 1)) && bloodDonor.getDateOfBirth().isAfter(LocalDate.of(1941, Month.JANUARY, 1))) {

        } else {
            throw new InvalidDonationSchedulingException("Donor isn't between the ages of 18 and 80");
        }        

        List<AppointmentSlot> appointmentSlotList = database.getAppointmentSlots();

        // iterate through list of appointment slots
        for (AppointmentSlot appointmentSlot: appointmentSlotList) {

            // ------------ Appointment blood type must match Donor type ------------ \\

                if(appointmentSlot.getBloodType().equalsIgnoreCase(bloodDonor.getBloodType())) {

                } else {
                        throw new InvalidDonationSchedulingException("Blood Types don't match");
                }

                // if first time donor 56 day rule doesn't apply, indicates its a first time donor.
                if(bloodDonationAppointment.getFirstTimeDonor() == true) {

                    // if not first time 56 day rule applies
                } else if (bloodDonor.getLastDonationDate().isAfter(bloodDonor.getLastDonationDate().plus(56, ChronoUnit.DAYS))) {

                } else {
                    throw new InvalidDonationSchedulingException("Last donation appointment was less than 56 days ago.");
                }
                
                // ------------ o	Appointment Date must be no more than 1 year from last appointment date ------------ \\

                if(appointmentSlot.getDate().isAfter(bloodDonor.getLastDonationDate().plusYears(1))) {

                } else {
                    throw new InvalidDonationSchedulingException("Donor isn't between the ages of 18 and 80");
                }   

        }
        return bloodDonationAppointment;

    }
    
}
