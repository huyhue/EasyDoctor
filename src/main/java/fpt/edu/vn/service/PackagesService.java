package fpt.edu.vn.service;

import java.util.List;

import fpt.edu.vn.model.Packages;

public interface PackagesService {
	List<Packages> getPackagesByDoctorId(int docterId);
	List<Packages> getAllPackages();
	Packages getPackagesById(int packagesId);
}
