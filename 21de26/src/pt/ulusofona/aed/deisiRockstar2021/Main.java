package pt.ulusofona.aed.deisiRockstar2021;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void loadFiles() throws IOException {
        ParseInfo.hashEstatisticas.clear();
        FicheiroLeitura.readFileSongs("songs.txt");
        FicheiroLeitura.readFileSongsArtists("song_artists.txt");
        FicheiroLeitura.lerDetalhes("song_details.txt");
        FicheiroLeitura.copiarDeHashParaArrayMusicas();
    }

    public static String execute(String comando) {
        String[] ComandoLista = comando.split(" ", 2);
        if (ComandoLista[0].contains("TAG")) {
            if (ComandoLista[0].equals("GET_UNIQUE_TAGS")) {
                return TagsQuerys.getUniqueTags();
            } else {
                return TagsQuerys.tags(ComandoLista[0], ComandoLista[1]);
            }
        } else {
            return SongAndArtistQuerys.songAndArtist(ComandoLista[0], ComandoLista[1]);
        }
    }

    public static String getCreativeQuery() {
        return "TOP10_MOST_VIVACITY_YEAR";
    }

    public static int getTypeOfSecondParameter() {
        return 3;
    }

    public static String getVideoUrl() {
        return "";
    }

    public static ArrayList<Song> getSongs() {
        return Song.arrayDeMusicas;
    }

    public static ParseInfo getParseInfo(String fileName) {
        return ParseInfo.hashEstatisticas.getOrDefault(fileName, new ParseInfo(fileName, 0, 0));
    }

    public static void main(String[] args) throws IOException {
        loadFiles();
        System.out.println("Welcome to DEISI Rockstar!");
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (line != null && !line.equals("KTHXBYE")) {
            long start = System.currentTimeMillis();
            String result = execute(line);
            long end = System.currentTimeMillis();
            System.out.println(result);
            System.out.println("(took " + (end - start) + " ms)");
            line = in.nextLine();
        }
        System.out.println("Adeus.");
    }
}