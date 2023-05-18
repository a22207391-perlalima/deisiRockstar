package pt.ulusofona.aed.deisiRockstar2021;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Artista {
    public static LinkedHashMap<String, Artista> hashArtistas = new LinkedHashMap<>();

    String idTema;
    String nome;
    HashMap<String, Song> temas;
    HashSet<String> tags;

    public Artista(String idTema, String nome) {
        this.idTema = idTema;
        this.nome = nome;
        this.temas = new HashMap<>();
        this.tags = new HashSet<>();
    }

    public Artista() {
    }

    @Override
    public String toString() {
        return nome + " | (" + temas.size() + ")";
    }
}