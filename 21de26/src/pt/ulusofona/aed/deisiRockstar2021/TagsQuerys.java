package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class TagsQuerys {
    
    public static String tags(String comando, String valores) {
        switch (comando) {
            case ("GET_ARTISTS_FOR_TAG"):
                return getArtistsForTag(valores);
            case ("ADD_TAGS"):
                return addTags(valores);
            case ("REMOVE_TAGS"):
                return removeTags(valores);
            case ("GET_UNIQUE_TAGS"):
                return getUniqueTags();
            case ("GET_UNIQUE_TAGS_IN_BETWEEN_YEARS"):
                return getUniqueTagsBetweenYears(valores);
            case ("GET_ARTISTS_MOST_TAGS"):
                return getArtistsMostTags(valores);
            default:
                return "Illegal command. Try again";
        }
    }

    public static String addTags(String valor) {

        String[] linha = valor.split(";");
        LinkedHashMap<String, Artista> artists = FicheiroLeitura.buscarArtistas();
        Artista artista = artists.get(linha[0].trim());

        if (artista == null) {
            return "Inexistent artist";
        }

        StringBuilder finalString = new StringBuilder(linha[0].trim() + " | ");
        HashSet<String> artistasTags = artista.tags;

        for (int i = 1; i < linha.length; i++) {
            String tag = linha[i].toUpperCase().trim();
            artistasTags.add(tag);
        }

        for (String tag : artistasTags) {
            finalString.append(tag).append(",");
        }

        artista.tags = artistasTags;
        finalString.deleteCharAt(finalString.length() - 1);

        return finalString.toString();
    }

    public static String removeTags(String value) {
        String[] linha = value.split(";");

        LinkedHashMap<String, Artista> artistas = FicheiroLeitura.buscarArtistas();
        Artista artista = artistas.get(linha[0].trim());

        if (artista == null) {
            return "Inexistent artist";
        }

        StringBuilder finalString = new StringBuilder(linha[0].trim() + " | ");
        HashSet<String> artistaTags = artista.tags;

        for (int i = 1; i < linha.length; i++) {
            String tagName = linha[i].trim().toUpperCase();
            artistaTags.remove(tagName);
        }

        if (artistaTags.size() > 0) {
            for (String tag : artistaTags) {
                finalString.append(tag).append(",");
            }
        } else {
            finalString.append("No tags ");
        }

        artista.tags = artistaTags;
        finalString.deleteCharAt(finalString.length() - 1);

        return finalString.toString();
    }

    public static String getArtistsForTag(String tag) {

        ArrayList<String> listArtistas = new ArrayList<>();
        ArrayList<Artista> artists = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());

        for (Artista artista : artists) {
            for (String tags : artista.tags) {
                if (tags.equals(tag.toUpperCase())) {
                    listArtistas.add(artista.nome);
                }
            }
        }

        if (listArtistas.size() == 0) {
            return "No results";
        }

        Collections.sort(listArtistas);

        return String.join(";", listArtistas);
    }

    public static String getUniqueTags() {
        ArrayList<Artista> artistas = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());
        HashMap<String, Integer> tagsOcorrencias = new HashMap<>();

        for (Artista artist : artistas) {
            for (String tag : artist.tags) {
                if (tag.length() > 1) {
                    Integer ocorrencias = tagsOcorrencias.getOrDefault(tag, 0);
                    tagsOcorrencias.put(tag, ++ocorrencias);

                }
            }
        }

        if (tagsOcorrencias.size() == 0) {
            return "No results";
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(tagsOcorrencias.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return Integer.compare(o1.getValue(), o2.getValue());
            }
        });

        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            finalString.append(list.get(i).getKey()).append(" ").append(list.get(i).getValue());
            if (i < list.size() - 1) {
                finalString.append("\n");
            }
        }
        return finalString.toString();
    }

    public static String getUniqueTagsBetweenYears(String info) {
        String[] infos = info.split(" ");
        int inicioAno = Integer.parseInt(infos[0]);
        int FimAno = Integer.parseInt(infos[1]);

        ArrayList<Artista> artistas = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());
        HashMap<String, Integer> tagsOcorrencias = new HashMap<>();

        boolean artistaJaLido = false;
        for (Artista artista : artistas) {
            for (Song tema : artista.temas.values()) {
                if (tema.anoLancamento >= inicioAno && tema.anoLancamento <= FimAno) {
                    for (String tag : artista.tags) {
                        Integer ocorrencias = tagsOcorrencias.getOrDefault(tag, 0);
                        tagsOcorrencias.put(tag, ++ocorrencias);
                        artistaJaLido = true;
                    }
                }
                if (artistaJaLido) {
                    break;
                }
            }
        }
        if (tagsOcorrencias.size() == 0) {
            return "No results";
        }
        List<Map.Entry<String, Integer>> list = new LinkedList<>(tagsOcorrencias.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return Integer.compare(o1.getValue(), o2.getValue());
            }
        });

        StringBuilder finalString = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            finalString.append(list.get(i).getKey()).append(" ").append(list.get(i).getValue());
            if (i > 0) {
                finalString.append("\n");
            }
        }
        return finalString.toString();
    }

    public static String getArtistsMostTags(String info) {
        int tamanhoMinimoDaTag = Integer.parseInt(info.trim());

        ArrayList<Artista> todosOsArtistas = new ArrayList<>(FicheiroLeitura.buscarArtistas().values());
        HashMap<String, ArrayList<Integer>> numeroDeTagsPorArtista = new HashMap<>();

        for (Artista artistaSelecionado : todosOsArtistas) {
            int contadorTagsValidas = 0;
            ArrayList<Integer> listaDasTagsTotalEValidas = new ArrayList<>(2);
            if (artistaSelecionado.tags.size() > 0) {
                listaDasTagsTotalEValidas.add(artistaSelecionado.tags.size());
                for (String tagSelecionada : artistaSelecionado.tags) {
                    if (tagSelecionada.length() >= tamanhoMinimoDaTag) {
                        contadorTagsValidas++;
                    }
                }
                listaDasTagsTotalEValidas.add(contadorTagsValidas);
                numeroDeTagsPorArtista.put(artistaSelecionado.nome, listaDasTagsTotalEValidas);
            }
        }

        if (numeroDeTagsPorArtista.size() == 0) {
            return "No results";
        }

        List<Map.Entry<String, ArrayList<Integer>>> list = new LinkedList<>(numeroDeTagsPorArtista.entrySet());
        list.sort(new Comparator<Map.Entry<String, ArrayList<Integer>>>() {
            public int compare(Map.Entry<String, ArrayList<Integer>> o1, Map.Entry<String, ArrayList<Integer>> o2) {
                return Integer.compare(o1.getValue().get(1), o2.getValue().get(1));
            }
        }.reversed());

        list.sort(new Comparator<Map.Entry<String, ArrayList<Integer>>>() {
            public int compare(Map.Entry<String, ArrayList<Integer>> o1, Map.Entry<String, ArrayList<Integer>> o2) {
                if (o1.getValue().get(1).equals(o2.getValue().get(1))) {
                    return Integer.compare(o1.getValue().get(0), o2.getValue().get(0));
                }
                return 0;
            }
        });

        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            finalString.append(list.get(i).getKey()).append(" : ");
            finalString.append(list.get(i).getValue().get(0)).append(" : ");
            finalString.append(list.get(i).getValue().get(1));
            if (i < list.size() - 1) {
                finalString.append("\n");
            }
        }
        return finalString.toString();
    }

}
