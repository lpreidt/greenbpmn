package repo;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

public class Check {
  public static void main(String[] args) throws SerialException, IOException, SQLException {
    Mockdata mockdata = new Mockdata();
    mockdata.insert();
  }  
}
