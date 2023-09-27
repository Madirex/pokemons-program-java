package com.madiben.repositories;

import com.madiben.dto.PokemonDataDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD de PokemonRepository
 *
 */
public interface PokemonRepository extends CRUDRepository<PokemonDataDTO, Integer> {
    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Lista de elementos encontrados
     */
    List<PokemonDataDTO> findByName(String name) throws SQLException;
}
