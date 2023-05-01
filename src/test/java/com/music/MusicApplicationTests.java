package com.music;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
        //String password = new SimpleHash("MD5", "123", "admin", 1024).toString();
        String password =
                new SimpleHash(
                        "SHA-1",
                        "1", "admin2",
                        16).toString();
        System.out.println(password);
    }

}
