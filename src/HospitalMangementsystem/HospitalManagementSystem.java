package HospitalMangementsystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="mysql";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
Scanner sc=new Scanner(System.in);

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patients patients=new Patients(connection,sc);
            Doctors doctors=new Doctors(connection);
            while(true){
                System.out.println("hospital management system");

                System.out.println("1, add Patient");
                System.out.println("2, view Patient");
                System.out.println("3, view Doctors");
                System.out.println("4, book Doctor appointment");
                System.out.println("5, exit");
                System.out.println("enter your choice");
                int choice=sc.nextInt();
                switch (choice){
                    case 1:
                        patients.addPatients();
                        System.out.println();
                        break;
                    case 2:
                        patients.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctors.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
bookAppointment(patients,doctors,connection,sc);
                        System.out.println();
                        break;
                    case 5:return;
                    default:
                        System.out.println("enter valid choice");
                        break;


                }
            }


        }catch (SQLException E){
            E.printStackTrace();
        }
    }
    public static void bookAppointment(Patients patient, Doctors doctor, Connection connection,Scanner scanner){
        System.out.println("enter patient id");
        int patientid=scanner.nextInt();
        System.out.println("enter doctor id");

        int doctorid=scanner.nextInt();
        System.out.println("enter date in format yyyy mm dd");
         String Datey=scanner.next();
         if(patient.getPatientsById(patientid) && doctor.getDoctorsById(doctorid)){
             if(checkDoctorAvailability(doctorid,Datey,connection)){
                 String appointQuery="INSERT INTO appointments(patient_id, doctor_id, appointment_date) values(?,?,?)";
                 try {
                     PreparedStatement preparedStatement=connection.prepareStatement(appointQuery);
                     preparedStatement.setInt(1,patientid);
                     preparedStatement.setInt(2,doctorid);
                     preparedStatement.setString(3,Datey);

                     int rowsAffected=preparedStatement.executeUpdate();
                     if(rowsAffected>0){
                         System.out.println("appointment booked");
                     }else{
                         System.out.println("some error occured");
                     }
                 }catch (SQLException E){
                     E.printStackTrace();
                 }
             }else{
                 System.out.println("doctor not available on this date");
             }
         }else{
             System.out.println("either doctor or patient does'nt exist");
         }


    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate,Connection connection){
        String query="SELECT COUNT(*) FROM appointments where doctor_id = ? AND appointment_date= ? ";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int count=resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (SQLException E){
            E.printStackTrace();
        }
       return false;

    }


}
