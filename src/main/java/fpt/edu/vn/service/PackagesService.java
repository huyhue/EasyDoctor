package fpt.edu.vn.service;

import java.util.List;



import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.model.Packages;

public interface PackagesService {
	List<Packages> getPackagesByDoctorId(int doctorId);
	List<Packages> getAllPackages();
	Packages getPackagesById(int packagesId);
	CommonMsg savePackages(Packages packages);
	CommonMsg deletePackages(int packagesId);
}
