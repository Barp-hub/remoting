package io.github.riwcwt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteTest {

    private static final Logger logger = LoggerFactory.getLogger(SqliteTest.class);

    @Test
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:rss.db";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                logger.info("Connected to the database");
                DatabaseMetaData dm = conn.getMetaData();
                logger.info("Driver name: " + dm.getDriverName());
                logger.info("Driver version: " + dm.getDriverVersion());
                logger.info("Product name: " + dm.getDatabaseProductName());
                logger.info("Product version: " + dm.getDatabaseProductVersion());
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
