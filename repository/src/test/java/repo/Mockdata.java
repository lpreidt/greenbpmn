package repo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import lombok.Data;

public class Mockdata {

    /*
     * "id": "Process_0vjv4zt",
     * "childProcess": "Process_1kndcr1;",
     * "startKnoten": "StartEvent_1;Event_1po129z;",
     * "endKnoten": "Event_1a8blh9;",
     * "energySumYear": 450,
     * "processType": "CORE",
     * "department": "Einkauf",
     * "processDescription": "Dies ist eine Prozessbeschreibung\n
     * private static final String DB_URL =
     * props.getProperty("spring.datasource.url");
     * private static final String USER =
     * props.getProperty("spring.datasource.username");
     * private static final String PASS =
     * props.getProperty("spring.datasource.password"); "
     * }
     */
/* 
    public Mockdata() {
    }
*/





    private static final String id = "Process_test";
    private static final String childProcess = "Process_child";
    private static final String startKnoten = "StartEvent_1;Event_1po129z;";
    private static final String endKnoten = "Event_1a8blh9;";
    private static final int energySumYear = 450;
    private static final String processType = "CORE";
    private static final String department = "Einkauf";
    private static final String processDescription = "This is a description";

  
    File xml= new File("src/test/java/repo/test_process.xml");
   


    Properties props = new Properties();
  

    public void insert() throws IOException, SerialException, SQLException {
    
        byte[] fileData = FileUtils.readFileToByteArray(xml);
        SerialBlob blob = new SerialBlob(fileData);

        // Verbindung zur Datenbank herstellen
        Connection con = null;
        try {
            props = PropertiesLoaderUtils.loadAllProperties("application.properties");
            String url = props.getProperty("spring.datasource.url");
            String user = props.getProperty("spring.datasource.username");
            String pass = props.getProperty("spring.datasource.password");
            con = DriverManager.getConnection(url, user, pass);

            // Statement zum Einfügen des Prozesses erstellen
            String sql = "INSERT INTO modell (id, child_Process, start_knoten, end_knoten, energy_sum_year, process_type, department, process_description, xml) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, childProcess);
            stmt.setString(3, startKnoten);
            stmt.setString(4, endKnoten);
            stmt.setInt(5, energySumYear);
            stmt.setString(6, processType);
            stmt.setString(7, department);
            stmt.setString(8, processDescription);
            stmt.setBlob(9, blob);

            // Prozess in Datenbank einfügen
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        Connection con = null;
        try {
            props = PropertiesLoaderUtils.loadAllProperties("application.properties");
            String url = props.getProperty("spring.datasource.url");
            String user = props.getProperty("spring.datasource.username");
            String pass = props.getProperty("spring.datasource.password");
            con = DriverManager.getConnection(url, user, pass);

            // Statement zum Einfügen des Prozesses erstellen
            String sql = "Delete from modell where id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, id);

            // Prozess aus Datenbank löschen
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
