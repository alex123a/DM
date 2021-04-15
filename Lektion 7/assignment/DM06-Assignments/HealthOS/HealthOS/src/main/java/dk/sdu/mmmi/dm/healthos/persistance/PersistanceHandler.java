package dk.sdu.mmmi.dm.healthos.persistance;

import dk.sdu.mmmi.dm.healthos.domain.Employee;
import dk.sdu.mmmi.dm.healthos.domain.Patient;
import dk.sdu.mmmi.dm.healthos.domain.Admission;
import dk.sdu.mmmi.dm.healthos.domain.Bed;
import dk.sdu.mmmi.dm.healthos.domain.IPersistanceHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oliver Nordestgaard | olnor18
 */
public class PersistanceHandler implements IPersistanceHandler{
    private static PersistanceHandler instance;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "healthDB";
    private String username = "postgres";
    private String password = "Blexsoftware3";
    private Connection connection = null;
    
    private PersistanceHandler(){
        initializePostgresqlDatabase();
    }

    public static PersistanceHandler getInstance(){
        if (instance == null) {
            instance = new PersistanceHandler();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees");
            ResultSet sqlReturnValues = stmt.executeQuery();
            int rowcount = 0;
            List<Employee> returnValue = new ArrayList<>();
            while (sqlReturnValues.next()){
                returnValue.add(new Employee(sqlReturnValues.getInt(1), sqlReturnValues.getString(2), sqlReturnValues.getInt(3), sqlReturnValues.getInt(4), sqlReturnValues.getInt(5), sqlReturnValues.getInt(6)));
            }
            return returnValue;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee getEmployee(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()){
               return null;
            }
            return new Employee(sqlReturnValues.getInt(1), sqlReturnValues.getString(2), sqlReturnValues.getInt(3), sqlReturnValues.getInt(4), sqlReturnValues.getInt(5), sqlReturnValues.getInt(6));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }    
    }

    /*
    Implement all of the following. Beware that the model classes are as of yet not properly implemented
    */

    @Override
    public boolean createEmployee(Employee employee) {
        //make HealthOS support this action in the presentation layer too.

        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO employees (id, name, phone, position_id, department_id, room_id) VALUES (?, ?, ?, ?, ?, ?)"
            );
            insertStatement.setInt(1, employee.getId());
            insertStatement.setString(2, employee.getName());
            insertStatement.setInt(3, employee.getPhone());
            insertStatement.setInt(4, employee.getPosition_id());
            insertStatement.setInt(5, employee.getDepartment_id());
            insertStatement.setInt(6, employee.getRoom_id());
            insertStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Patient> getPatients() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM patients");
            ResultSet sqlReturnValues = stmt.executeQuery();
            List<Patient> returnValue = new ArrayList<>();
            while (sqlReturnValues.next()){
                returnValue.add(new Patient(sqlReturnValues.getInt(1), sqlReturnValues.getString(2), sqlReturnValues.getString(3), sqlReturnValues.getString(4)));
            }
            return returnValue;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient getPatient(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM patients WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()){
                return null;
            }
            return new Patient(sqlReturnValues.getInt(1), sqlReturnValues.getString(2), sqlReturnValues.getString(3), sqlReturnValues.getString(4));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createPatient(Patient patient) {
        //make HealthOS support this action in the presentation layer too.
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO patients (id, name, phone, cpr_number) VALUES (?, ?, ?, ?)"
            );
            insertStatement.setInt(1, patient.getId());
            insertStatement.setString(2, patient.getName());
            insertStatement.setString(3, patient.getPhone());
            insertStatement.setString(4, patient.getCpr_number());
            insertStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Bed> getBeds() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM beds");
            List<Bed> list = new ArrayList<>();
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                list.add(new Bed(rset.getInt(1), rset.getString(2)));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Bed getBed(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM beds WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rset = stmt.executeQuery();
            if (!rset.next()) {
                return null;
            }
            return new Bed(rset.getInt(1), rset.getString(2));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createBed(Bed bed) {
        //make HealthOS support this action in the presentation layer too.
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO beds (id, bed_number) VALUES (?, ?)");
            stmt.setInt(1, bed.getId());
            stmt.setString(2, bed.getBed_number());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Admission> getAdmissions() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM admissions");
            List<Admission> list = new ArrayList<>();
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                list.add(new Admission(rset.getInt(1),
                        rset.getInt(2), rset.getInt(3),
                        rset.getInt(4), rset.getInt(5)));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admission getAdmission(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM admissions WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rset = stmt.executeQuery();
            if (!rset.next()) {
                return null;
            }
            return new Admission(rset.getInt(1),
                    rset.getInt(2), rset.getInt(3),
                    rset.getInt(4), rset.getInt(5));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createAdmission(Admission admission) {
        //make HealthOS support this action in the presentation layer too.
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO admissions(id, patient_id, room_id, bed_id, assigned_employee_id) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, admission.getId());
            stmt.setInt(2, admission.getPatient_id());
            stmt.setInt(3, admission.getRoom_id());
            stmt.setInt(4, admission.getBed_ind());
            stmt.setInt(5, admission.getAssigned_employee_id());
            stmt.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAdmission(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM admissions WHERE id = ?");
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
