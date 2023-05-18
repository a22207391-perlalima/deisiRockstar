package pt.ulusofona.aed.deisiRockstar2021;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FicheiroLeitura {

    public static void readFileSongs(String file) throws IOException {
        Song.arrayDeMusicas.clear();
        Song.hashMusicas.clear();
        int lidas = 0, ignoradas = 0;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length == 3) {
                    if (Song.hashMusicas.containsKey(dados[0].trim())) {
                        ignoradas++;
                        continue;
                    }
                    int dataTrim;
                    try {
                        dataTrim = Integer.parseInt(dados[2].trim());
                    } catch (NumberFormatException e) {
                        ignoradas++;
                        continue;
                    }
                    if (dataTrim >= 0 && dataTrim <= 2021) {
                        Song musica = new Song();
                        musica.id = dados[0].trim();
                        musica.titulo = dados[1].replace("\"\"", "").trim();
                        musica.anoLancamento = dataTrim;
                        Song.hashMusicas.put(musica.id, musica);
                        Song.arrayDeMusicas.add(musica);
                        lidas++;
                    } else {
                        ignoradas++;
                    }
                } else {
                    ignoradas++;
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException();
        }
        ParseInfo.hashEstatisticas.put(file, new ParseInfo(file, lidas, ignoradas));
    }

    public static void readFileSongsArtists(String file) throws IOException {
        Artista.hashArtistas.clear();
        int lidas = 0, ignoradas = 0;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length == 2) {
                    if (!Song.hashMusicas.containsKey(dados[0].trim())) {
                        ignoradas++;
                        continue;
                    }
                    ArrayList<Artista> artistas = new ArrayList<>();
                    String stringArtistas = dados[1].trim();
                    stringArtistas = stringArtistas.replace("\"", "");
                    stringArtistas = stringArtistas.replace("[", "");
                    stringArtistas = stringArtistas.replace("]", "");
                    stringArtistas = stringArtistas.replace("'", "");
                    if (stringArtistas.contains(",")) {
                        String[] dadosDoArtista = stringArtistas.split(",");
                        lidas++;
                        for (String nomeDoArtistaSelecionado : dadosDoArtista) {
                            nomeDoArtistaSelecionado = nomeDoArtistaSelecionado.trim();
                            Artista artista = new Artista(dados[0].trim(), nomeDoArtistaSelecionado);
                            if (nomeDoArtistaSelecionado.equals("")) {
                                ignoradas++;
                                lidas--;
                                break;
                            }
                            if (!Artista.hashArtistas.containsKey(artista.nome)) {
                                Artista.hashArtistas.put(artista.nome, artista);
                            }
                            Artista.hashArtistas.get(artista.nome).temas.put(artista.idTema, Song.hashMusicas.get(dados[0].trim()));
                            artista.temas = Artista.hashArtistas.get(artista.nome).temas;
                            artistas.add(artista);
                        }
                    } else {
                        Artista artista = new Artista(dados[0].trim(), stringArtistas);
                        if (stringArtistas.equals("")) {
                            ignoradas++;
                            continue;
                        }
                        if (!Artista.hashArtistas.containsKey(artista.nome)) {
                            Artista.hashArtistas.put(artista.nome, artista);
                        }
                        Artista.hashArtistas.get(artista.nome).temas.put(artista.idTema, Song.hashMusicas.get(dados[0].trim()));
                        artista.temas = Artista.hashArtistas.get(artista.nome).temas;
                        artistas.add(artista);
                        lidas++;
                    }
                    Song.hashMusicas.get(dados[0].trim()).artistas = artistas;
                    Song.hashMusicas.get(dados[0].trim()).temArtistas = true;
                } else {
                    ignoradas++;
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException();
        }
        ParseInfo.hashEstatisticas.put(file, new ParseInfo(file, lidas, ignoradas));
    }

    public static void lerDetalhes(String ficheiro) throws IOException {
        int lidas = 0, ignoradas = 0;
        try {
            FileReader fr = new FileReader(ficheiro);
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length == 7) {
                    if (Song.hashMusicas.get(dados[0].trim()) == null) {
                        ignoradas++;
                        continue;
                    }
                    Song musica = Song.hashMusicas.get(dados[0].trim());
                    try {
                        musica.duracao = Integer.parseInt(dados[1].trim());
                        musica.letraExplicita = Boolean.parseBoolean(dados[2].trim());
                        musica.popularidade = Integer.parseInt(dados[3].trim());
                        musica.dancabilidade = Double.parseDouble(dados[4].trim());
                        musica.vivacidade = Double.parseDouble(dados[5].trim());
                        musica.volumeMedio = Double.parseDouble(dados[6].trim());
                        musica.temDetalhes = true;
                        lidas++;
                    } catch (NumberFormatException e) {
                        ignoradas++;
                    }
                } else {
                    ignoradas++;
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException();
        }
        ParseInfo.hashEstatisticas.put(ficheiro, new ParseInfo(ficheiro, lidas, ignoradas));
    }

    public static void copiarDeHashParaArrayMusicas() {
        Song.arrayDeMusicas.clear();
        Song.arrayDeMusicas = new ArrayList<>(Song.hashMusicas.values());
    }

    public static LinkedHashMap<String, Artista> buscarArtistas() {
        return Artista.hashArtistas;
    }
}
