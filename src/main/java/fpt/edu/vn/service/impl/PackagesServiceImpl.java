package fpt.edu.vn.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
}
