package com.madiben.database;

import com.madiben.utils.ApplicationProperties;
import lombok.NonNull;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Optional;

/**
 * Controlador de Bases de Datos
 */
public class DatabaseManager {
    private static DatabaseManager controller;
    @NonNull
    private String serverUrl;
    @NonNull
    private String serverPort;
    @NonNull
    private String dataBaseName;
    @NonNull
    private String user;
    @NonNull
    private String password;
    @NonNull
    private String driver;
    @NonNull
    private String init;
    @NonNull
    private Connection connection;
    @NonNull
    private PreparedStatement preparedStatement;

    /**
     * Constructor privado para Singleton
     */
    private DatabaseManager() {
        initConfig();
    }

    /**
     * Devuelve una instancia del controlador
     * @return instancia del controladorBD
     */
    public static DatabaseManager getInstance() {
        if (controller == null) {
            controller = new DatabaseManager();
        }
        return controller;
    }

    /**
     * Carga la configuración de acceso al servidor de Base de Datos
     */
    private void initConfig() {
        ApplicationProperties properties = ApplicationProperties.getInstance();
        serverUrl = properties.readProperty("db.url");
        serverPort = properties.readProperty("db.port");
        dataBaseName = properties.readProperty("db.name");
        driver = properties.readProperty("db.driver");
        init = properties.readProperty("db.init");
        Dotenv dotenv = Dotenv.load();
        user = dotenv.get("DATABASE_USER");
        password = dotenv.get("DATABASE_PASSWORD");
    }

    /**
     * Abre la conexión con el servidor de Base de Datos
     * @throws SQLException Servidor no accesible por problemas de conexión o datos de acceso incorrectos
     */
    public void open() throws SQLException {
        String url = this.driver + /*this.serverUrl + ":" +  this.serverPort +*/ File.separator + this.dataBaseName; //TODO: FIX
        connection = DriverManager.getConnection(url, user, password);
        initData();
    }

    /**
     * Cierra la conexión con el servidor de base de datos
     * @throws SQLException Servidor no responde o no puede realizar la operación de cierre
     */
    public void close() throws SQLException {
        preparedStatement.close();
        connection.close();
    }

    /**
     * Inicializa la base de datos con los datos del fichero data.sql
     * Solo si el properties tiene la propiedad db.init en TRUE
     */
    public void initData() {
        if (init.equalsIgnoreCase("true")) {
            try {
                String sql = new String(getClass().getClassLoader()
                        .getResourceAsStream("data.sql").readAllBytes(), StandardCharsets.UTF_8);
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Realiza una consulta a la base de datos de manera "preparada" obteniendo los parámetros opcionales si son necesarios
     * @param querySQL consulta SQL de tipo select
     * @param params   parámetros de la consulta parametrizada
     * @return ResultSet de la consulta
     * @throws SQLException No se ha podido realizar la consulta o la tabla no existe
     */
    private ResultSet executeQuery(@NonNull String querySQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(querySQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Realiza una consulta select a la base de datos de manera "preparada" obteniendo
     * los parámetros opcionales si son necesarios
     * @param querySQL consulta SQL de tipo select
     * @param params   parámetros de la consulta parametrizada
     * @return ResultSet de la consulta
     * @throws SQLException No se ha podido realizar la consulta o la tabla no existe
     */
    public Optional<ResultSet> select(@NonNull String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    /**
     * Realiza una consulta select a la base de datos de manera "preparada" obteniendo
     * los parámetros opcionales si son necesarios
     * @param querySQL consulta SQL de tipo select
     * @param limit    número de registros de la página
     * @param offset   desplazamiento de registros o número de registros ignorados para comenzar la devolución
     * @param params   parámetros de la consulta parametrizada
     * @return ResultSet de la consulta
     * @throws SQLException No se ha realizado la consulta, la tabla no existe o el desplazamiento es mayor que el número de registros
     */
    public Optional<ResultSet> select(@NonNull String querySQL, int limit, int offset, Object... params) throws SQLException {
        String query = querySQL + " LIMIT " + limit + " OFFSET " + offset;
        return Optional.of(executeQuery(query, params));
    }

    /**
     * Realiza una consulta de tipo insert de manera "preparada" con los parámetros opcionales si son necesarios
     * @param insertSQL consulta SQL de tipo insert
     * @param params parámetros de la consulta parametrizada
     * @return Clave del registro insertada
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public Optional<ResultSet> insert(@NonNull String insertSQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
        return Optional.of(preparedStatement.getGeneratedKeys());
    }

    /**
     * Realiza una consulta de tipo insert de manera "preparada" con los parámetros opcionales si son necesarios
     * @param insertSQL consulta SQL de tipo insert
     * @param params parámetros de la consulta parametrizada
     * @return PreparedStatement de la consulta
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public Optional<PreparedStatement> insertNumericKey(@NonNull String insertSQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return Optional.of(preparedStatement);
    }

    /**
     * Realiza una consulta de tipo update de manera "preparada" con los parámetros opcionales si son encesarios
     * @param updateSQL consulta SQL de tipo update
     * @param params parámetros de la consulta parametrizada
     * @return número de registros actualizados
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public int update(@NonNull String updateSQL, Object... params) throws SQLException {
        return updateQuery(updateSQL, params);
    }

    /**
     * Realiza una consulta de tipo delete de manera "preparada" con los parámetros opcionales si son necesarios
     * @param deleteSQL consulta SQL de tipo delete
     * @param params parámetros de la consulta parametrizada
     * @return número de registros eliminados
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public int delete(@NonNull String deleteSQL, Object... params) throws SQLException {
        return updateQuery(deleteSQL, params);
    }

    /**
     * Realiza una consulta de tipo update, es decir, que modifica los datos, de manera "preparada" con
     * los parámetros opcionales si son necesarios
     * @param genericSQL consulta SQL de tipo update, delete, creted, etc.. que modifica los datos
     * @param params parámetros de la consulta parametrizada
     * @return número de registros eliminados
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    private int updateQuery(@NonNull String genericSQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(genericSQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }
}