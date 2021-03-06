package fpt.edu.vn.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.component.PatientDto;
import fpt.edu.vn.component.ReviewDto;
import fpt.edu.vn.model.Clinic;
import fpt.edu.vn.model.Declaration;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.FileModel;
import fpt.edu.vn.model.History;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.Review;
import fpt.edu.vn.model.Role;
import fpt.edu.vn.model.Specialty;
import fpt.edu.vn.model.User;
import fpt.edu.vn.repository.DoctorRepository;
import fpt.edu.vn.repository.FileModelRepository;
import fpt.edu.vn.repository.HistoryRepository;
import fpt.edu.vn.repository.PatientRepository;
import fpt.edu.vn.repository.ReviewRepository;
import fpt.edu.vn.repository.ClinicRepository;
import fpt.edu.vn.repository.DeclarationRepository;
import fpt.edu.vn.repository.RoleRepository;
import fpt.edu.vn.repository.SpecialtyRepository;
import fpt.edu.vn.repository.UserRepository;
import fpt.edu.vn.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final RoleRepository roleRepository;
	private final ReviewRepository reviewRepository;
	private final HistoryRepository historyRepository;
	private final FileModelRepository fileModelRepository;
	private final DeclarationRepository declarationRepository;
	private final SpecialtyRepository specialtyRepository;
	private final ClinicRepository clinicRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, DoctorRepository doctorRepository,
			PatientRepository patientRepository, RoleRepository roleRepository, ReviewRepository reviewRepository,
			HistoryRepository historyRepository, FileModelRepository fileModelRepository,
			DeclarationRepository declarationRepository, SpecialtyRepository specialtyRepository,
			ClinicRepository clinicRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.roleRepository = roleRepository;
		this.reviewRepository = reviewRepository;
		this.historyRepository = historyRepository;
		this.fileModelRepository = fileModelRepository;
		this.declarationRepository = declarationRepository;
		this.specialtyRepository = specialtyRepository;
		this.clinicRepository = clinicRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public List<PatientDto> getAllPatient() {
		List<PatientDto> listDTO = new ArrayList<>();
		List<Patient> list = patientRepository.findAll();
		for (Patient p : list) {
			listDTO.add(new PatientDto(p.getId().toString(), p.getUserName(), p.getEmail(), p.getFullname(), p.getMobile(), p.getAddress()));
		}
		return listDTO;
	}

	@Override
	public CommonMsg savePatient(PatientDto patientDto) {
		CommonMsg commonMsg = new CommonMsg();
		if (patientDto.getId().isEmpty()) {
			Optional<Patient> checkUserName = patientRepository.findByUserName(patientDto.getUserName());
			Patient checkEmail = patientRepository.findByEmail(patientDto.getEmail());
			if (checkUserName.isPresent()) {
				commonMsg.setMsgCode("exitUserName");
				return commonMsg;
			}
			if (checkEmail != null) {
				commonMsg.setMsgCode("exitEmail");
				return commonMsg;
			}
			// add new
			Patient patient = new Patient();
			patient.setEmail(patientDto.getEmail());
			patient.setFullname(patientDto.getFullname());
			patient.setUserName(patientDto.getUserName());
			patient.setMobile(patientDto.getMobile());
			patient.setAddress(patientDto.getAddress());
			patientRepository.save(patient);
			commonMsg.setMsgCode("200");
		} else {
			// update
			Patient patientView = getPatientById(Integer.parseInt(patientDto.getId()));
			patientView.setFullname(patientDto.getFullname());
			patientView.setMobile(patientDto.getMobile());
			patientView.setAddress(patientDto.getAddress());
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

	@Override
	public User findByConfirmationToken(String token) {
		return userRepository.findByConfirmationToken(token);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@PreAuthorize("#patient.id == principal.id or hasRole('ADMIN')")
	public void updatePatient(Patient patient) {
		Patient p = patientRepository.findById(patient.getId()).get();
		p.setFullname(patient.getFullname());
		p.setMobile(patient.getMobile());
		p.setAge(patient.getAge());
		p.setGender(patient.getGender());
		p.setAddress(patient.getAddress());
		patientRepository.save(p);
	}

	@Override
	@PreAuthorize("#doctor.id == principal.id or hasRole('ADMIN')")
	public void updateDoctor(Doctor doctor) {
		Doctor d = doctorRepository.findById(doctor.getId()).get();
		d.setFullname(doctor.getFullname());
		d.setMobile(doctor.getMobile());
		d.setAge(doctor.getAge());
		d.setGender(doctor.getGender());
		d.setStartPracticeDate(doctor.getStartPracticeDate());
		d.setDescription(doctor.getDescription());
		d.setPackages(doctor.getPackages());
		doctorRepository.save(d);
	}

	@Override
	public void updateUserActiveState(int id, boolean active) {
		User user = userRepository.findById(id).get();
		user.setEnabled(active);
		userRepository.save(user);
	}

	@Override
	public User findByUserName(String username) {
		try {
			userRepository.findByUserName(username).get();
		} catch (Exception e) {
			return null;
		}
		return userRepository.findByUserName(username).get();
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<DoctorDto> getAllDoctors() {
		List<DoctorDto> listDTO = new ArrayList<>();
		List<Doctor> list = doctorRepository.findAll();
		for (Doctor d : list) {
			listDTO.add(new DoctorDto(d.getId().toString(), d.getUserName(), d.getEmail(), d.getFullname(), d.getSpecialty().getName(), d.getClinic().getName()));
		}
		return listDTO;
	}

	@Override
	public CommonMsg saveDoctor(DoctorDto doctordto) {
		CommonMsg commonMsg = new CommonMsg();
		if (doctordto.getNameSpecialty() == null) {
			commonMsg.setMsgCode("notSpecialty");
			return commonMsg;
		}
		if (doctordto.getNameClinic() == null) {
			commonMsg.setMsgCode("notClinic");
			return commonMsg;
		}
		if (doctordto.getId().isEmpty()) {
			Optional<Doctor> checkUserName = doctorRepository.findByUserName(doctordto.getUserName());
			Doctor checkEmail = doctorRepository.findByEmail(doctordto.getEmail());
			if (checkUserName.isPresent()) {
				commonMsg.setMsgCode("exitUserName");
				return commonMsg;
			}
			if (checkEmail != null) {
				commonMsg.setMsgCode("exitEmail");
				return commonMsg;
			}
			// add new
			Doctor doctor = new Doctor();
			doctor.setUserName(doctordto.getUserName());
			doctor.setPassword(passwordEncoder.encode("123456"));
			doctor.setEmail(doctordto.getEmail());
			doctor.setFullname(doctordto.getFullname());
			doctor.setSpecialty(specialtyRepository.findByName(doctordto.getNameSpecialty()));
			doctor.setClinic(clinicRepository.findByName(doctordto.getNameClinic()));
			doctorRepository.save(doctor);
			commonMsg.setMsgCode("200");
		} else {
			// update
			Doctor doctorUpdate = getDoctorById(Integer.parseInt(doctordto.getId()));
			doctorUpdate.setFullname(doctordto.getFullname());
			doctorUpdate.setSpecialty(specialtyRepository.findByName(doctordto.getNameSpecialty()));
			doctorUpdate.setClinic(clinicRepository.findByName(doctordto.getNameClinic()));
			doctorRepository.save(doctorUpdate);
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

	@Override
	@PreAuthorize("hasRole('PATIENT')")
	public List<Doctor> getAllDoctorsByPatient() {
		return doctorRepository.findAll();
	}

	@Override
	public List<Doctor> getAllDoctorsBySpecialty(int specialtyId) {
		return doctorRepository.findBySpecialtyId(specialtyId);
	}

	@Override
	@PreAuthorize("#userId == principal.id")
	public User getUserById(int userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Override
	public User findById(int userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	@PreAuthorize("#passwordChangeForm.id == principal.id")
	public CommonMsg updateUserPassword(ChangePasswordForm passwordChangeForm) {
		CommonMsg commonMsg = new CommonMsg();
		User user = userRepository.findById(passwordChangeForm.getId()).get();
		if (passwordEncoder.matches(passwordChangeForm.getCurrentPassword(), user.getPassword()) == false) {
			commonMsg.setMsgCode("205");
		} else {
			user.setPassword(passwordEncoder.encode(passwordChangeForm.getPassword()));
			userRepository.save(user);
			commonMsg.setMsgCode("200");
		}
		return commonMsg;
	}

	// capitalize First Letter String
	private String capitalize(String name) {
		return StringUtils.capitalize(name.toLowerCase());
	}

	@Override
	public Doctor getDoctorById(int userId) {
		return doctorRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Override
	@PreAuthorize("#patientId == principal.id or hasRole('ADMIN')")
	public Patient getPatientById(int patientId) {
		return patientRepository.findById(patientId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
	}

	@Override
	public void savePasswordByUser(User user) {
		User userRE = userRepository.findByUserName(user.getUserName())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		userRE.setConfirmationToken(UUID.randomUUID().toString());
		userRE.setPassword(passwordEncoder.encode(user.getPassword()));
		userRE.setEnabled(true);
		userRepository.save(userRE);
	}

	@Override
	public void savePatientRegister(Patient userRE) {
		Patient patient = new Patient(userRE.getUserName(), passwordEncoder.encode(userRE.getPassword()),
				userRE.getConfirmationToken(), getRolesForPatient());
		patient.setEmail(userRE.getEmail());
		patient.setProfileImage("/img/avatar.png");
		userRepository.save(patient);
	}

	@Override
	public Collection<Role> getRolesForDoctor() {
		HashSet<Role> roles = new HashSet();
		roles.add(roleRepository.findByName("ROLE_DOCTOR"));
		return roles;
	}

	@Override
	public Collection<Role> getRolesForPatient() {
		HashSet<Role> roles = new HashSet();
		roles.add(roleRepository.findByName("ROLE_PATIENT"));
		return roles;
	}

	@Override
	public double getRatingByDoctorId(int doctorId) {
		Double rating = reviewRepository.getRatingByDoctorId(doctorId);
		return (rating == null) ? 0.0 : rating;
	}

	@Override
	public List<Review> getAllReviewByDoctorId(int doctorId) {
		return reviewRepository.getAllReviewByDoctorId(doctorId);
	}
	
	@Override
	public List<ReviewDto> getAllReview() {
		List<ReviewDto> listDTO = new ArrayList<>();
		List<Review> list = reviewRepository.findAll();
		for (Review r : list) {
			listDTO.add(new ReviewDto(r.getId().toString(), r.getFeedback(), r.getRating(), r.getPatient().getFullname(), r.getDoctor().getFullname()));
		}
		return listDTO;
	}
	
	@Override
	public CommonMsg deleteReview(int reviewId) {
		CommonMsg commonMsg = new CommonMsg();
		reviewRepository.deleteById(reviewId);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}

	@Override
	public List<History> getHistoryByPatientId(int patientId) {
		return historyRepository.findByPatientId(patientId);
	}

	@Override
	public void saveResultByDoctor(History history, MultipartFile[] files) {
		historyRepository.save(history);
		if (files != null) {
			try {
				List<FileModel> storedFile = new ArrayList<FileModel>();

				for (MultipartFile file : files) {
					FileModel fileModel = fileModelRepository.findByNameAndUserId(file.getOriginalFilename(),
							history.getPatient().getId());
					if (fileModel != null) {
						fileModel.setData(file.getBytes());
					} else {
						fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes(),
								history.getPatient(), history);
					}
					storedFile.add(fileModel);
				}

				fileModelRepository.saveAll(storedFile);
			} catch (Exception e) {
				System.err.print("Loi roi " + e.getMessage());
			}
		}
	}

	@Override
	public void saveCertificationByDoctor(MultipartFile file, int doctorId) {
		try {
			FileModel fileModel = fileModelRepository.findCertificationByUserId(doctorId);
			if (fileModel != null) {
				fileModelRepository.delete(fileModel);
			}
			fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes(),
					userRepository.findById(doctorId).get());
			fileModelRepository.save(fileModel);
		} catch (Exception e) {
			System.err.print("Loi roi " + e.getMessage());
		}
	}

	@Override
	public void saveImageProfileByUser(MultipartFile file, int id) {
		User user = findById(id);
		try {
			FileModel fileModel = fileModelRepository.findImageByUserId(id);
			if (fileModel != null) {
				fileModelRepository.delete(fileModel);
			}
			fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes(), user);
			fileModelRepository.save(fileModel);

			user.setProfileImage("/file/" + fileModel.getId());
			userRepository.save(user);
		} catch (Exception e) {
			System.err.print("Loi roi " + e.getMessage());
		}
	}

	@Override
	public FileModel getFileByFileId(int id) {
		return fileModelRepository.findById(id).get();
	}

	@Override
	public FileModel getCertificationByUserId(int userId) {
		FileModel file = fileModelRepository.findCertificationByUserId(userId);
		return file;
	}

	@Override
	public String getImageByUserId(int userId) {
		FileModel file = fileModelRepository.findImageByUserId(userId);
		return (file == null) ? "/img/avatar.png" : "/file/" + file.getId();
	}

	@Override
	public History getHistoryByAppointmentId(int id) {
		History history = historyRepository.getHistoryByAppointmentId(id);
		return (history == null) ? new History() : history;
	}

	@Override
	public Declaration getDeclarationByPatientId(int patientId) {
		Declaration declaration = declarationRepository.getDeclarationByPatientId(patientId);
		return (declaration == null) ? new Declaration() : declaration;
	}

	@Override
	public void saveDeclarationByPatientId(Declaration declaration) {
		Patient patient = declaration.getPatient();
		patient.setDeclaration(declaration);
//		patientRepository.save(declaration);
		declarationRepository.save(declaration);
	}

	@Override
	public void updateDeclarationByPatientId(Declaration declaration) {
		Declaration declarationUO = declarationRepository.findById(declaration.getId()).get();
		declarationUO.setBackground(declaration.getBackground());
		declarationUO.setBlood(declaration.getBlood());
		declarationUO.setMedicine(declaration.getMedicine());
		declarationUO.setNotes(declaration.getNotes());
		declarationUO.setSymptom(declaration.getSymptom());
		declarationRepository.save(declarationUO);
	}

	@Override
	public List<Specialty> getAllSpecialty() {
		return specialtyRepository.findAll();
	}

	@Override
	public List<Clinic> getAllClinic() {
		return clinicRepository.findAll();
	}

	@Override
	public CommonMsg saveClinic(Clinic clinic) {
		CommonMsg commonMsg = new CommonMsg();
		if (clinic.getId()==null) {
			Clinic checkName = clinicRepository.findByName(clinic.getName());
			if (checkName != null) {
				commonMsg.setMsgCode("exitName");
				return commonMsg;
			}
			// add new
			clinicRepository.save(clinic);
			commonMsg.setMsgCode("200");
		} else {
			// update
			Clinic clinicUO = clinicRepository.findById(clinic.getId()).get();
			clinicUO.setName(clinic.getName());
			clinicUO.setAddress(clinic.getAddress());
			clinicUO.setTelephone(clinic.getTelephone());
			clinicUO.setWebsite(clinic.getWebsite());
			clinicUO.setDescription(clinic.getDescription());
			clinicRepository.save(clinicUO);
			commonMsg.setMsgCode("205");
		}
		return commonMsg;
	}

	@Override
	public CommonMsg deleteClinic(int clinicId) {
		CommonMsg commonMsg = new CommonMsg();
		clinicRepository.deleteById(clinicId);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}

}
