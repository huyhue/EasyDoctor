package fpt.edu.vn.component;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AppointmentRegisterForm {

    private int packagesId;
    private int doctorId;
    private int patientId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;

    public AppointmentRegisterForm() {
    }

    public AppointmentRegisterForm(int packagesId, int doctorId, LocalDateTime start, LocalDateTime end) {
        this.packagesId = packagesId;
        this.doctorId = doctorId;
        this.start = start;
        this.end = end;
    }

    public int getPackagesId() {
		return packagesId;
	}

	public void setPackagesId(int packagesId) {
		this.packagesId = packagesId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
