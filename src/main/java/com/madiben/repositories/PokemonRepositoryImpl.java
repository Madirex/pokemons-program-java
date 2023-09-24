package com.madiben.repositories;

import com.madiben.database.DatabaseController;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.models.Pokemon;
import com.madiben.utils.StringConverters;
import com.madiben.utils.Utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz PokemonRepository
 */
public class PokemonRepositoryImpl implements PokemonRepository {
    private final DatabaseController database;

    /**
     * Constructor de la clase PokemonRepositoryImpl
     *
     * @param database Instancia de la clase DatabaseController
     */

    public PokemonRepositoryImpl(DatabaseController database) {
        this.database = database;
    }

    /**
     * [Método deshabilitado]
     *
     * @return Optional de la lista de elementos encontrados
     */
    @Override
    public Optional<List<PokemonDataDTO>> findAll() {
        return Optional.empty();
    }

    /**
     * [Método deshabilitado]
     *
     * @param integer Id del elemento a buscar
     * @return Optional del elemento encontrado
     */
    @Override
    public Optional<PokemonDataDTO> findById(Integer integer) {
        return Optional.empty();
    }

    /**
     * Guarda un elemento en el repositorio
     *
     * @param entity Elemento a guardar
     * @return Optional del elemento guardado
     */
    @Override
    public Optional<PokemonDataDTO> save(PokemonDataDTO entity) throws SQLException {
        var sql = "INSERT INTO pokemon (num, name, height, weight) VALUES (?, ?, ?, ?)";
        database.open();
        var res = database.insert(sql, entity.getNum(), entity.getName(), entity.getHeight(), entity.getWeight())
                .orElseThrow(() -> new SQLException("Error al insertar el Pokémon"));
        if (res.next()) {
            database.close();
            return Optional.of(entity);
        } else {
            database.close();
            throw new SQLException("Error al insertar el Pokémon");
        }
    }

    /**
     * [Método deshabilitado]
     *
     * @param integer Id del elemento a actualizar
     * @param entity  Elemento con los nuevos datos
     * @return Optional del elemento actualizado
     */
    @Override
    public Optional<PokemonDataDTO> update(Integer integer, PokemonDataDTO entity) {
        return Optional.empty();
    }

    /**
     * [Método deshabilitado]
     *
     * @param integer Id del elemento a borrar
     * @return Optional del elemento borrado
     */
    @Override
    public Optional<PokemonDataDTO> delete(Integer integer) {
        return Optional.empty();
    }

    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Optional del elemento encontrado
     */
    @Override
    public Optional<PokemonDataDTO> findByName(String name) throws SQLException {
        try {
            var sql = "SELECT * FROM pokemon WHERE name=?";
            database.open();
            var res = database.select(sql, name);
            if (res.isPresent() && res.get().first()) {
                return Optional.of(PokemonDataDTO.builder()
                        .id(res.get().getInt("id"))
                        .num(res.get().getString("num"))
                        .name(res.get().getString("name"))
                        .height(StringConverters.getInstance()
                                .strPositiveValToDoubleParser(res.get().getString("height")).orElse(0.0))
                        .weight(StringConverters.getInstance()
                                .strPositiveValToDoubleParser(res.get().getString("weight")).orElse(0.0))
                        .build());
            }
        } catch (SQLException e) {
            Utils.print("Error base de datos al buscar el Pokémon");
        } finally {
            database.close();
        }
        return Optional.empty();
    }

    /**
     * Select que imprime TODOS los datos de la base de datos
     */
    @Override
    public void select() throws SQLException {
        var sql = "SELECT *";

        database.open();
        ResultSet rs = database.select(sql).orElseThrow(() -> new SQLException("Error al ejecutar el SELECT"));

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Imprimir nombre columnas
        for (int i = 1; i <= columnCount; i++) {
            Utils.print(metaData.getColumnName(i) + "\t");
        }

        Utils.print("\n");

        // Imprimir datos
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                Utils.print(rs.getString(i) + "\t");
            }
            Utils.print("\n");
        }
        database.close();
    }
}

