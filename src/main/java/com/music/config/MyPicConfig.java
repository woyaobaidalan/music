package com.music.config;

import com.music.common.constant.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyPicConfig implements WebMvcConfigurer {

    private static final String[] PATH = new String[]{
            "/img/singerPic/**",
            "/img/songListPic/**"

    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH)
                .addResourceLocations(Constants.SINGER_PIC_PATH)
                .addResourceLocations(Constants.SONGLIST_PIC_PATH);

    }
}
