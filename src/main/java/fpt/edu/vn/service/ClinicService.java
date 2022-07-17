package fpt.edu.vn.service;

import java.util.List;


import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.model.Clinic;

public interface ClinicService {
	List<Clinic> getClinicByDoctorId(int doctorId);
	List<Clinic> getAllClinic();
	Clinic getClinicById(int clinicId);
	CommonMsg saveClinic(Clinic clinic);
	CommonMsg deleClinic(int clinicId);
}
