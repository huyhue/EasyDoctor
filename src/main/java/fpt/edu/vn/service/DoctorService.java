package fpt.edu.vn.service;

import java.util.List;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.model.Doctor;

public interface DoctorService {
	List<Doctor> getDoctorBySpecialtyId(int specialId);
	List<DoctorDto> getAllDoctor();
	Doctor getDoctorById(int doctorId);
	CommonMsg saveDoctor(DoctorDto doctordto);
	CommonMsg deleteDoctor(int doctorId);
}
