package org.example.app;

import java.sql.*;

public class jdbc {
    private static final String url="jdbc:mysql://localhost:3306/banking";
    public static final  String username="root";
    public static final String password="Example@2006#";

    private static void getdata(Statement stmt) throws SQLException {

        String sql="select * from personal_info";
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            System.out.printf(" id : %d\t",rs.getInt("id"));
            System.out.printf(" name : %s\t",rs.getString("name"));
            System.out.printf(" age : %d\t",rs.getInt("age"));
            System.out.printf(" class : %d\n",rs.getInt("class"));
        }

    }

    private static void insertdata(Statement stmt, int id, String name, int age, int cls) throws SQLException {
        String sql= String.format("insert into student values(%d, '%s', %d, %d)",id,name,age,cls);

        int numRow = stmt.executeUpdate(sql);
        if(numRow>0){
            System.out.println("inserted successfully");
        }
        else{
            System.out.println("insert failed");
        }
    }

    private static void updatedata(Statement stmt, int id, int age) throws SQLException {
        String sql= String.format("UPDATE student set age = %d where id = %d ",age, id);

        int numRow = stmt.executeUpdate(sql);
        if(numRow>0){
            System.out.println("updated successfully");
        }
        else{
            System.out.println("update failed");
        }
    }

    public static void databaseConnection() {
        try {
            //==================== step - 1 ===============================
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            //==================== step - 2 ===============================
            Connection conn= DriverManager.getConnection(url,username,password);
            //==================== step - 3 ===============================
            Statement stmt=conn.createStatement();

            //==================== step - 4 ===============================

            //  getdata(stmt);
            // insertdata(stmt,5, "anita", 34, 14);

            // updatedata(stmt,2, 20);

            //==================================================================================================
            // better way for multiple qry
            String sql="insert into student values(?, ?, ?, ?)";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,6);
            pstmt.setString(2,"mohan");
            pstmt.setInt(3,25);
            pstmt.setInt(4,15);
            int  numRow=pstmt.executeUpdate();
            if(numRow>0){
                System.out.println("inserted successfully");
            }
            else{
                System.out.println("insert failed");
            }



        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
