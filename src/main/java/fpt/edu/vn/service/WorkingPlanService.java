package fpt.edu.vn.service;

import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.WorkingPlan;

public interface WorkingPlanService {
    void updateWorkingPlan(WorkingPlan workingPlan);

    void addBreakToWorkingPlan(TimePeroid breakToAdd, int planId, String dayOfWeek);

    void deleteBreakFromWorkingPlan(TimePeroid breakToDelete, int planId, String dayOfWeek);

    WorkingPlan getWorkingPlanByDoctorId(int doctorId);
}
