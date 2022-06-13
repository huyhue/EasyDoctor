package fpt.edu.vn.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.model.Declaration;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.FileModel;
import fpt.edu.vn.model.History;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.Review;
import fpt.edu.vn.model.Role;
import fpt.edu.vn.model.Specialty;
import fpt.edu.vn.model.User;

public interface UserService {
	List<Patient> getAllPatients();
	User findByEmail(String email);
	User findByUserName(String username);
	User findByConfirmationToken(String token);
	
	User getUserById(int userId);
	User findById(int userId);
	Doctor getDoctorById(int userId);
	Patient getPatientById(int userId);
	
	void updateUserPassword(ChangePasswordForm passwordChangeForm);
	void updatePatient(Patient patient);
	void updateDoctor(Doctor doctor);
	void updateUserActiveState(int id, boolean active);
	
	void savePasswordByUser(User user);
	void savePatientRegister(Patient userRE);

//	Doctor
	List<Doctor> getAllDoctors();
	List<Doctor> getAllDoctorsByPatient();
	List<Doctor> getAllDoctorsBySpecialty(int specialtyId);
	
	Collection<Role> getRolesForDoctor();
	Collection<Role> getRolesForPatient();
	
//	Review
	double getRatingByDoctorId(int doctorId);
	List<Review> getAllReviewByDoctorId(int doctorId);
	
//	History
	List<History> getHistoryByPatientId(int patientId);
	History getHistoryByAppointmentId(int id);
	
//	Declaration
	Declaration getDeclarationByPatientId(int patientId);
	void saveDeclarationByPatientId(Declaration declaration);
	void updateDeclarationByPatientId(Declaration declaration);
	
//	FileModel
	FileModel getFileByFileId(int id);
	FileModel getCertificationByUserId(int userId);
	String getImageByUserId(int userId);
	void saveImageProfileByUser(MultipartFile file, int id);
	void saveResultByDoctor(History history, MultipartFile[] files);
	void saveCertificationByDoctor(MultipartFile file, int doctorId);
	
//	Specialty
	List<Specialty> getAllSpecialty();
}
