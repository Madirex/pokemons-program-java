package com.madiben.repositories;

import com.madiben.dto.PokemonDataDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface PokemonRepository extends CRUDRepository<PokemonDataDTO, Integer>{
    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Optional del elemento encontrado
     */
    Optional<PokemonDataDTO> findByName(String name) throws SQLException;

    /**
     * Select que imprime los datos de la base de datos
     */
    void select() throws SQLException;
}
