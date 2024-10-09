package co.wedevx.digitalbank.automation.ui.utils;

import java.io.ObjectInputFilter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.ConfigReader.getPropertiesValue;
import static org.bouncycastle.cms.RecipientId.password;

public class DBUtils {

    private static  Connection connection = null;
    private static Statement statement;
    private static ResultSet resultSet;
    //method to establish connection with the db
public static void establishConnection(){



    //in a real world scenario username and pass usually taken from resources-properties.
    try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(getPropertiesValue("digitalbank.db.url"),
                getPropertiesValue("digitalbank.db.username"),
                getPropertiesValue("digitalbank.db.password"));
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}

    //a method that can dynamically send select statements
    //and returns a list of map of all columns
    public static List<Map<String, Object>> runSQLSelectQuery(String sqlQuery){
    List<Map<String, Object>> dbResultList = new ArrayList<>();
        try {
         statement = connection.createStatement();
         resultSet = statement.executeQuery(sqlQuery);

         //getMetaData method returns info about your info.
            //cover your envelope
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

         while (resultSet.next()){
             //every row we need to create new Map.
             Map<String, Object> rowMap = new HashMap<>();

             //go to one row, transform it into map, call the columns,
             //take all column names, map ot with all column objects
             // once this rowMap is populated, add it into List.
             // So then we have multiple rows with info
             for (int column = 1; column <= columnCount; column++){
                 rowMap.put(resultSetMetaData.getColumnName(column), resultSet.getObject(column));
             }
             dbResultList.add(rowMap);
         }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dbResultList;
    }


    //create a method that inserts into db
    //return a number of rows updated or zero when action is not taken

    //delete or truncate the table

public static int runSQLUpdateQuery(String sqlQuery){
    int rowsAffected = 0;
    try{
        statement = connection.createStatement();
    rowsAffected = statement.executeUpdate(sqlQuery);
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    return rowsAffected;
}
    //close connection method
    public static void closeConnection(){
    try{
        if (resultSet != null){
            resultSet.close();
        }
        if (statement != null){
            statement.close();
        }
        if (connection != null){
            connection.close();
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
}
