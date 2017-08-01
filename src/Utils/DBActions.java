package Utils;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBActions {

    public static String mysq_conx     =  "jdbc:mysql://10.0.3.8:3306/aprendoz_desarrollo";
    public static String mysq_user     =  "root";
    public static String mysq_pass     =  "irc4Quag";

    public int getIdPersonaByCode(String code) throws SQLException {
        int id_unique_persona = 0;
        Connection con = (Connection) DriverManager.getConnection(mysq_conx, mysq_user, mysq_pass);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Id_Persona FROM PERSONA WHERE Codigo = '"+code+"'");

            while (rs.next()) {
                int id_persona = rs.getInt("Id_Persona");
                System.out.println("--> Id persona encontrado: " + id_persona);
                id_unique_persona = id_persona;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.close();
        }
        return id_unique_persona;
    }

    public void insertDataPeopleFamiliarGroup(int id_persona, int id_grupofamiliar, int tipo_persona, int responsable) throws SQLException{
        Connection con = (Connection) DriverManager.getConnection(mysq_conx, mysq_user, mysq_pass);
        con.setAutoCommit(false);
        PreparedStatement pstm = null;

        try {
            String sql = "INSERT INTO insc_persona_grupo_familiar ("+
                    "Persona_Id_Persona," +
                    "Grupo_familiar_Id_Grupo_Familiar," +
                    "Tipo_Persona_Id_Tipo_Persona," +
                    "responsable)" +
                    "VALUES(?, ?, ?, ? )";

            pstm = (PreparedStatement) con.prepareStatement(sql);
            pstm.setInt(1, id_persona);
            pstm.setInt(2, id_grupofamiliar);
            pstm.setInt(3, tipo_persona);
            pstm.setInt(4, responsable);

            pstm.execute();
            con.commit();
            pstm.close();
            con.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.close();
        }
    }

    public void insertStudentCosts(int id_persona, String grade, int sy) throws SQLException {
        
        Connection con = (Connection) DriverManager.getConnection(mysq_conx, mysq_user, mysq_pass);
        con.setAutoCommit(false);
        PreparedStatement pstm = null;
        
        int idCurso   = 0;
        System.out.println("El grado a insertar: "+grade);

        if(grade.equals("Prekindergarten | Prejardín")){
            idCurso = 10001;
        }else if(grade.equals("Kindergarten | Jardín")){
            idCurso = 10002;
        }else if(grade.equals("1st Grade | Transición")){
            idCurso = 10003;
        }else if(grade.equals("2nd Grade | Primero")){
            idCurso = 10004;
        }else if(grade.equals("3rd Grade | Segundo")){
            idCurso = 10005;
        }else if(grade.equals("4th Grade | Tercero")){
            idCurso = 10006;
        }else if(grade.equals("5th Grade | Cuarto")){
            idCurso = 10007;
        }else if(grade.equals("6th Grade | Quinto")){
            idCurso = 10008;
        }else if(grade.equals("7th Grade | Sexto")){
            idCurso = 10009;
        }else if(grade.equals("8th Grade | Séptimo")){
            idCurso = 10010;
        }else if(grade.equals("9th Grade | Octavo")){
            idCurso = 10011;
        }else if(grade.equals("10th Grade | Noveno")){
            idCurso = 10012;
        }else if(grade.equals("11th Grade | Decimo")){
            idCurso = 10013;
        }else if(grade.equals("12th Grade | Undecimo")){
            idCurso = 10014;
        }

        System.out.println(" Salida: El grado: "+idCurso);
        System.out.println("El año escolar "+sy);
        int idSy   = sy-2009;
        System.out.println(" Salida: El año escolar "+idSy);
        try {
            String sql = "INSERT INTO insc_alum_curso ("+
                    "Curso_Id_Curso," +
                    "Persona_Id_Persona," +
                    "Sy_Id_Sy," +
                    "Calificacion," +
                    "calif_char," +
                    "fecha_matricula) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";

            pstm = (PreparedStatement) con.prepareStatement(sql);
            pstm.setInt(1, idCurso);
            pstm.setInt(2, id_persona);
            pstm.setInt(3, idSy);
            pstm.setDouble(4, 0.0);
            pstm.setString(5, null);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
            java.util.Date dateStr = formatter.parse("2017-01-01");
            java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
            pstm.setDate(6, null);
            System.out.println("-----> "+ sql +" --- "+pstm);

            pstm.execute();
            con.commit();
            pstm.close();
            con.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.close();
        }
    }
}
