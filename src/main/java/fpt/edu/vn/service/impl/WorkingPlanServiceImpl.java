package fpt.edu.vn.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.WorkingPlan;
import fpt.edu.vn.repository.WorkingPlanRepository;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.WorkingPlanService;

@Service
public class WorkingPlanServiceImpl implements WorkingPlanService {

    private final WorkingPlanRepository workingPlanRepository;

    public WorkingPlanServiceImpl(WorkingPlanRepository workingPlanRepository) {
        this.workingPlanRepository = workingPlanRepository;
    }

    @Override
    @PreAuthorize("#updateData.doctor.id == principal.id")
    public void updateWorkingPlan(WorkingPlan updateData) {
        WorkingPlan workingPlan = workingPlanRepository.findById(updateData.getId()).get();
        workingPlan.getMonday().setWorkingHours(updateData.getMonday().getWorkingHours());
        workingPlan.getTuesday().setWorkingHours(updateData.getTuesday().getWorkingHours());
        workingPlan.getWednesday().setWorkingHours(updateData.getWednesday().getWorkingHours());
        workingPlan.getThursday().setWorkingHours(updateData.getThursday().getWorkingHours());
        workingPlan.getFriday().setWorkingHours(updateData.getFriday().getWorkingHours());
        workingPlan.getSaturday().setWorkingHours(updateData.getSaturday().getWorkingHours());
        workingPlan.getSunday().setWorkingHours(updateData.getSunday().getWorkingHours());
        workingPlanRepository.save(workingPlan);
    }
    
    //TimePeroid{start=06:42, end=07:42}2monday
    @Override
    public void addBreakToWorkingPlan(TimePeroid breakToAdd, int planId, String dayOfWeek) {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WorkingPlan workingPlan = workingPlanRepository.getWorkingPlanByDoctorId(planId);
        if (!workingPlan.getDoctor().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        workingPlan.getDay(dayOfWeek).getBreaks().add(breakToAdd);
        workingPlanRepository.save(workingPlan);
    }

    @Override
    public void deleteBreakFromWorkingPlan(TimePeroid breakToDelete, int planId, String dayOfWeek) {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WorkingPlan workingPlan = workingPlanRepository.findById(planId).get();
        if (workingPlan.getDoctor().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        workingPlan.getDay(dayOfWeek).getBreaks().remove(breakToDelete);
        workingPlanRepository.save(workingPlan);
    }


    @Override
    @PreAuthorize("#doctorId == principal.id")
    public WorkingPlan getWorkingPlanByDoctorId(int doctorId) {
        return workingPlanRepository.getWorkingPlanByDoctorId(doctorId);
    }


}
