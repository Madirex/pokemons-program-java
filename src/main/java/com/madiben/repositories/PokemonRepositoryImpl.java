package com.madiben.repositories;

import com.madiben.database.DatabaseController;
import com.madiben.models.Pokemon;

import java.util.List;
import java.util.Optional;

public class PokemonRepositoryImpl implements PokemonRepository
{
    private final DatabaseController database;

    public PokemonRepositoryImpl(DatabaseController database) {
        this.database = database;
    }

    /**
     * Busca todos los elementos en el repositorio
     *
     * @return Optional de la lista de elementos encontrados
     */
    @Override
    public Optional<List<Pokemon>> findAll() {
        return Optional.empty();
    }

    /**
     * Busca un elemento en el repositorio
     *
     * @param integer Id del elemento a buscar
     * @return Optional del elemento encontrado
     */
    @Override
    public Optional<Pokemon> findById(Integer integer) {
        return Optional.empty();
    }

    /**
     * Guarda un elemento en el repositorio
     *
     * @param entity Elemento a guardar
     * @return Optional del elemento guardado
     */
    @Override
    public Optional<Pokemon> save(Pokemon entity) {
        return Optional.empty();
    }

    /**
     * Actualiza un elemento del repositorio
     *
     * @param integer Id del elemento a actualizar
     * @param entity  Elemento con los nuevos datos
     * @return Optional del elemento actualizado
     */
    @Override
    public Optional<Pokemon> update(Integer integer, Pokemon entity) {
        return Optional.empty();
    }

    /**
     * Borra un elemento del repositorio
     *
     * @param integer Id del elemento a borrar
     * @return Optional del elemento borrado
     */
    @Override
    public Optional<Pokemon> delete(Integer integer) {
        return Optional.empty();
    }

    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Optional del elemento encontrado
     */
    @Override
    public Optional<Pokemon> findByName(String name) {
        return Optional.empty();
    }
}
