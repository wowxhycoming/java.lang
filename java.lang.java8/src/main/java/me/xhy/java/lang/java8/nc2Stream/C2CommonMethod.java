package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.music.Album;
import me.xhy.java.lang.materials.music.MusicData;
import me.xhy.java.lang.materials.music.Track;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by xuhuaiyu on 2017/2/8.
 * 常用操作
 */
public class C2CommonMethod {

    public static void main(String[] args) {

        // toList ，从 Stream 生成一个列表
        List<String> collected = Stream.of("a", "b", "c", "d").collect(Collectors.toList());
        collected.forEach(System.out::println);
        collected.forEach((String t) -> System.out.println(t));

        // map - for
        collected = new ArrayList<>();
        for (String s : asList("a", "b", "c", "d")) {
            String uppercaseString = s.toUpperCase();
            collected.add(uppercaseString);
        }
        for(String s : collected) {
            System.out.println(s);
        }

        // map 的参数为 Function<T, R> T R ， 生成一个新的值代替 Stream 中的值
        collected = Stream.of("a", "b", "c", "d").map(s -> s.toLowerCase()).collect(toList());
        collected.forEach(System.out::println);
        collected = collected.stream().map(String::toUpperCase).collect(toList());
        collected.forEach(System.out::println);

        // filter 的参数为 Predicate<T> T boolean
        collected = Stream.of("1a", "b", "c", "d").filter(e -> isDigit(e.charAt(0))).collect(toList());
        collected.forEach(System.out::println);

        // flatMap 的参数为
        // 非扁平化处理
        List<List<Integer>> togetherFalse = Stream.of(asList(1,2), asList(3,4)).collect(toList());
        togetherFalse.forEach(e -> System.out.println(e.getClass().getName()));
        // 扁平化处理
        List<Integer> together = Stream.of(asList(1,2), asList(3,4)).flatMap(member -> member.stream()).collect(toList());
        together.forEach(System.out::println);

        // min 和 max
        List<Track> tracks = MusicData.getSomeTracks();
        // 返回 Optional 对象， Optional 是一个可能存在的值, 通过 get() 方法获取值。
        Track shortestTrack = tracks.stream().min(Comparator.comparing(track -> track.getLength())).get();
	    System.out.println(shortestTrack.getName() + shortestTrack.getLength());

        // reduce ， count、min、max 都是 reduce 。
        int count = Stream.of(1,2,3).reduce(0, (acc, element) -> acc + element);
	    System.out.println(count);

        // 一个整合操作 ： 找出专辑上所有乐队的国籍 。 用toList收集，会产生重复数据，这里使用toSet收集
        Album aLoveSupreme = MusicData.aLoveSupreme;
        Set<String> nationals = aLoveSupreme.getMusicians().filter(artist -> artist.getMembers().count() > 1)
                .map(artist -> artist.getNationality())
                .collect(toSet());
        nationals.stream().forEach(System.out::println);

        // 重构遗留代码 ：找出长度大于60秒的曲目 。 数据准备
        List<Album> albums = MusicData.getSomeAlbums();
	    Set<String> trackNames = new HashSet<>();
        for(Album album : albums) {
            for(Track track : album.getTrackList()) {
                System.out.println(album.getName() + ":" + track.getName() + ":" + track.getLength());
                if(track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }

        trackNames = albums.stream().flatMap(album -> album.getTrackList().stream())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .collect(toSet());









    }


}
