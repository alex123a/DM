package dk.sdu.mmmi.dm.healthos.presentation;

import dk.sdu.mmmi.dm.healthos.domain.*;
import dk.sdu.mmmi.dm.healthos.persistance.PersistanceHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
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
        try(Scanner s = new Scanner(System.in)){
            while(running) {
                switch(s.nextLine().toLowerCase()) {
                    case "getemployees":
                        System.out.println(persistanceHandler.getEmployees());
                        break;
                    case "getemployee":
                        System.out.println("What is the employee ID?");
                        System.out.println(persistanceHandler.getEmployee(Integer.parseInt(s.nextLine())));
                        break;
                    case "getpatients":
                        System.out.println(persistanceHandler.getPatients());
                        break;
                    case "getpatient":
                        System.out.println("What is the patient ID?");
                        System.out.println(persistanceHandler.getPatient(Integer.parseInt(s.nextLine())));
                        break;
                    case "getbeds":
                        System.out.println(persistanceHandler.getBeds());
                        break;
                    case "getbed":
                        System.out.println("What is the bed ID?");
                        System.out.println(persistanceHandler.getBed(Integer.parseInt(s.nextLine())));
                        break;
                    case "getadmissions":
                        System.out.println(persistanceHandler.getAdmissions());
                        break;
                    case "getadmission":
                        System.out.println("What is the admission ID?");
                        System.out.println(persistanceHandler.getAdmission(Integer.parseInt(s.nextLine())));
                        break;
                    case "createemployee":
                        System.out.println("Employee created: " + createemp(persistanceHandler, s));
                        break;
                    case "createpatient":
                        System.out.println("Patient created: " + createpat(persistanceHandler, s));
                        break;
                    case "createbed":
                        System.out.println("Bed created: " + createbed(persistanceHandler, s));
                        break;
                    case "createadmission":
                        System.out.println("Admission created: " + createadmission(persistanceHandler, s));
                        break;
                    case "deleteadmission":
                        System.out.print("Which admission do you want to delete? Enter id > ");
                        System.out.println(persistanceHandler.deleteAdmission(Integer.parseInt(s.nextLine())));
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
                + "- getPatients\n"
                + "- getPatient\n"
                + "- getBeds\n"
                + "- getBed\n"
                + "- getAdmissions\n"
                + "- getAdmission\n"
                + "- exit\n";
    }

    private static boolean createemp(IPersistanceHandler persistanceHandler, Scanner scanner) {
        System.out.print("Enter employee id > ");
        int id = scanner.nextInt();
        System.out.print("Enter employee name and lastname > ");
        String name = scanner.next();
        name = name + " " + scanner.next();
        System.out.print("Enter employee phone > ");
        int phone = scanner.nextInt();
        System.out.print("Enter employee position id > ");
        int position = scanner.nextInt();
        System.out.print("Enter employee department id > ");
        int department = scanner.nextInt();
        System.out.print("Enter employee room id > ");
        int room = scanner.nextInt();
        return persistanceHandler.createEmployee(new Employee(id, name, phone, position, department, room));
    }

    private static boolean createpat(IPersistanceHandler persistanceHandler, Scanner scanner) {
        System.out.print("Enter patient id > ");
        int id = scanner.nextInt();
        System.out.print("Enter patient name > ");
        String name = scanner.next();
        System.out.print("Enter patient phone > ");
        String phone = scanner.next();
        System.out.print("Enter patient cpr_number > ");
        String cpr_number = scanner.next();
        return persistanceHandler.createPatient(new Patient(id, name, phone, cpr_number));
    }

    private static boolean createbed(IPersistanceHandler persistanceHandler, Scanner scanner) {
        System.out.print("Enter bed id > ");
        int id = scanner.nextInt();
        System.out.print("Enter bed number > ");
        String bed_number = scanner.next();
        return persistanceHandler.createBed(new Bed(id, bed_number));
    }

    private static boolean createadmission(IPersistanceHandler persistanceHandler, Scanner scanner) {
        System.out.print("Enter admission id > ");
        int id = scanner.nextInt();
        System.out.print("Enter patient id > ");
        int patient = scanner.nextInt();
        System.out.print("Enter room id > ");
        int room = scanner.nextInt();
        System.out.print("Enter bed id > ");
        int bed = scanner.nextInt();
        System.out.print("Enter employee id > ");
        int employee = scanner.nextInt();
        return persistanceHandler.createAdmission(new Admission(id, patient, room, bed, employee));
    }
}
