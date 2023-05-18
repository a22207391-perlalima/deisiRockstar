package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class SongAndArtistQuerys {

    public static String songAndArtist(String comando, String valores) {
        switch (comando) {
            case ("COUNT_SONGS_YEAR"):
                return countSongYear(Integer.parseInt(valores));
            case ("COUNT_DUPLICATE_SONGS_YEAR"):
                return countDuplicateSongsYear(Integer.parseInt(valores));
            case ("GET_MOST_DANCEABLE"):
                return getMostDanceable(valores);
            case ("GET_ARTISTS_ONE_SONG"):
                return getArtistsOneSong(valores);
            case ("GET_TOP_ARTISTS_WITH_SONGS_BETWEEN"):
                return getTopArtistsWithSongsBetween(valores);
            case ("MOST_FREQUENT_WORDS_IN_ARTIST_NAME"):
                return mostFrequentWordsInArtistName(valores);
            case ("TOP10_MOST_VIVACITY_YEAR"):
                return top10MostVivacityYear(valores);
            case ("GET_ARTISTS_LOUDER_THAN"):
                return getArtistsLouderThan(valores);
            default:
                return "Illegal command. Try again";
        }
    }
    public static String countSongYear(int ano) {
        ArrayList<Song> songs = Main.getSongs();

        int TodosOsTemas = 0;

        for (Song song : songs) {
            if (song.anoLancamento == ano) {
                TodosOsTemas++;
            }
        }
        return Integer.toString(TodosOsTemas);
    }

    public static String getMostDanceable(String info) {

        String[] infos = info.split(" ");

        int inicio = Integer.parseInt(infos[0]);
        int fim = Integer.parseInt(infos[1]);
        int numero = Integer.parseInt(infos[2]);

        ArrayList<Song> songs = Main.getSongs();

        ArrayList<String> listGetMostDanceable = new ArrayList<>();

        songs.sort(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return Double.compare(o1.dancabilidade, o2.dancabilidade);
            }
        });

        for (int i = songs.size() - 1; i >= 0; i--) {
            if (songs.get(i).anoLancamento >= inicio && songs.get(i).anoLancamento <= fim) {

                StringBuilder builder = new StringBuilder();
                builder.append(songs.get(i).titulo).append(" : ");
                builder.append(songs.get(i).anoLancamento).append(" : ");
                builder.append(songs.get(i).dancabilidade).append("\n");
                listGetMostDanceable.add(builder.toString());

                if (listGetMostDanceable.size() == numero) {

                    builder = new StringBuilder();

                    for (String value : listGetMostDanceable) {
                        builder.append(value);
                    }

                    return builder.toString();
                }
            }
        }

        return "";
    }

    public static String countDuplicateSongsYear(int ano) {
        ArrayList<Song> listSongsAno = new ArrayList<>();
        ArrayList<Song> songs = Main.getSongs();
        int counterDuplicateSongs = 0;

        for (Song song : songs) {
            if (song.anoLancamento == ano) {
                listSongsAno.add(song);
            }
        }

        for (int i = 0; i < listSongsAno.size(); i++) {
            Song song = listSongsAno.get(i);

            for (int j = i; j < listSongsAno.size(); j++) {
                Song otherSong = listSongsAno.get(j);

                if (!song.id.equals(otherSong.id) && song.titulo.equals(otherSong.titulo)) {
                    counterDuplicateSongs++;
                    break;
                }

            }
        }
        return Integer.toString(counterDuplicateSongs);
    }

    public static String getArtistsOneSong(String info) {
        String[] infos = info.split(" ");

        int anoInicio = Integer.parseInt(infos[0]);
        int anoFim = Integer.parseInt(infos[1]);

        if (anoInicio >= anoFim) {
            return "Invalid period";
        }

        ArrayList<Artista> artistas = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());

        artistas.sort(new Comparator<Artista>() {
            @Override
            public int compare(Artista o1, Artista o2) {
                return o1.nome.compareTo(o2.nome);
            }
        });

        StringBuilder finalString = new StringBuilder();
        int counter = 0;
        for (Artista artista : artistas) {
            for (Song tema : artista.temas.values()) {
                if (tema.anoLancamento >= anoInicio && tema.anoLancamento <= anoFim) {
                    if (artista.temas.size() == 1) {
                        finalString.append(artista.nome).append(" | ");
                        finalString.append(artista.temas.get(artista.idTema).titulo).append(" | ");
                        if (++counter == artistas.size()) {
                            finalString.append(artista.temas.get(artista.idTema).anoLancamento);
                        } else {
                            finalString.append(artista.temas.get(artista.idTema).anoLancamento).append("\n");
                        }
                    }
                }
            }
        }
        return finalString.toString();
    }

    public static String getTopArtistsWithSongsBetween(String info) {
        String[] infos = info.split(" ");
        int numOcorrencias = Integer.parseInt(infos[0]);
        int numMinTemas = Integer.parseInt(infos[1]);
        int numMaxTemas = Integer.parseInt(infos[2]);

        ArrayList<Artista> listArtistas = new ArrayList<>();
        ArrayList<Artista> artistas = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());

        for (Artista artista : artistas) {
            if (artista.temas.size() >= numMinTemas && artista.temas.size() <= numMaxTemas) {
                listArtistas.add(artista);
            }
        }
        if (listArtistas.size() == 0) {
            return "No results";
        }
        listArtistas.sort(new Comparator<Artista>() {
            @Override
            public int compare(Artista o1, Artista o2) {
                return Integer.compare(o1.temas.size(), o2.temas.size());
            }
        });

        int loopSize = 0;
        loopSize = Math.min(numOcorrencias, listArtistas.size());

        StringBuilder finalString = new StringBuilder();
        for (int i = listArtistas.size() - 1; i >= listArtistas.size() - loopSize; i--) {
            finalString.append(listArtistas.get(i).nome).append(" ");
            if (i == listArtistas.size() - loopSize) {
                finalString.append(listArtistas.get(i).temas.size());
                break;
            } else {
                finalString.append(listArtistas.get(i).temas.size()).append("\n");
            }
        }

        return finalString.toString();
    }

    public static String mostFrequentWordsInArtistName(String info) {
        String[] infos = info.split(" ");
        int numPalavras = Integer.parseInt(infos[0]);
        int minSongsArtista = Integer.parseInt(infos[1]);
        int minNumPalavrasComprimento = Integer.parseInt(infos[2]);

        ArrayList<Artista> artistas = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());
        HashMap<String, Integer> OcorrenciasPalavras = new HashMap<>();

        for (Artista artista : artistas) {
            if (artista.temas.size() >= minSongsArtista) {
                String[] artistaName = artista.nome.split(" ");
                for (String palavra : artistaName) {
                    if (palavra.length() >= minNumPalavrasComprimento) {
                        Integer ocorrencias = OcorrenciasPalavras.getOrDefault(palavra, 0);
                        OcorrenciasPalavras.put(palavra, ++ocorrencias);
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(OcorrenciasPalavras.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return Integer.compare(o1.getValue(), o2.getValue());
            }
        });

        int loopInit = Math.max(list.size() - numPalavras, 0);
        StringBuilder finalString = new StringBuilder();
        for (int i = loopInit; i < list.size(); i++) {
            finalString.append(list.get(i).getKey()).append(" ").append(list.get(i).getValue());
            if (i < list.size() - 1) {
                finalString.append("\n");
            }
        }
        return finalString.toString();
    }

    public static String getArtistsLouderThan(String info) {
        String[] infos = info.split(" ");
        int ano = Integer.parseInt(infos[0].trim());
        double ruido = Double.parseDouble(infos[1].trim());
        ArrayList<Artista> todosOsArtistas = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());
        HashSet<String> artistasValidosParaApresentar = new HashSet<>();
        StringBuilder finalString = new StringBuilder();
        for (Artista artistaSelecionado : todosOsArtistas) {
            for (Song musica : artistaSelecionado.temas.values()) {
                if (musica.anoLancamento == ano) {
                    if (musica.volumeMedio > ruido && musica.volumeMedio < 0) {
                        artistasValidosParaApresentar.add(artistaSelecionado.nome);
                    }
                }
            }
        }
        for (Iterator<String> it = artistasValidosParaApresentar.iterator(); it.hasNext(); ) {
            String nomeArtistaSelecionado = it.next();
            if (!it.hasNext()) {
                finalString.append(nomeArtistaSelecionado);
            } else {
                finalString.append(nomeArtistaSelecionado).append("\n");
            }
        }
        return finalString.toString();
    }

    public static String top10MostVivacityYear(String info) {
        String[] infos = info.split(" ");
        int ano = Integer.parseInt(infos[0]);
        double vivacidadeValor = Double.parseDouble(infos[1]);

        ArrayList<Song> songs = Main.getSongs();
        ArrayList<Song> listGetMostVivacity = new ArrayList<>();

        for (Song song : songs) {
            if (song.vivacidade >= vivacidadeValor && song.anoLancamento == ano) {
                listGetMostVivacity.add(song);
            }
        }

        if (listGetMostVivacity.size() == 0) {
            return "No results";
        }

        listGetMostVivacity.sort(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return Double.compare(o1.vivacidade, o2.vivacidade);
            }
        });

        StringBuilder finalString = new StringBuilder();
        for (int i = listGetMostVivacity.size() - 1; i >= listGetMostVivacity.size() - 10; i--) {
            finalString.append(listGetMostVivacity.get(i).titulo).append(" : ");
            finalString.append(listGetMostVivacity.get(i).anoLancamento).append(" : ");
            finalString.append(listGetMostVivacity.get(i).vivacidade);
            if (i > listGetMostVivacity.size() - 10) {
                finalString.append("\n");
            }
        }

        return finalString.toString();
    }
}
