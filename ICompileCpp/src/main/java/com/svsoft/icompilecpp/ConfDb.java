package com.svsoft.icompilecpp;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Steve Vogel  
 */
public class ConfDb {
    
    /**
     * path of confDb.db file
     */
    private String path = null;    

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
        
    ConfDb()
    {
        path = System.getProperty("user.home")+File.separator;
        path += ".svsoft";
        
        // Create directory if not exist
        File dir = new File(path);
        if( dir.exists() == false )
            dir.mkdir();
        
        path += File.separator+"confdb.db";        
    }
    
    boolean initDb()
    {                 
        File f = new File(path);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            // @TODO logger
            //Logger.getLogger(ConfDb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS hosts (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    ip text NOT NULL,\n"
                + "    password text"
                + ");";
        
        try             
        {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path);
            Statement stmt = conn.createStatement();
            
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
        return true;
    }
}