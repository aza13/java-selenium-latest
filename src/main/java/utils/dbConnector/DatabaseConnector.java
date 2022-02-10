package utils.dbConnector;

import constants.ConstantVariable;
import org.apache.log4j.Logger;
import utils.fileReader.ConfigDataReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseConnector {

    private static String databaseUrl = null;
    private static String databaseDriverName = null;
    private static String userName = null;
    private static String password = null;
    private static Connection connection = null;
    private Statement stmt = null;
    private ResultSet resultSet = null;
    public Integer rowCount;

    private static final Logger logger = Logger.getLogger(DatabaseConnector.class);


    public DatabaseConnector(){
        databaseUrl = ConfigDataReader.configPropInit(ConstantVariable.CONFIG_PROP_FILEPATH).getProperty("dbUrl");
        databaseDriverName = ConfigDataReader.configPropInit(ConstantVariable.CONFIG_PROP_FILEPATH).getProperty("dbDriverName");
        userName = ConfigDataReader.configPropInit(ConstantVariable.CONFIG_PROP_FILEPATH).getProperty("dbUserName");
        password = ConfigDataReader.configPropInit(ConstantVariable.CONFIG_PROP_FILEPATH).getProperty("dbPassword");
    }

    public Connection getConnection() {
        try {
            Class.forName(databaseDriverName);
            try {
                connection = DriverManager.getConnection(databaseUrl, userName, password);
                connection.setAutoCommit(true);
                logger.info("Successfully created database connection in getConnection:: DatabaseConnector");
            } catch (SQLException ex) {
                logger.error("Failed to create the database connection in getConnection:: DatabaseConnector" + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            logger.error("Driver not found in getConnection:: DatabaseConnector" + ex.getMessage());
        }
        return connection;
    }

    public List<HashMap<Object,Object>> getResultSetToList(String query) throws SQLException {

        List<HashMap<Object,Object>> list = null;

        try {
            logger.info("Executing given database query in getResultSetToList :: DatabaseConnector");
            stmt = getConnection().createStatement();
            resultSet = stmt.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = metaData.getColumnCount();
            String[] columnName = new String[count];
            list=new ArrayList<>();
            rowCount = 0;
            while(resultSet.next()) {
                HashMap<Object,Object> map=new HashMap<>();
                for (int i = 1; i <= count; i++){
                    columnName[i-1] = metaData.getColumnLabel(i);
                    map.put(columnName[i-1], resultSet.getObject(i));
                }
                list.add(map);
                rowCount++;
            }
            // Fix - IndexOutOfBounds Index 0 out of bound
            if(rowCount == 0)
                list = null;

        }  catch (SQLException ex) {
            logger.error("Get Result set error in getResultSetToList:: DatabaseConnector" + ex.getMessage());
        }
        return list;
    }

    public Integer getTableRowCount(){return rowCount;}

    public void closeDatabaseConnector() {
        try {
            if (resultSet != null)
                resultSet.close();

            if (stmt != null)
                stmt.close();

            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            logger.error("Close DB connection closeDatabaseConnector:: DatabaseConnector" + ex.getMessage());
        }
    }
}
