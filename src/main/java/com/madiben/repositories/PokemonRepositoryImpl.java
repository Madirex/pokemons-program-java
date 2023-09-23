package com.madiben.repositories;

import com.madiben.database.DatabaseController;
import com.madiben.models.Pokemon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
     * Busca todos los elementos en el repositorio
     *
     * @return Optional de la lista de elementos encontrados
     */
    @Override
    public Optional<List<Pokemon>> findAll() throws SQLException {
        String sql = "SELECT * FROM pokemon";
        database.open();
        ResultSet res = database.select(sql).orElseThrow(() -> new SQLException("Error al obtener a los pokemons."));
        var pokemons = new ArrayList<Pokemon>();
        while (res.next()) {
//            var pokemon = Pokemon.builder()
//                    .id(res.getInt("id"))
//                    .num(res.getString("num"))
//                    .img(res.getString("img"))
//                    //.type(res.getType("type")) //TODO: DO
//                    .height(res.getString("height"))
//                    .weight(res.getString("weight"))
//                    .candy(res.getString("candy"))
//                    .candyCount(res.getInt("candyCount"))
//                    .egg(res.getString("egg"))
//                    .spawnChance(res.getDouble("spawnChance"))
//                    .avgSpawns(res.getDouble("avgSpawns"))
//                    .spawnTime(res.getString("spawntime"))
//                    //.multipliers() //TODO: DO
//                    //.weaknesses() //TODO: DO
//                    //.nextEvolution() //TODO: DO
//                    //.prevEvolution() //TODO: DO
//
//                    .build();
//            pokemons.add(pokemon);
        }
        database.close();
        return Optional.of(pokemons);

    }

    /**
     * Busca un elemento en el repositorio
     *
     * @param integer Id del elemento a buscar
     * @return Optional del elemento encontrado
     */

    @Override
    public Optional<Pokemon> findById(Integer integer) throws SQLException {
        String sql = "SELECT * FROM pokemon where id= ?";
        database.open();
        ResultSet res = database.select(sql).orElseThrow(() -> new SQLException("Error al obtener las personas."));
        var pokemons = new ArrayList<>();

        if (res.next()) {

//            var pokemon = Pokemon.builder()
//                    .id(res.getInt("id"))
//                    .num(res.getString("num"))
//                    .img(res.getString("img"))
//                    //.type(res.getType("type")) //TODO: DO
//                    .height(res.getString("height"))
//                    .weight(res.getString("weight"))
//                    .candy(res.getString("candy"))
//                    .candyCount(res.getInt("candyCount"))
//                    .egg(res.getString("egg"))
//                    .spawnChance(res.getDouble("spawnChance"))
//                    .avgSpawns(res.getDouble("avgSpawns"))
//                    .spawnTime(res.getString("spawntime"))
//                    //.multipliers() //TODO: DO
//                    //.weaknesses() //TODO: DO
//                    //.nextEvolution() //TODO: DO
//                    //.prevEvolution() //TODO: DO
//                    .build();
//            pokemons.add(pokemon);
            database.close();
//            return Optional.of(pokemon);
        }
        database.close();
        return Optional.empty();

    }

    /**
     * Guarda un elemento en el repositorio
     *
     * @param entity Elemento a guardar
     * @return Optional del elemento guardado
     */
    @Override
    public Optional<Pokemon> save(Pokemon entity) throws SQLException {
        var sql = "INSERT INTO pokemon (id,num,name,img,type,height,weight,candy,candyCount,egg,spawnChance," +
        "avgSpawns,spawnTime,multipliers,weaknesses,nextEvolution,prevEvolution) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        database.open();
        var res= database.insert(sql,entity.getId(),entity.getNum(),entity.getName(),entity.getImg(),entity.getType()
                ,entity.getHeight(),entity.getWeight(),entity.getCandy(),entity.getCandyCount(),entity.getEgg(),
                entity.getSpawnChance(),entity.getAvgSpawns(),entity.getSpawnTime(),entity.getMultipliers(),
                entity.getWeaknesses(),entity.getNextEvolution(),entity.getPrevEvolution())
                .orElseThrow(() -> new SQLException("Error al insertar el pokemon"));
        database.close();
        if(res.next()){
            return Optional.of(entity);
        }
        else {
            database.close();
            throw new SQLException("Error al insertar el pokemon");

        }


    }

    /**
     * Actualiza un elemento del repositorio
     *
     * @param integer Id del elemento a actualizar
     * @param entity  Elemento con los nuevos datos
     * @return Optional del elemento actualizado
     */
    @Override
    public Optional<Pokemon> update(Integer integer, Pokemon entity) throws SQLException {
        var sql="UPDATE pokemon SET id=?, num=?, name=?, img=?, type=?,height=?,weight=?,candy=?,candyCount=?,egg=?," +
                "spawnChance=?,avgSpawns=?,spawnTime=?,multipliers=?,weaknesses=?,nextEvolution=?,prevEvolution=? " +
                "WHERE id=?";
        database.open();
        var res= database.update(sql,entity.getId(),entity.getNum(),entity.getName(),entity.getImg(),entity.getType(),
                entity.getHeight(),entity.getWeight(),entity.getCandy(),entity.getCandyCount(),entity.getEgg(),
                entity.getSpawnChance(),entity.getAvgSpawns(),entity.getSpawnTime(),entity.getMultipliers(),
                entity.getWeaknesses(),entity.getNextEvolution(),entity.getPrevEvolution(),integer);
        if(res >0){
            database.close();
            return Optional.of(entity);
        }
        else {
            database.close();
            throw new SQLException("Error al actualizar el pokemon");

        }

    }

    /**
     * Borra un elemento del repositorio
     *
     * @param integer Id del elemento a borrar
     * @return Optional del elemento borrado
     */
    @Override
    public Optional<Pokemon> delete(Integer integer) throws SQLException {
        var pokemon = findById(integer).orElseThrow(() -> new RuntimeException("No se ha encontrado el pokemon"));
        var sql="DELETE FROM pokemon WHERE id=?";
        database.open();
        var res= database.delete(sql,integer);
        database.close();
        if(res >0){
            return Optional.of(pokemon);
        }
        else {
            throw new SQLException("Error al borrar el pokemon");

        }
    }

    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Optional del elemento encontrado
     */
    @Override
    public Optional<Pokemon> findByName(String name) throws SQLException {
        var sql="SELECT * FROM pokemon WHERE name=?";
        database.open();
        var pokemons = new ArrayList<>();
        var res= database.select(sql,name).orElseThrow(() -> new SQLException("Error al obtener el pokemon"));
        if(res.next()){
//            var pokemon = Pokemon.builder()
//                    .id(res.getInt("id"))
//                    .num(res.getString("num"))
//                    .img(res.getString("img"))
//                    //.type(res.getType("type")) //TODO: DO
//                    .height(res.getString("height"))
//                    .weight(res.getString("weight"))
//                    .candy(res.getString("candy"))
//                    .candyCount(res.getInt("candyCount"))
//                    .egg(res.getString("egg"))
//                    .spawnChance(res.getDouble("spawnChance"))
//                    .avgSpawns(res.getDouble("avgSpawns"))
//                    .spawnTime(res.getString("spawntime"))
//                    //.multipliers() //TODO: DO
//                    //.weaknesses() //TODO: DO
//                    //.nextEvolution() //TODO: DO
//                    //.prevEvolution() //TODO: DO
//
//                    .build();
//            pokemons.add(pokemon);
            database.close();
//            return Optional.of(pokemon);
        }
        database.close();
        return Optional.empty();

        }
    }

