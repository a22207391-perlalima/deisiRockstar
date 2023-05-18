package pt.ulusofona.aed.deisiRockstar2021;

import java.util.HashMap;

public class ParseInfo {
    public static HashMap<String, ParseInfo> hashEstatisticas = new HashMap<>();

    String ficheiro;
    int linhasLidas;
    int linhasIgnoradas;

    public ParseInfo(String ficheiro, int linhasLidas, int linhasIgnoradas) {
        this.ficheiro = ficheiro;
        this.linhasLidas = linhasLidas;
        this.linhasIgnoradas = linhasIgnoradas;
    }

    public ParseInfo(int linhasLidas, int linhasIgnoradas) {
        this.linhasLidas = linhasLidas;
        this.linhasIgnoradas = linhasIgnoradas;
    }

    public ParseInfo() {
    }

    public String toString() {
        return "OK: " + linhasLidas + ", Ignored: " + linhasIgnoradas;
    }
}
