package HospitalMangementsystem;

import java.sql.*;
import java.util.Scanner;

public class Patients {

    private Connection connection;
    private Scanner scanner;

    public Patients(Connection connection, Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatients(){
        System.out.println("enter patient Name");
        String name=scanner.next();
        System.out.println("enter patient gender");
        String gender=scanner.next();
        System.out.println("enter patient age");
        int age=scanner.nextInt();

        try{
            String query="insert into patients (name,age,gender) values (?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(query);

            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("patient data added successfully");
            }else{
                System.out.println("failed to add patient");
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
    }

    public void viewPatients(){
        String query="select * from patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
//            int affectedRows=preparedStatement.executeUpdate();
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("patients: ");
            System.out.println("+--------+--------------------+--------------------+-------------------+");
            System.out.println();
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender= resultSet.getString("gender");
                System.out.println(id+"  | "+name+"|"+ age+" |"+gender);

            }



        }catch (    SQLException e){
            e.printStackTrace();
        }
    }


    public boolean getPatientsById(int id){
        String query="select * from patients where id= ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            System.out.println("enter the patient id");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
//            while(resultSet.next()){
//                int idp=resultSet.getInt("id");
//                String name=resultSet.getString("name");
//                int age=resultSet.getInt("age");
//                String gender= resultSet.getString("gender");
//                System.out.println(idp+"  | "+name+"|"+ age+" |"+gender);
//
//            }

            if(resultSet.next()){
                return true;
            }else{
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
