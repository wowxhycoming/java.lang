package me.xhy.java.lang.materials.music;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class MusicData {

    public static final Artist johnColtrane = new Artist("John Coltrane", "US");

    public static final Artist johnLennon = new Artist("John Lennon", "UK");
    public static final Artist paulMcCartney = new Artist("Paul McCartney", "UK");
    public static final Artist georgeHarrison = new Artist("George Harrison", "UK");
    public static final Artist ringoStarr = new Artist("Ringo Starr", "UK");

    public static final List<Artist> membersOfTheBeatles = Arrays.asList(johnLennon, paulMcCartney, georgeHarrison, ringoStarr);
//    public static final Artist theBeatles = new Artist("The Beatles", membersOfTheBeatles, "UK");
    public static final Artist theBeatles = new Artist("The Beatles", membersOfTheBeatles, "London");

    public static final List<Artist> allArtists = Arrays.asList(johnColtrane,johnLennon,paulMcCartney,georgeHarrison,ringoStarr,theBeatles);

    public static final Album aLoveSupreme = new Album("A Love Supreme", asList(new Track("Acknowledgement", 467), new Track("Resolution", 442)), asList(johnColtrane, theBeatles, theBeatles));

    public static final Album sampleShortAlbum = new Album("sample Short Album", asList(new Track("short track", 30)), asList(johnColtrane));

    public static final Album manyTrackAlbum = new Album("sample Short Album", asList(new Track("short track", 30), new Track("short track 2", 30), new Track("short track 3", 30), new Track("short track 4", 30), new Track("short track 5", 30)), asList(johnColtrane));

    public static Stream<Album> albums = Stream.of(aLoveSupreme);

    public static Stream<Artist> threeArtists() {
        return Stream.of(johnColtrane, johnLennon, theBeatles);
    }

    public static List<Artist> getThreeArtists() {
        return Arrays.asList(johnColtrane, johnLennon, theBeatles);
    }

    public static Track bakai = new Track("Bakai", 524);
    public static Track viloets = new Track("Viloets for Your Furs", 378);
    public static Track tiemWas = new Track("Tiem Was", 451);

    public static List<Track> getSomeTracks() {
        return asList(bakai, viloets, tiemWas);
    }

    public static List<Album> getSomeAlbums() {
        return asList(aLoveSupreme, sampleShortAlbum, manyTrackAlbum);
    }


}
