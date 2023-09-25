package com.madiben.repositories;

import com.madiben.database.DatabaseManager;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.utils.StringConverters;
import com.madiben.utils.Utils;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz PokemonRepository
 */
@RequiredArgsConstructor
public class PokemonRepositoryImpl implements PokemonRepository {
    private final DatabaseManager database;

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
        Optional<PokemonDataDTO> objReturn = Optional.empty();
        var sql = "INSERT INTO pokemon (num, name, height, weight) VALUES (?, ?, ?, ?)";
        database.open();
        PreparedStatement res = database.insertNumericKey(sql, entity.getNum(), entity.getName(), entity.getHeight(), entity.getWeight())
                .orElseThrow(() -> new SQLException("Error al insertar el Pokémon"));
        objReturn = Optional.of(entity);
            int n = res.executeUpdate();
            if (n > 0){
                ResultSet rs = res.getGeneratedKeys();
                while (rs.next()){
                    objReturn.get().setId(rs.getInt(1));
                }
                rs.close();
                database.close();
            }else{
                throw new SQLException("Error al insertar el Pokémon");
            }
        return objReturn;
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
            var sql = "SELECT * FROM pokemon WHERE name LIKE ?";
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

