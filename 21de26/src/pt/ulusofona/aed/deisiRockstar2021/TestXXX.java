package pt.ulusofona.aed.deisiRockstar2021;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestXXX {

    public void readFiles() throws IOException {
        Song.arrayDeMusicas.clear();
        Song.hashMusicas.clear();
        Artista.hashArtistas.clear();
        ParseInfo.hashEstatisticas.clear();

        FicheiroLeitura.readFileSongs("test-files/songs.txt");
        FicheiroLeitura.readFileSongsArtists("test-files/song_artists.txt");
        FicheiroLeitura.lerDetalhes("test-files/song_details.txt");
    }

    @Test
    public void testToStringSongs() {
        Song musica = new Song();
        musica.id = "445bdef";
        musica.titulo = "Teste";
        musica.anoLancamento = 2021;
        musica.duracao = 354051;
        musica.popularidade = 51;
        musica.artistas = new ArrayList<>();
        musica.artistas.add(new Artista("123456789abcd", "Artista"));
        String resultadoReal = musica.toString();
        String resultadoEsperado = "445bdef | Teste | 2021 | 5:54 | 51.0 | Artista | (0)";
        assertEquals("O toString() da Songs tem que ser [id | titulo | ano | duracao | popularidade | artistas]", resultadoEsperado, resultadoReal);
    }

    @Test
    public void testToStringParseInfo() {
        ParseInfo info = new ParseInfo(3, 0);
        String resultadoReal = info.toString();
        String resultadoEsperado = "OK: 3, Ignored: 0";
        assertEquals("O toString() do ParseInfo tem que ser [OK: -X-, Ignored: -X-]", resultadoEsperado, resultadoReal);
    }

    @Test
    public void testCountSongsByYear() throws IOException {
        readFiles();
        String resultado = Main.execute("COUNT_SONGS_YEAR 2013");
        assertEquals("3", resultado);
    }

    @Test
    public void testCountDuplicateByYear() throws IOException {
        readFiles();
        String resultado = Main.execute("COUNT_DUPLICATE_SONGS_YEAR 2012");
        assertEquals("1", resultado);
    }

    @Test
    public void testGetArtistsByTag() throws IOException {
        readFiles();
        Main.execute("ADD_TAGS Madonna;ROCK");
        String resultado = Main.execute("GET_ARTISTS_FOR_TAG ROCK");
        assertEquals("Madonna", resultado);
    }

    @Test
    public void testGetMostDancable() throws IOException {
        readFiles();
        String resultado = Main.execute("GET_MOST_DANCEABLE 2012 2013 3");
        assertEquals(3, resultado.split("\n").length);
    }

    @Test
    public void testGetTopArtistsWithSongsBetween() throws IOException {
        readFiles();
        String resultado = Main.execute("GET_TOP_ARTISTS_WITH_SONGS_BETWEEN 3 0 2");
        assertEquals(1, resultado.split("\n").length);
    }

    @Test
    public void testMostFrequentWordInArtistName() throws IOException {
        readFiles();
        String resultado = Main.execute("MOST_FREQUENT_WORDS_IN_ARTIST_NAME 2 1 6");
        assertEquals(1, resultado.split("\n").length);
    }

    @Test
    public void testGetUniqueTags() throws IOException {
        readFiles();
        Main.execute("ADD_TAGS Supertramp;ROCK");
        String resultado = Main.execute("GET_UNIQUE_TAGS");
        assertEquals("No results", resultado);
    }

    @Test
    public void testAddTags() throws IOException {
        readFiles();
        String resultado = Main.execute("ADD_TAGS Supertramp;ROCK");
        assertEquals("Inexistent artist", resultado);
    }

    @Test
    public void testRemoveTags() throws IOException {
        readFiles();
        Main.execute("ADD_TAGS Supertramp;ROCK");
        String resultado = Main.execute("REMOVE_TAGS Supertramp;ROCK");
        assertEquals("Inexistent artist", resultado);
    }


    @Test
    public void teste12() throws IOException {

    }

    @Test
    public void teste13() throws IOException {

    }
}
