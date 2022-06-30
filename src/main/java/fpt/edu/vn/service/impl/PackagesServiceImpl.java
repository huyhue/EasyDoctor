package fpt.edu.vn.service.impl;

import org.springframework.stereotype.Service;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.exception.PackagesNotFoundException;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.repository.PackagesRepository;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.UserService;

import java.util.List;

@Service
public class PackagesServiceImpl implements PackagesService {

	private final UserService userService;
	private final PackagesRepository packagesRepository;
	
	
	public PackagesServiceImpl(UserService userService, PackagesRepository packagesRepository) {
		super();
		this.userService = userService;
		this.packagesRepository = packagesRepository;
	}

	@Override
    public List<Packages> getPackagesByDoctorId(int doctorId) {
        return packagesRepository.findPackagesByDoctorId(doctorId);
    }
	
	@Override
	public List<Packages> getAllPackages() {
		return packagesRepository.findAll();
	}
	
	@Override
    public Packages getPackagesById(int packagesId) {
        return packagesRepository.findById(packagesId).orElseThrow(PackagesNotFoundException::new);
    }
	
	@Override
	public CommonMsg savePackages(Packages packages) {
		CommonMsg commonMsg = new CommonMsg();
		if (packages.getId() == null) {
			Packages checkName = packagesRepository.findByName(packages.getName());
			if (checkName != null) {
				commonMsg.setMsgCode("exitName");
				return commonMsg;
			}
			//add new
			packagesRepository.save(packages);
			commonMsg.setMsgCode("200");
		}else {
			//update
			Packages packagesUO = getPackagesById(packages.getId());
			packagesUO.setName(packages.getName());
			packagesUO.setPrice(packages.getPrice());
			packagesUO.setDuration(packages.getDuration());
			packagesUO.setEditable(packages.getEditable());
			packagesUO.setDescription(packages.getDescription());
			packagesRepository.save(packagesUO);
			commonMsg.setMsgCode("205");
		}
		return commonMsg;
	}
	
	@Override
	public CommonMsg deletePackages(int packagesId) {
		CommonMsg commonMsg = new CommonMsg();
		packagesRepository.deleteById(packagesId);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}
}
