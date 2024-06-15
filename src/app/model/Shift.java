package app.model;

import java.time.LocalDateTime;

public class Shift {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime fechaHora;

    public Shift(int id, Doctor doctor, Patient patient, LocalDateTime fechaHora) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.fechaHora = fechaHora;
    }

    public Shift() {
    }


}
