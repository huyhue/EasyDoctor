package fpt.edu.vn.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.Review;
import fpt.edu.vn.model.Role;
import fpt.edu.vn.model.User;
import fpt.edu.vn.repository.DoctorRepository;
import fpt.edu.vn.repository.PatientRepository;
import fpt.edu.vn.repository.ReviewRepository;
import fpt.edu.vn.repository.RoleRepository;
import fpt.edu.vn.repository.UserRepository;
import fpt.edu.vn.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final RoleRepository roleRepository;
	private final ReviewRepository reviewRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, DoctorRepository doctorRepository,
			PatientRepository patientRepository, RoleRepository roleRepository, ReviewRepository reviewRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.roleRepository = roleRepository;
		this.reviewRepository = reviewRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
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
		d.setCertification(doctor.getCertification());
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
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	@Override
	@PreAuthorize("hasRole('PATIENT')")
	public List<Doctor> getAllDoctorsByPatient() {
		return doctorRepository.findAll();
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
	public void updateUserPassword(ChangePasswordForm passwordChangeForm) {
		User user = userRepository.findById(passwordChangeForm.getId()).get();
		user.setPassword(passwordEncoder.encode(passwordChangeForm.getPassword()));
		userRepository.save(user);
	}

	@Override
	public void updateImage(int id, String fileImage) {
		User user = userRepository.findById(id).get();
		user.setProfileImage(fileImage);
		userRepository.save(user);
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
	
}
