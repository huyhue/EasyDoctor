package fpt.edu.vn.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

import fpt.edu.vn.component.DayPlan;
import fpt.edu.vn.component.TimePeroid;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalTime;

@TypeDefs(@TypeDef(name = "json", typeClass = JsonStringType.class))
@Entity
@Table(name = "working_plans")
public class WorkingPlan {

    @Id
    @Column(name = "id_doctor")
    private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "monday")
    private DayPlan monday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "tuesday")
    private DayPlan tuesday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "wednesday")
    private DayPlan wednesday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "thursday")
    private DayPlan thursday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "friday")
    private DayPlan friday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "saturday")
    private DayPlan saturday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "sunday")
    private DayPlan sunday;


    public WorkingPlan() {
    }

    public WorkingPlan(int id, Doctor doctor, DayPlan monday, DayPlan tuesday, DayPlan wednesday, DayPlan thursday,
			DayPlan friday, DayPlan saturday, DayPlan sunday) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayPlan getDay(String day) {
        switch (day) {
            case "monday":
                return monday;

            case "tuesday":
                return tuesday;

            case "wednesday":
                return wednesday;

            case "thursday":
                return thursday;

            case "friday":
                return friday;

            case "saturday":
                return saturday;

            case "sunday":
                return sunday;

            default:
                return null;
        }
    }

    public DayPlan getMonday() {
        return monday;
    }

    public void setMonday(DayPlan monday) {
        this.monday = monday;
    }

    public DayPlan getTuesday() {
        return tuesday;
    }

    public void setTuesday(DayPlan tuesday) {
        this.tuesday = tuesday;
    }

    public DayPlan getWednesday() {
        return wednesday;
    }

    public void setWednesday(DayPlan wednesday) {
        this.wednesday = wednesday;
    }

    public DayPlan getThursday() {
        return thursday;
    }

    public void setThursday(DayPlan thursday) {
        this.thursday = thursday;
    }

    public DayPlan getFriday() {
        return friday;
    }

    public void setFriday(DayPlan friday) {
        this.friday = friday;
    }

    public DayPlan getSaturday() {
        return saturday;
    }

    public void setSaturday(DayPlan saturday) {
        this.saturday = saturday;
    }

    public DayPlan getSunday() {
        return sunday;
    }

    public void setSunday(DayPlan sunday) {
        this.sunday = sunday;
    }

    public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public static WorkingPlan generateDefaultWorkingPlan(Doctor doctor) {
        LocalTime defaultStartHour = LocalTime.parse("06:00");
        LocalTime defaultEndHour = LocalTime.parse("18:00");
        TimePeroid defaultWorkingPeroid = new TimePeroid(defaultStartHour, defaultEndHour);
        DayPlan defaultDayPlan = new DayPlan(defaultWorkingPeroid);
        
        WorkingPlan wp = new WorkingPlan();
        wp.setId(doctor.getId());
        wp.setDoctor(doctor);
        wp.setMonday(defaultDayPlan);
        wp.setTuesday(defaultDayPlan);
        wp.setWednesday(defaultDayPlan);
        wp.setThursday(defaultDayPlan);
        wp.setFriday(defaultDayPlan);
        wp.setSaturday(defaultDayPlan);
        wp.setSunday(defaultDayPlan);
        return wp;
    }
}
