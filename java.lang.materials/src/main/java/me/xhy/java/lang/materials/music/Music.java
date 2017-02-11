package me.xhy.java.lang.materials.music;

import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public interface Music {

    public String getName();

    public Stream<Artist> getMusicians();

    public default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(artist -> {
            return concat(Stream.of(artist), artist.getMembers());
        });
    }

}
