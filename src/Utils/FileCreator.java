package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.mysql.jdbc.Driver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
//import com.mysql.jdbc.Statement;
//import java.sql.Date;
import java.util.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

public class FileCreator {   
    
    public void FileGenerator() throws SQLException{
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://10.0.3.8:3306/aprendoz_desarrollo", "root", "irc4Quag");
        List data = new ArrayList();
        List logdata = new ArrayList();
        
        try{
            Statement stmt = null;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT \n" +
                                                "	CONCAT(vista_alumnos_activos_201718.Nombre1, \" \", vista_alumnos_activos_201718.Nombre2) as names,\n" +
                                                "	CONCAT(vista_alumnos_activos_201718.Apellido1, \" \", vista_alumnos_activos_201718.Apellido2) as lastname,\n" +
                                                "	CONCAT(\"\",vista_alumnos_activos_201718.Codigo) as code,\n" +
                                                "	vista_alumnos_activos_201718.No_Documento as no_dni,\n" +
                                                "	vista_alumnos_activos_201718.email as email,\n" +
                                                "	m.fecha_matricula as enroll_date,\n" +
                                                "	vista_alumnos_activos_201718.Grado as grade,\n" +
                                                "	vista_alumnos_activos_201718.School_year as sy,\n" +
                                                "	CONCAT(CURDATE(), \"_\", CURTIME()) as today\n" +
                                                "	\n" +
                                                "FROM insc_persona_grupo_familiar \n" +
                                                "INNER JOIN vista_alumnos_activos_201718 ON insc_persona_grupo_familiar.Persona_Id_Persona = vista_alumnos_activos_201718.Id_Persona\n" +
                                                "LEFT JOIN matricula m ON m.Persona_Id_Persona = vista_alumnos_activos_201718.Id_Persona\n" +
                                                "\n" +
                                                "WHERE vista_alumnos_activos_201718.Id_Curso >= 10006 and vista_alumnos_activos_201718.Id_Curso <= 10014 AND m.SY_Id_SY = 8 #AND m.fecha_matricula = CURDATE()\n" +
                                                "GROUP BY vista_alumnos_activos_201718.Id_Grado, vista_alumnos_activos_201718.Id_Persona");
                
            while (rs.next()) {
                            
                            String logdate = dateFileName();
                            
                            int rows                = rs.getRow();
                            String names            = rs.getString("names");
                            String lastname         = rs.getString("lastname");
                            String code             = rs.getString("code");
                            String no_dni           = rs.getString("no_dni");
                            String email            = rs.getString("email");
                            String fecha_matricula  = rs.getString("enroll_date");
                            String grado            = rs.getString("grade");
                            String sy               = rs.getString("sy");
                            String today            = rs.getString("today");
                            
                            System.out.print(rows+"->");
                            System.out.println(names+ "," + 
                                     lastname +","+ 
                                     code +","+ 
                                     no_dni +","+ 
                                     email +","+ 
                                     fecha_matricula +","+
                                     grado +","+
                                     sy +","+
                                     today);
                            data.add(names+ "," + 
                                     lastname +","+ 
                                     code +","+ 
                                     no_dni +","+ 
                                     email +","+ 
                                     fecha_matricula +","+
                                     grado +","+
                                     sy +","+
                                     today);
                            logdata.add(names+ "," + 
                                     lastname +","+ 
                                     code +","+ 
                                     no_dni +","+ 
                                     email +","+ 
                                     fecha_matricula +","+
                                     grado +","+
                                     sy +","+
                                     today);
            }
            
            String file_date = dateFileName();
            //writeToFile(data, "/Volumes/sapiens/INTERFASE/PSE_FILES/PSE_FILE_"+file_date+".txt");
            writeToFile(data, "/Volumes/Automations/bin/OpenDirectoryDatatransfer/DataOpenDirectory_"+file_date+".csv");
            //mailFile(data, "/Volumes/sapiens/INTERFASE/PSE_FILES/PSE_FILE_MAIL.txt");
            //logFile(logdata, "/Volumes/sapiens/INTERFASE/PSE_FILES/logs/pse_log_"+file_date+".txt");
                
            System.out.println("Extrayendo información de la base de datos...");
            
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
         }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
         }finally{
            con.close();
        }   
        System.out.println("Success: UPDATE mensualidad in table ***importacion_cartera***"); 
    }
    
    private static void writeToFile(java.util.List list, String path) {
            BufferedWriter out = null;
            try {
                    File file = new File(path);
                    out = new BufferedWriter(new FileWriter(file, true));
                    for (Object s : list) {
                            out.write((String) s);
                            out.newLine();
                    }
                    out.close();
            } catch (IOException e) {
            }
    } 
    
    private static void mailFile(java.util.List list, String path){
            BufferedWriter out = null;
            try {
                    File file = new File(path);
                    out = new BufferedWriter(new FileWriter(file, true));
                    for (Object s : list) {
                            out.write((String) s);
                            out.newLine();
                    }
                    out.close();
            } catch (IOException e) {
            }
    }
    
    private static void logFile(java.util.List list, String path){
            BufferedWriter out = null;
            try {
                    File file = new File(path);
                    out = new BufferedWriter(new FileWriter(file, true));
                    for (Object s : list) {
                            out.write((String) s);
                            out.newLine();
                    }
                    out.close();
            } catch (IOException e) {
            }
    }
    
    private static String dateFileName(){
        Date today = new Date();
        Format formatter = new SimpleDateFormat("yyyyMMdd_HHmm");
        String s = formatter.format(today);
        
        return s;
    } 
    
    public void deleteFileTxt(){
        
        try{
    		File file = new File("/Volumes/sapiens/INTERFASE/PSE_FILES/PSE_FILE_MAIL.txt");
                
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}