package fpt.edu.vn.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.component.PatientDto;
import fpt.edu.vn.component.ProfileDto;
import fpt.edu.vn.component.ReviewDto;
import fpt.edu.vn.model.Clinic;
import fpt.edu.vn.model.Declaration;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.FileModel;
import fpt.edu.vn.model.History;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.Question;
import fpt.edu.vn.model.Review;
import fpt.edu.vn.model.Role;
import fpt.edu.vn.model.Specialty;
import fpt.edu.vn.model.User;

public interface UserService {
	// Patient
	List<Patient> getAllPatients();

	List<PatientDto> getAllPatient();

	CommonMsg savePatient(PatientDto patientDto);

	User findByEmail(String email);

	User findByUserName(String username);

	User findByConfirmationToken(String token);

	User getUserById(int userId);

	User findById(int userId);

	Doctor getDoctorById(int userId);

	Patient getPatientById(int userId);

	CommonMsg updateUserPassword(ChangePasswordForm passwordChangeForm);

	void updatePatient(Patient patient);

	void updateDoctor(Doctor doctor);

	void updateUserActiveState(int id, boolean active);

	void savePasswordByUser(User user);

	void savePatientRegister(Patient userRE);

	CommonMsg saveProfileInfo(ProfileDto profileDto);

	// Doctor
	List<DoctorDto> getAllDoctors();

	CommonMsg saveDoctor(DoctorDto doctordto);

	void updateDoctorWithWorkingPlan(Doctor doctor);

	List<Doctor> getAllDoctorsByPatient();

	List<Doctor> getAllDoctorsBySpecialty(int specialtyId);

	Collection<Role> getRolesForDoctor();

	Collection<Role> getRolesForPatient();

	// Review
	double getRatingByDoctorId(int doctorId);

	List<Review> getAllReviewByDoctorId(int doctorId);

	List<ReviewDto> getAllReview();

	CommonMsg deleteReview(int reviewId);

	// History
	List<History> getHistoryByPatientId(int patientId);

	History getHistoryByAppointmentId(int id);

	// Declaration
	Declaration getDeclarationByPatientId(int patientId);

	void saveDeclarationByPatientId(Declaration declaration);

	void updateDeclarationByPatientId(Declaration declaration);

	// FileModel
	FileModel getFileByFileId(int id);

	FileModel getCertificationByUserId(int userId);

	String getImageByUserId(int userId);

	void saveImageProfileByUser(MultipartFile file, int id);

	void saveResultByDoctor(History history, MultipartFile[] files);

	void saveCertificationByDoctor(MultipartFile file, int doctorId);

	// Specialty
	List<Specialty> getAllSpecialty();

	// Clinic
	List<Clinic> getAllClinic();

	CommonMsg saveClinic(Clinic clinic);

	CommonMsg deleteClinic(int clinicId);

	// Follow
	void followDoctor(int doctorId, int patientId);

	void unfollowDoctor(int doctorId, int patientId);

	Boolean isFollowDoctor(int doctorId, int patientId);

	CommonMsg saveQuestion(Question question);

	// Question
	List<Question> getAllQuestion();

	Question sendAnswerQuestion(Question question);
}
