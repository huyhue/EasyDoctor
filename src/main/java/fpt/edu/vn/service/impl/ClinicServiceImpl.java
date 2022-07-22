package fpt.edu.vn.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.exception.ClinicNotFoundException;
import fpt.edu.vn.model.Clinic;
import fpt.edu.vn.repository.ClinicRepository;
import fpt.edu.vn.service.ClinicService;
import fpt.edu.vn.service.UserService;

@Service
public class ClinicServiceImpl implements ClinicService{
	private final UserService userService;
	private final ClinicRepository clinicRepository;
	
	public ClinicServiceImpl(UserService userService, ClinicRepository clinicRepository) {
		super();
		this.userService = userService;
		this.clinicRepository = clinicRepository;
	}

	@Override
	public List<Clinic> getClinicByDoctorId(int doctorId) {
		return clinicRepository.findClinicByDoctorId(doctorId);
	}

	@Override
	public List<Clinic> getAllClinic() {
		return clinicRepository.findAll();
	}

	@Override
	public Clinic getClinicById(int clinicId) {
		return clinicRepository.findById(clinicId).orElseThrow(ClinicNotFoundException::new);
	}

	@Override
	public CommonMsg saveClinic(Clinic clinic) {
		CommonMsg commonMsg = new CommonMsg();
		if (clinic.getId() == null) {
			Clinic checkName = clinicRepository.findByName(clinic.getName());
			if(checkName != null) {
				commonMsg.setMsgCode("exitName");
				return commonMsg;
			}
			//add new
			clinicRepository.save(clinic);
			commonMsg.setMsgCode("200");
		}else {
			//update
			Clinic clinicUO = getClinicById(clinic.getId());
			clinicUO.setName(clinic.getName());
			clinicUO.setAddress(clinic.getAddress());
			clinicUO.setPhone(clinic.getPhone());
			clinicUO.setWebsite(clinic.getWebsite());
			clinicUO.setDescription(clinic.getDescription());
			clinicRepository.save(clinicUO);
			commonMsg.setMsgCode("205");
		}
		return commonMsg;
	}

	@Override
	public CommonMsg deleClinic(int clinicId) {
		CommonMsg commonMsg = new CommonMsg();
		clinicRepository.deleteById(clinicId);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}
	
}
