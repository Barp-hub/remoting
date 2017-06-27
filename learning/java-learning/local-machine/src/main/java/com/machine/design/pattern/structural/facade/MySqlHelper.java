package com.machine.design.pattern.structural.facade;

import java.sql.Connection;

/**
 * Created by michael on 2017-06-27.
 */
public class MySqlHelper {

    public static Connection getMySqlDBConnection() {
        //get MySql DB connection using connection parameters
        return null;
    }

    public void generateMySqlPDFReport(String tableName, Connection
            con) {
        //get data from table and generate pdf report
    }

    public void generateMySqlHTMLReport(String tableName, Connection
            con) {
        //get data from table and generate pdf report
    }

}
