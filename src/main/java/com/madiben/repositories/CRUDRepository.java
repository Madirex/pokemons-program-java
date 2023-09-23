package com.madiben.repositories;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones CRUD sobre un repositorio
 *
 * @param <T>  Tipo de la entidad
 * @param <ID> Tipo del id de la entidad
 */
public interface CRUDRepository<T, ID> {
    /**
     * Devuelve todos los elementos del repositorio
     *
     * @return Optional de la lista de elementos
     */
    Optional<List<T>> findAll();

    /**
     * Devuelve un elemento del repositorio
     *
     * @param id Id del elemento a buscar
     * @return Optional del elemento encontrado
     */
    Optional<T> findById(ID id);

    /**
     * Guarda un elemento en el repositorio
     *
     * @param entity Elemento a guardar
     * @return Optional del elemento guardado
     */
    Optional<T> save(T entity);

    /**
     * Actualiza un elemento del repositorio
     *
     * @param id     Id del elemento a actualizar
     * @param entity Elemento con los nuevos datos
     * @return Optional del elemento actualizado
     */
    Optional<T> update(ID id, T entity);

    /**
     * Borra un elemento del repositorio
     *
     * @param id Id del elemento a borrar
     * @return Optional del elemento borrado
     */
    Optional<T> delete(ID id);
}