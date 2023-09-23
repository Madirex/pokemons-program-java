package com.madiben.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationProperties {

    private static ApplicationProperties applicationPropertiesInstance;
    private final Properties properties;

    private ApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.ALL,ex,
                    () -> "IOException Ocurrido al leer el fichero de propiedades.");
        }
    }

    public static ApplicationProperties getInstance() {
        if (applicationPropertiesInstance == null) {
            applicationPropertiesInstance = new ApplicationProperties();
        }
        return applicationPropertiesInstance;
    }

    public String readProperty(String keyName) {
        return properties.getProperty(keyName, "No existe esa clave en el fichero de propiedades");
    }
}