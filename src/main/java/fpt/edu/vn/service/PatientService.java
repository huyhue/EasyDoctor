package fpt.edu.vn.service;

import java.util.List;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.PatientDto;
import fpt.edu.vn.model.Patient;

public interface PatientService {
	List<PatientDto> getAllPatient();
	Patient getPatientById(int patientId);
	CommonMsg savePatient(PatientDto patientDto);
	CommonMsg deletePatient(int patientId);
}
