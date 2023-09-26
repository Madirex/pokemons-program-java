package com.madiben.repositories;

import com.madiben.database.DatabaseManager;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.utils.StringConverters;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz PokemonRepository
 */
@RequiredArgsConstructor
public class PokemonRepositoryImpl implements PokemonRepository {
    private final DatabaseManager database;

    /**
     * Devuelve todos los elementos del repositorio
     *
     * @return Optional de la lista de elementos
     */
    @Override
    public List<PokemonDataDTO> findAll() throws SQLException {
        List<PokemonDataDTO> list = new ArrayList<>();
        var sql = "SELECT * FROM pokemon";
        var res = database.select(sql).orElseThrow();
        while (res.next()) {
            list.add(PokemonDataDTO.builder()
                    .id(res.getLong("id"))
                    .num(res.getString("num"))
                    .name(res.getString("name"))
                    .height(StringConverters.getInstance()
                            .strPositiveValToDoubleParser(res.getString("height")).orElse(0.0))
                    .weight(StringConverters.getInstance()
                            .strPositiveValToDoubleParser(res.getString("weight")).orElse(0.0))
                    .build());
        }
        database.close();
        return list;
    }

    /**
     * Busca un elemento en el repositorio por su id
     *
     * @param integer Id del elemento a buscar
     * @return Optional del elemento encontrado
     */
    @Override
    public Optional<PokemonDataDTO> findById(Integer integer) throws SQLException {
        Optional<PokemonDataDTO> optReturn = Optional.empty();
        var sql = "SELECT * FROM pokemon WHERE id = ?";
        var res = database.select(sql, integer).orElseThrow();
        if (res.next()) {
            optReturn = Optional.of(PokemonDataDTO.builder()
                    .id(res.getLong("id"))
                    .num(res.getString("num"))
                    .name(res.getString("name"))
                    .height(StringConverters.getInstance()
                            .strPositiveValToDoubleParser(res.getString("height")).orElse(0.0))
                    .weight(StringConverters.getInstance()
                            .strPositiveValToDoubleParser(res.getString("weight")).orElse(0.0))
                    .build());
        }
        database.close();
        return optReturn;
    }

    /**
     * Guarda un elemento en el repositorio
     *
     * @param entity Elemento a guardar
     * @return Optional del elemento guardado
     */
    @Override
    public Optional<PokemonDataDTO> save(PokemonDataDTO entity) throws SQLException {
        long id = 0;
        var sql = "INSERT INTO pokemon (num, name, height, weight) VALUES (?, ?, ?, ?)";
        database.open();
        var res = database.insertAndGetKey(sql, entity.getNum(), entity.getName(), entity.getHeight(), entity.getWeight())
                .orElseThrow();
        if (res.next()) {
            id = res.getLong(1);
        }
        database.close();
        entity.setId(id);
        return Optional.of(entity);
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
     * @return Lista de elementos encontrados
     */
    @Override
    public List<PokemonDataDTO> findByName(String name) throws SQLException {
        return findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

}

