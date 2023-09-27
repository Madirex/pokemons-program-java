package com.madiben.utils;

import org.slf4j.LoggerFactory;

/**
 * Clase LogMaker que se encarga de imprimir mensajes en el Logger
 */
public class LogGeneral {
    private static LogGeneral logGeneralInstance;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(LogGeneral.class);

    /**
     * Constructor privado de LogMaker
     */
    private LogGeneral() {
    }

    /**
     * Devuelve una instancia de LogMaker
     *
     * @return Instancia de LogMaker
     */
    public static LogGeneral getInstance() {
        if (logGeneralInstance == null) {
            logGeneralInstance = new LogGeneral();
        }
        return logGeneralInstance;
    }

    /**
     * Imprime un mensaje de información en el Logger
     *
     * @param message Mensaje de información
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * Imprime un mensaje de error en el Logger
     *
     * @param message Mensaje de error
     */
    public void error(String message, Class<?> errorClass) {
        var errorMsg = "Error en clase " + errorClass.getName() + ": " + message;
        logger.error(errorMsg);
    }
}
