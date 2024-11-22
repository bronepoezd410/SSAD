package Laba1;
import java.util.ArrayList;
import java.util.Objects;

class Patient {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String address;
    private String phone;
    private int medicalCardNumber;
    private String diagnosis;

    public Patient(int id, String lastName, String firstName, String middleName, String address, String phone, int medicalCardNumber, String diagnosis) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.phone = phone;
        this.medicalCardNumber = medicalCardNumber;
        this.diagnosis = diagnosis;
    }
//    public Patient(int id, String lastName, String firstName, int medicalCardNumber, String diagnosis) {
//        this(id, lastName, firstName, "", "", "", medicalCardNumber, diagnosis);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMedicalCardNumber() {
        return medicalCardNumber;
    }

    public void setMedicalCardNumber(int medicalCardNumber) {
        this.medicalCardNumber = medicalCardNumber;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", medicalCardNumber=" + medicalCardNumber +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, medicalCardNumber, diagnosis);
    }
}

class PatientManager {
    private ArrayList<Patient> patients;
    public PatientManager() {
        this.patients = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public ArrayList<Patient> findPatientsByDiagnosis(String diagnosis) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getDiagnosis().equalsIgnoreCase(diagnosis)) {
                result.add(patient);
            }
        }
        return result;
    }

    public ArrayList<Patient> findPatientsByCardRange(int start, int end) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getMedicalCardNumber() >= start && patient.getMedicalCardNumber() <= end) {
                result.add(patient);
            }
        }
        return result;
    }

    public void displayPatients(ArrayList<Patient> patients) {
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        PatientManager manager = new PatientManager();

        manager.addPatient(new Patient(1, "Шагин", "Виталий", "Линуксович", "Симферополь", "+7978", 101, "Простуда"));
        manager.addPatient(new Patient(2, "Османов", "Эдем", "Бекирович", "Симферополь", "+7978", 102, "Простуда"));
        manager.addPatient(new Patient(3, "Ромашкан", "Данил", "Алексеевич", "Симферополь", "+7978", 103, "Грипп"));
        manager.addPatient(new Patient(4, "Авагян", "Давид", "Грагатович", "Севастополь", "+7978", 104, "Грипп"));

        System.out.println("Пациенты с 'Простуда':");
        manager.displayPatients(manager.findPatientsByDiagnosis("Простуда"));

        System.out.println("\nПациенты с медкартой [102, 104]:");
        manager.displayPatients(manager.findPatientsByCardRange(102, 104));
    }
}

// toString(): Формирует читаемую строку с полной информацией о пациенте.
// hashCode(): Позволяет сравнивать объекты на основе их полей (например, при использовании в коллекциях).