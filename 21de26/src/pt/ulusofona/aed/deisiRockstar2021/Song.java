package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Song {
    public static ArrayList<Song> arrayDeMusicas = new ArrayList<>();
    public static LinkedHashMap<String, Song> hashMusicas = new LinkedHashMap<>();

    String id;
    String titulo;
    ArrayList<Artista> artistas;
    int anoLancamento;
    int duracao;
    int popularidade;
    double dancabilidade;
    double vivacidade;
    double volumeMedio;
    boolean letraExplicita;
    boolean temDetalhes;
    boolean temArtistas;

    public Song() {
    }

    public Song(String id, String titulo, ArrayList<Artista> artistas, int anoLancamento, int duracao,
                int popularidade, double dancabilidade, double vivacidade, double volumeMedio, boolean letraExplicita) {
        this.id = id;
        this.titulo = titulo;
        this.artistas = artistas;
        this.anoLancamento = anoLancamento;
        this.duracao = duracao;
        this.popularidade = popularidade;
        this.dancabilidade = dancabilidade;
        this.vivacidade = vivacidade;
        this.volumeMedio = volumeMedio;
        this.letraExplicita = letraExplicita;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" | ");
        builder.append(titulo).append(" | ");
        builder.append(anoLancamento).append(" | ");
        builder.append(String.format("%d:%d", (duracao / (1000 * 60)), (duracao / 1000) % 60)).append(" | ");
        builder.append(popularidade).append(".0 | ");
        if (artistas != null) {
            if (artistas.size() < 2) {
                builder.append(artistas.get(0).toString());
            } else {
                for (Artista artist : artistas) {
                    builder.append(artist.nome).append(" / ");
                }
                builder.replace(builder.length() - 3, builder.length() - 1, " | ");
                builder.replace(builder.length() - 1, builder.length(), "(");
                for (Artista artist : artistas) {
                    builder.append(artist.temas.size()).append(" / ");
                }
                builder.replace(builder.length() - 3, builder.length() - 1, ")");
            }
        }

        return builder.toString().trim();
    }
}
