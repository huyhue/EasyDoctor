package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.WorkingPlan;

public interface WorkingPlanRepository extends JpaRepository<WorkingPlan, Integer> {
    @Query("select w from WorkingPlan w where w.doctor.id = :doctorId")
    WorkingPlan getWorkingPlanByDoctorId(@Param("doctorId") int doctorId);
}
