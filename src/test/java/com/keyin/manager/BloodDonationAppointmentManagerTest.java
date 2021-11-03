package com.keyin.manager;

import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import com.keyin.domain.donor.BloodDonor;
import com.keyin.domain.Database;
import com.keyin.domain.appointment.AppointmentSlot;
import com.keyin.domain.appointment.BloodDonationAppointment;
import com.keyin.domain.exception.InvalidDonationSchedulingException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BloodDonationAppointmentManagerTest {
    @Mock 
    private Database mockDatabase = Mockito.mock(Database.class);

    /////////////////// ------------------- test case should validate the basic implemented “happy path” ------------------- \\\\\\\\\\\\\\\\\\\

    @Test
    public void happyPath() {
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setId(1);
        bloodDonor.setBloodType("O+");
        bloodDonor.setFirstName("John");
        bloodDonor.setLastName("Doe");
        bloodDonor.setLastDonationDate(LocalDate.of(2021, Month.OCTOBER, 21));
        bloodDonor.setNextAppointmentDate(null);
        bloodDonor.setDateOfBirth(LocalDate.of(1990, Month.APRIL, 12));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(bloodDonor);

        BloodDonationAppointmentManager blood = new BloodDonationAppointmentManager(mockDatabase);
        try {
            blood.bookAppointment(1);
        } catch (InvalidDonationSchedulingException e) {
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Test"));
        }

        BloodDonationAppointment BloodDonation = new BloodDonationAppointment();
        BloodDonation.setAppointmentDuration(12);
        BloodDonation.setFirstTimeDonor(true);

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setBloodType("O+");
        appointmentSlot.setDate(LocalDate.of(2022, Month.FEBRUARY, 12));
        appointmentSlot.setStartTime(Time.valueOf("12:30:00"));
        appointmentSlot.setEndTime(Time.valueOf("14:00:00"));
        appointmentSlot.setLocation("123 Street");
        appointmentSlots.add(appointmentSlot);
        Mockito.when(mockDatabase.getAppointmentSlots()).thenReturn(appointmentSlots);
        BloodDonationAppointmentManager testHappyPath = new BloodDonationAppointmentManager(mockDatabase);
        try{
            testHappyPath.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid"));
        }
    }

    /////////////////// ------------------- A valid appointment blood type and donor blood type ------------------- \\\\\\\\\\\\\\\\\\\

    @Test
    public void validBloodType() {
        BloodDonor ValidDonorBloodType = new BloodDonor();
        ValidDonorBloodType.setId(1);
        ValidDonorBloodType.setFirstName("John");
        ValidDonorBloodType.setLastName("Doe");
        ValidDonorBloodType.setBloodType("O+");
        ValidDonorBloodType.setLastDonationDate(LocalDate.of(2021, Month.OCTOBER, 21));
        ValidDonorBloodType.setDateOfBirth(LocalDate.of(1990, Month.JANUARY, 12));
        Mockito.when(mockDatabase.getDonor(1)).thenReturn(ValidDonorBloodType);

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setLocation("123 Street");
        appointmentSlot.setBloodType("O+");
        appointmentSlot.setDate(LocalDate.of(2022, Month.FEBRUARY, 12));
        appointmentSlots.add(appointmentSlot);
        Mockito.when(mockDatabase.getAppointmentSlots()).thenReturn(appointmentSlots);
        BloodDonationAppointmentManager testBloodType = new BloodDonationAppointmentManager(mockDatabase);
        try{
            testBloodType.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid Blood Type"));
        }
    }

    /////////////////// ------------------- A invalid birth date for a donor, both too young and too old ------------------- \\\\\\\\\\\\\\\\\\\

    @Test
    public void invalidAge() {
        BloodDonor tooYoungBloodDonor = new BloodDonor();
        tooYoungBloodDonor.setId(1);
        tooYoungBloodDonor.setFirstName("Jane");
        tooYoungBloodDonor.setLastName("Doe");
        tooYoungBloodDonor.setDateOfBirth(LocalDate.of(2021, Month.OCTOBER, 31));
        Mockito.when(mockDatabase.getDonor(1)).thenReturn(tooYoungBloodDonor);
        BloodDonationAppointmentManager testYoungAge = new BloodDonationAppointmentManager(mockDatabase);
        try{
            testYoungAge.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid Age: Too Young"));
        }

        BloodDonor tooOldBloodDonor = new BloodDonor();
        tooOldBloodDonor.setId(1);
        tooOldBloodDonor.setFirstName("John");
        tooOldBloodDonor.setLastName("Doe");
        tooOldBloodDonor.setDateOfBirth(LocalDate.of(1800, Month.JANUARY, 1));
        Mockito.when(mockDatabase.getDonor(1)).thenReturn(tooOldBloodDonor);
        BloodDonationAppointmentManager testOldAge = new BloodDonationAppointmentManager(mockDatabase);
        try{
            testOldAge.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid Age: Too Old"));
        }
    }

    /////////////////// ------------------- A invalid blood type on the appointment and donor ------------------- \\\\\\\\\\\\\\\\\\\

    @Test
    public void invalidBloodType() {
        BloodDonor invalidBloodDonorBloodType = new BloodDonor();
        invalidBloodDonorBloodType.setId(1);
        invalidBloodDonorBloodType.setBloodType("O-");
        invalidBloodDonorBloodType.setFirstName("Bad");
        invalidBloodDonorBloodType.setLastName("Blood");
        invalidBloodDonorBloodType.setDateOfBirth(LocalDate.of(2000, Month.JANUARY, 1));
        Mockito.when(mockDatabase.getDonor(1)).thenReturn(invalidBloodDonorBloodType);

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setLocation("123 Street");
        appointmentSlot.setBloodType("A+");
        appointmentSlots.add(appointmentSlot);
        Mockito.when(mockDatabase.getAppointmentSlots()).thenReturn(appointmentSlots);
        BloodDonationAppointmentManager testBloodType = new BloodDonationAppointmentManager(mockDatabase);
        try{
            testBloodType.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid BloodType"));
        }
    }

    /////////////////// ------------------- A invalid donation dates for a Donor, both too soon and too far away ------------------- \\\\\\\\\\\\\\\\\\\

    @Test
    public void invalidDonationDates(){
        BloodDonor tooSoon = new BloodDonor();
        tooSoon.setId(1);
        tooSoon.setBloodType("O+");
        tooSoon.setFirstName("Alex");
        tooSoon.setLastName("Bristow");
        tooSoon.setDateOfBirth(LocalDate.of(1994, Month.MAY, 04));
        tooSoon.setLastDonationDate(LocalDate.of(2021, Month.AUGUST, 20));
        tooSoon.setNextAppointmentDate(LocalDate.of(2021, Month.AUGUST, 24));
        Mockito.when(mockDatabase.getDonor(1)).thenReturn(tooSoon);

        BloodDonationAppointment BloodDonation = new BloodDonationAppointment();
        BloodDonation.setAppointmentDuration(10);
        BloodDonation.setFirstTimeDonor(false);

        BloodDonationAppointmentManager tooSoonTest = new BloodDonationAppointmentManager(mockDatabase);
        try{
            tooSoonTest.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid Date: Too Soon"));
        }

        BloodDonor tooFar = new BloodDonor();
        tooFar.setId(1);
        tooFar.setBloodType("A");
        tooFar.setFirstName("Alex");
        tooFar.setLastName("Bristow");
        tooFar.setDateOfBirth(LocalDate.of(1994, Month.MAY, 04));
        tooFar.setLastDonationDate(LocalDate.of(2021, Month.AUGUST, 20));
        tooFar.setNextAppointmentDate(LocalDate.of(2023, Month.AUGUST, 24));
        Mockito.when(mockDatabase.getDonor(1)).thenReturn(tooFar);

        BloodDonationAppointment BloodDonation2 = new BloodDonationAppointment();
        BloodDonation2.setAppointmentDuration(9);
        BloodDonation2.setFirstTimeDonor(false);

        BloodDonationAppointmentManager tooFarTest = new BloodDonationAppointmentManager(mockDatabase);
        try{
            tooFarTest.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid Date: Too Far"));
        }
    }

    /////////////////// ------------------- A attempt to schedule an appointment when Donor already has one ------------------- \\\\\\\\\\\\\\\\\\\

    @Test
    public void invalidAppointmentDate() {
        BloodDonor attemptAppointment = new BloodDonor();
        attemptAppointment.setId(1);
        attemptAppointment.setBloodType("A");
        attemptAppointment.setFirstName("Alex");
        attemptAppointment.setLastName("Bristow");
        attemptAppointment.setDateOfBirth(LocalDate.of(1994, Month.MAY, 04));
        attemptAppointment.setLastDonationDate(LocalDate.of(2021, Month.AUGUST, 20));
        attemptAppointment.setNextAppointmentDate(LocalDate.of(2022, Month.JANUARY, 24));
        Mockito.when(mockDatabase.getDonor(1)).thenReturn(attemptAppointment);

        BloodDonationAppointment newAppointment = new BloodDonationAppointment();
        newAppointment.setAppointmentDuration(3);
        newAppointment.setFirstTimeDonor(false);

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot newAppointmentSlot = new AppointmentSlot();
        newAppointmentSlot.setId(1);
        newAppointmentSlot.setLocation("123 Street");
        newAppointmentSlot.setBloodType("B-");
        appointmentSlots.add(newAppointmentSlot);

        ArrayList<AppointmentSlot> appointmentSlots2 = new ArrayList<AppointmentSlot>();
        AppointmentSlot newAppointmentSlot2 = new AppointmentSlot();
        newAppointmentSlot2.setId(1);
        newAppointmentSlot2.setLocation("123 Street");
        newAppointmentSlot2.setBloodType("B-");
        appointmentSlots2.add(newAppointmentSlot);
        Mockito.when(mockDatabase.getAppointmentSlots()).thenReturn(appointmentSlots2);
        BloodDonationAppointmentManager testAppointment = new BloodDonationAppointmentManager(mockDatabase);
        try{
            testAppointment.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertFalse(e.getMessage().equalsIgnoreCase("Invalid Appointment: Already have appointment"));
        }
    }

}
