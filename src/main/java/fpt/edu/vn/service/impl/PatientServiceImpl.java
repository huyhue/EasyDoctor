package fpt.edu.vn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.PatientDto;
import fpt.edu.vn.exception.PatientNotFoundException;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.repository.PatientRepository;
import fpt.edu.vn.service.PatientService;
import fpt.edu.vn.service.UserService;

@Service
public class PatientServiceImpl implements PatientService {
		
	private final UserService userService;
	private final PatientRepository patientRepository;
	
	public PatientServiceImpl(UserService userService, PatientService patientService, PatientRepository patientRepository) {
		super();
		this.userService = userService;
		this.patientRepository = patientRepository;
	}

	@Override
	public List<PatientDto> getAllPatient() {
		List <PatientDto> listDTO = new ArrayList<>();
		List<Patient> list = patientRepository.findAll();
		for (Patient p : list) {
			listDTO.add(new PatientDto(p.getId(), p.getUserName(), p.getEmail(), p.getFullname(), p.getMobile()));
		}
		return listDTO;
	}

	@Override
	public Patient getPatientById(int patientId) {
		return patientRepository.findById(patientId).orElseThrow(PatientNotFoundException::new);
	}

	@Override
	public CommonMsg savePatient(PatientDto patientDto) {
		CommonMsg commonMsg = new CommonMsg();
		if (patientDto.getId() < 0 ) {
			Patient checkName = (Patient) patientRepository.findByName(patientDto.getFullname());
			if (checkName != null) {
				commonMsg.setMsgCode("exitName");
						return commonMsg;
			}
			//add new
			Patient patient = new Patient();
			patient.setEmail(patientDto.getEmail());
			patientRepository.save(patient);
			commonMsg.setMsgCode("200");
		}else{
			//update
			Patient patientView = getPatientById(patientDto.getId());
			patientView.setFullname(patientDto.getFullname());
			patientView.setUserName(patientDto.getUserName());
			patientView.setEmail(patientDto.getEmail());
			patientView.setMobile(patientDto.getMobile());
			patientRepository.save(patientView);
			commonMsg.setMsgCode("205");
		}
		return commonMsg;
	}

	@Override
	public CommonMsg deletePatient(int patientId) {
		CommonMsg commonMsg = new CommonMsg();
		patientRepository.deleteById(patientId);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}


	
}
