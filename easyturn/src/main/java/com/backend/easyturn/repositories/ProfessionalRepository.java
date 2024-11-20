package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {
    Professional findByProfessionalRegistration(String professionalRegistration);
    Professional findByMail(String mail);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.professional.idProfessional = :professionalId " +
            "AND CAST(a.appointmentDateTime AS DATE) = :date " +
            "ORDER BY a.appointmentDateTime")
    Set<Appointment> findAppointmentsByProfessionalAndDate(
            @Param("professionalId") Integer professionalId,
            @Param("date") LocalDate date);
}
