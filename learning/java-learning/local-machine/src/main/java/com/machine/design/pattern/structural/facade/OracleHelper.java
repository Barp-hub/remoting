package com.machine.design.pattern.structural.facade;

import java.sql.Connection;

/**
 * Created by michael on 2017-06-27.
 */
public class OracleHelper {

    public static Connection getOracleDBConnection() {
        //get MySql DB connection using connection parameters
        return null;
    }

    public void generateOraclePDFReport(String tableName, Connection
            con) {
        //get data from table and generate pdf report
    }

    public void generateOracleHTMLReport(String tableName, Connection
            con) {
        //get data from table and generate pdf report
    }

}
