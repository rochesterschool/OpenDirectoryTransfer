/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maguilera
 */

import Utils.FileCreator;
import java.sql.SQLException;
import java.text.ParseException;

public class main {
    
    public static void main(String[] args) throws SQLException, ParseException {
        
        FileCreator fileCreator = new FileCreator();
        fileCreator.FileGenerator();
        
    }
    
}
