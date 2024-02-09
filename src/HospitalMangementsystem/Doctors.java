package HospitalMangementsystem;

import java.sql.*;
import java.util.Scanner;

public class Doctors {

    private Connection connection;
//    private Scanner scanner;

    public Doctors(Connection connection){
        this.connection=connection;
//        this.scanner=scanner;
    }


    public void viewDoctors(){
        String query="select * from doctors";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
//            int affectedRows=preparedStatement.executeUpdate();
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+--------+--------------------+--------------------+-------------------+");
            System.out.println();
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");

                String specialization= resultSet.getString("specialization");
                System.out.println(id+"  | "+name+"|"+" |"+specialization);

            }



        }catch (    SQLException e){
            e.printStackTrace();
        }
    }


    public boolean getDoctorsById(int id){
        String query="select * from doctors where id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            System.out.println("enter the doctor id");
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
