package com.music;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.entity.Song;
import com.music.service.SongService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
        //String password = new SimpleHash("MD5", "123", "admin", 1024).toString();
        String password =
                new SimpleHash(
                        "SHA-1",
                        "123", "admin1",
                        16).toString();
        System.out.println(password);
    }

    @Autowired
    private SongService songService;
    @Test
    void changeUrl(){
        List<Song> list = songService.list();

        for(Song song : list){
            String url = song.getUrl();
            String s = url.substring(7);
            String newUrl = "/song/" + s;
            LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Song::getUrl, url);
            Song song1 = songService.getOne(lambdaQueryWrapper);
            song1.setUrl(newUrl);

            boolean flag = songService.updateById(song1);
            System.out.println(newUrl);

        }

    }

}
