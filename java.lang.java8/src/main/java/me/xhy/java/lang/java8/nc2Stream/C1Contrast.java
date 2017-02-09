package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.music.Artist;
import me.xhy.java.lang.materials.music.MusicData;

import java.util.Iterator;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/8.
 */
public class C1Contrast {
    public static void main(String[] args) {
        List<Artist> allArtists = MusicData.allArtists;

        // for
        int count = 0;
        for (Artist artist : allArtists) {
            if (artist.isFrom("London")) {
                count ++;
            }
        }
        System.out.println(count);

        // iterator
        count = 0;
        Iterator<Artist> iterator = allArtists.iterator();
        while(iterator.hasNext()) {
            Artist artist = iterator.next();
            if(artist.isFrom("London")) {
                count++;
            }
        }
        System.out.println(count);

        // stream
        long fromLondonCount = allArtists.stream().filter(artist -> artist.isFrom("London")).count();
        // stream 不是一个新集合，而是创建集合的配方
        System.out.println(fromLondonCount);

        // 惰性求值  代码中加入print说明
        allArtists.stream().filter(artist -> {
            System.out.println("do something?");
            return artist.isFrom("London");
        });
        // 没有及早求值方法，上面只是在调制配方，并不会使用配方来做任何事情
        // 返回 Stream 的都是惰性求值方法。理想的情况就是构造一个惰性求值链，最后在用一个及早求值得出结果。
    }


}
