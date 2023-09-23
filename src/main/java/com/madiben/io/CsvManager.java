package com.madiben.io;

import com.madiben.dto.PokemonDataDTO;
import com.madiben.utils.StringConverters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class CsvManager {


    private static CsvManager csvManagerInstance;

    private CsvManager() {
    }

    public static CsvManager getInstance() {
        if (csvManagerInstance == null) {
            csvManagerInstance = new CsvManager();
        }
        return csvManagerInstance;
    }

    public Optional<Stream<PokemonDataDTO>> fileToPokemonDataDTO(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return Optional.of(reader.lines()
                    .map(line -> line.split(","))
                    .map(values -> new PokemonDataDTO(values[0],
                            StringConverters.getInstance().strPositiveValToDoubleParser(values[1]).orElse(0.0),
                            StringConverters.getInstance().strPositiveValToDoubleParser(values[2]).orElse(0.0))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
