package com.madiben.utils;

import java.util.Optional;

/**
 * Esta clase contiene métodos para convertir Strings a otros tipos de datos
 */
public class StringConverters {

    private static StringConverters stringConvertersInstance;

    /**
     * SINGLETON - Constructor privado de la clase StringConverters
     */
    private StringConverters() {
    }

    /**
     * SINGLETON - Este método devuelve una instancia de la clase StringConverters
     * @return Instancia de la clase StringConverters
     */
    public static StringConverters getInstance() {
        if (stringConvertersInstance == null) {
            stringConvertersInstance = new StringConverters();
        }
        return stringConvertersInstance;
    }

    /**
     * Parsea un string con valor double POSITIVO a un Opcional de Double
     * Si el número es negativo o no es un número válido, devuelve un Optional vacío
     * Este método convierte hasta que el caracter deje de ser un número
     * Ejemplo de uso: "1.80 m" devolvería un Optional Double con un doble con valor de 1.80
     * @param str String a convertir
     * @return Optional de double
     */
    public Optional<Double> strPositiveValToDoubleParser(String str) {
        StringBuilder strToParse = new StringBuilder();
        double convertedNum = 0.0;

        if (str == null) {
            return Optional.empty();
        }
        for(int i = 0; i < str.length(); i++){
            if (str.charAt(i) == '.' || charIsNumber(str.charAt(i))){
                strToParse.append(str.charAt(i));
            }
        }

        if (!strToParse.isEmpty()){
            convertedNum = Double.parseDouble(strToParse.toString());
        }else{
            return Optional.empty();
        }

        return Optional.of(convertedNum);
    }

    /**
     * Este método comprueba en qué parte del boolean se debería de insertar un número dado el caracter
     * Es decir, dado un boolean que indica si el caracter a insertar es posterior al punto decimal o no,
     * parsea el caracter y lo inserta en la parte correcta del double (anterior o posterior al punto decimal)
     * @param isPostDecimalPoint ¿Trabajando en la parte posterior al punto decimal?
     * @param convertedNum Número convertido hasta el momento
     * @param character Caracter a insertar en el número double
     * @return Optional de double
     */
    private double characterInsertToDouble(boolean isPostDecimalPoint, double convertedNum, char character){
        if (isPostDecimalPoint){
            convertedNum = convertedNum + ((character - '0') * 0.1);
        } else {
            convertedNum = convertedNum * 10 + (character - '0');
        }
        return convertedNum;
    }

    /**
     * Este método comprueba si un caracter es un número o no
     * @param ch Caracter a comprobar
     * @return ¿Es un número?
     */
    private boolean charIsNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

    /**
     * Este método parsea el nombre de un Pokémon (eliminando el género de delante)
     * Excluye lo que viene después del símbolo ASCII 226
     * @param name Nombre del Pokémon
     * @return Nombre del Pokémon sin el género (excluyendo lo que viene después del símbolo ASCII 226)
     */
    public String parsePokemonName(String name) {
        StringBuilder pokemonName = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == 226) {
                if (i - 1 > 0 && name.charAt(i - 1) == ' ') {
                    pokemonName.deleteCharAt(i - 1);
                }
                break;
            }
            pokemonName.append(name.charAt(i));
        }
        return pokemonName.toString();
    }

    /**
     * Este método convierte el número de un Pokémon a su ID
     * @param num Número del Pokémon
     * @return ID del Pokémon
     */
    public long convertPokemonNumToPokemonId(String num) {
        System.out.println(num);
        while (num.charAt(0) == '0') {
            num = num.substring(1);
        }
        System.out.println(num);
        return Long.parseLong(num);
    }
}
