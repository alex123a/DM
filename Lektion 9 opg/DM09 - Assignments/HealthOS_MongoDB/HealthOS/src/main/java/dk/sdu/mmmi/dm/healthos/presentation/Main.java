package dk.sdu.mmmi.dm.healthos.presentation;

import dk.sdu.mmmi.dm.healthos.domain.*;
import dk.sdu.mmmi.dm.healthos.persistance.PersistanceHandler;
import org.bson.types.ObjectId;

import java.util.Scanner;

/**
 * @author Oliver Nordestgaard | olnor18
 */

public class Main {
    public static void main(String[] args) {
        System.out.println(
                "------------------------------------------\n"
                        + "WELCOME TO HealthOS\n"
                        + "Please input your command or type \"help\"\n"
                        + "------------------------------------------\n"
        );
        boolean running = true;
        IPersistanceHandler persistanceHandler = PersistanceHandler.getInstance();
        try (Scanner s = new Scanner(System.in)) {
            while (running) {
                switch (s.nextLine().toLowerCase()) {
                    case "getemployees":
                        for (Employee employee : persistanceHandler.getEmployees()) {
                            System.out.println(employee);
                        }
                        break;
                    case "getemployee":
                        System.out.println("What is the employee ID?");
                        System.out.println(persistanceHandler.getEmployee(new ObjectId(s.nextLine())));
                        break;
                    case "createemployee":
                        String emp = Employee.class.getSimpleName().toLowerCase();
                        Employee employee = new Employee();
                        generateMessage("name", emp);
                        employee.setName(s.nextLine());
                        generateMessage("phone", emp);
                        employee.setPhone(Integer.parseInt(s.nextLine()));
                        generateMessage("position_id", emp);
                        employee.setPosition_id(Integer.parseInt(s.nextLine()));
                        generateMessage("department_id", emp);
                        employee.setDepartment_id(Integer.parseInt(s.nextLine()));
                        generateMessage("room_id", emp);
                        employee.setRoom_id(Integer.parseInt(s.nextLine()));
                        persistanceHandler.createEmployee(employee);
                        break;
                    case "getpatients":
                        System.out.println(persistanceHandler.getPatients());
                        break;
                    case "getpatient":
                        System.out.println("What is the patient ID?");
                        System.out.println(persistanceHandler.getPatient(s.nextInt()));
                        break;
                    case "createpatient":
                        String pat = Patient.class.getSimpleName().toLowerCase();
                        Patient patient = new Patient();
                        generateMessage("id", pat);
                        patient.setId(Integer.parseInt(s.nextLine()));
                        generateMessage("name", pat);
                        patient.setName(s.nextLine());
                        generateMessage("phone", pat);
                        patient.setPhone(s.nextLine());
                        generateMessage("cpr", pat);
                        patient.setCpr(s.nextLine());
                        persistanceHandler.createPatient(patient);
                        break;
                    case "getbeds":
                        System.out.println(persistanceHandler.getBeds());
                        break;
                    case "getbed":
                        System.out.println("What is the bed ID?");
                        System.out.println(persistanceHandler.getBed(s.nextInt()));
                        break;
                    case "createbed":
                        String bed = Bed.class.getSimpleName().toLowerCase();
                        Bed bedObj = new Bed();
                        generateMessage("id", bed);
                        bedObj.setId(Integer.parseInt(s.nextLine()));
                        generateMessage("bed number", bed);
                        bedObj.setBed_number(s.nextLine());
                        persistanceHandler.createBed(bedObj);
                        break;
                    case "getadmissions":
                        System.out.println(persistanceHandler.getAdmissions());
                        break;
                    case "getadmission":
                        System.out.println("What is the admission ID?");
                        System.out.println(persistanceHandler.getAdmission(s.nextInt()));
                        break;
                    case "createadmission":
                        String adm = Admission.class.getSimpleName().toLowerCase();
                        Admission admission = new Admission();
                        generateMessage("id", adm);
                        admission.setId(Integer.parseInt(s.nextLine()));
                        generateMessage("patient id", adm);
                        admission.setPatient_id(Integer.parseInt(s.nextLine()));
                        generateMessage("room id", adm);
                        admission.setRoom_id(Integer.parseInt(s.nextLine()));
                        generateMessage("bed id", adm);
                        admission.setBed_id(Integer.parseInt(s.nextLine()));
                        generateMessage("assigned employee id", adm);
                        admission.setAssigned_employee_id(Integer.parseInt(s.nextLine()));
                        persistanceHandler.createAdmission(admission);
                        break;
                    case "deleteadmission":
                        System.out.println("What is the admission ID?");
                        System.out.println(persistanceHandler.deleteAdmission(s.nextInt()));
                        break;
                    case "exit":
                        running = false;
                        break;
                    case "help":
                    default:
                        System.out.println(generateHelpString());
                        break;
                }
            }
        }
    }

    private static String generateHelpString() {
        return "Please write one of the following commands:\n"
                + "- getEmployees\n"
                + "- getEmployee\n"
                + "- createEmployee\n"
                + "- getPatients\n"
                + "- getPatient\n"
                + "- createPatient\n"
                + "- getBeds\n"
                + "- getBed\n"
                + "- createBed\n"
                + "- getAdmissions\n"
                + "- getAdmission\n"
                + "- createAdmission\n"
                + "- deleteAdmission\n"
                + "- exit\n";
    }

    private static void generateMessage(String parameter, String type) {
        System.out.println(String.format("Please enter the %s of the %s...", parameter, type));
    }
}
