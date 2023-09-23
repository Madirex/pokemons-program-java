package com.madiben.repositories;

import com.madiben.models.Pokemon;

import java.sql.SQLException;
import java.util.Optional;

public interface PokemonRepository extends CRUDRepository<Pokemon, Integer>{
    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Optional del elemento encontrado
     */
    Optional<Pokemon> findByName(String name) throws SQLException;
}
