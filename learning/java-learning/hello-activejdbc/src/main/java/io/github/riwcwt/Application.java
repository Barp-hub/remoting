package io.github.riwcwt;

import io.github.riwcwt.model.Role;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Base.open();
        logger.info("count : " + Role.getTableName());

        Base.close();
    }
}
