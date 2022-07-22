package fpt.edu.vn.service.impl;


import org.springframework.stereotype.Service;



import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.exception.DoctorNotFoundException;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.repository.DoctorRepository;
import fpt.edu.vn.service.DoctorService;
import fpt.edu.vn.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	private final UserService userService;
	private final DoctorRepository doctorRepository;
	
	public DoctorServiceImpl(UserService userService, DoctorRepository doctorRepository) {
		super();
		this.userService = userService;
		this.doctorRepository = doctorRepository;
	}
	@Override
	public List<Doctor> getDoctorBySpecialtyId(int specialId) {
		return doctorRepository.findBySpecialtyId(specialId);
	}

	@Override
	public List<DoctorDto> getAllDoctor() {
		List<DoctorDto> listDTO = new ArrayList<>();
		List<Doctor> list = doctorRepository.findAll();
		for (Doctor d : list) {
			listDTO.add(new DoctorDto(d.getId(), d.getUserName(), d.getEmail(), d.getFullname(), null, null));
		}
		return listDTO;
	}

	@Override
	public Doctor getDoctorById(int doctorId) {
		return doctorRepository.findById(doctorId).orElseThrow(DoctorNotFoundException::new);
	}

	@Override
	public CommonMsg saveDoctor(DoctorDto doctordto) {
		CommonMsg commonMsg = new CommonMsg();
		if (doctordto.getId() < 0 ) {
			Doctor checkName = doctorRepository.findByName(doctordto.getFullname());
			if (checkName != null) {
				commonMsg.setMsgCode("exitName");
						return commonMsg;
			}
			//add new
			Doctor doctor = new Doctor();
			doctor.setEmail(doctordto.getEmail());
			doctorRepository.save(doctor);
			commonMsg.setMsgCode("200");
		}else {
			//update
			Doctor doctorAdd = getDoctorById(doctordto.getId());
			doctorAdd.setFullname(doctordto.getFullname());
			doctorAdd.setUserName(doctordto.getUserName());
			doctorAdd.setEmail(doctordto.getEmail());
			doctorRepository.save(doctorAdd);
			commonMsg.setMsgCode("205");
		}
		return commonMsg;
	}

	@Override
	public CommonMsg deleteDoctor(int doctorId) {
		CommonMsg commonMsg = new CommonMsg();
		doctorRepository.deleteById(doctorId);
		commonMsg.setMsgCode("200");
		return commonMsg;

	}
}