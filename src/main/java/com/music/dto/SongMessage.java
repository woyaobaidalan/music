package com.music.dto;

import com.music.entity.Song;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SongMessage {

    private Song song;

    private MultipartFile mpfile;

    public SongMessage(Song song, MultipartFile mpfile) {
        this.song = song;
        this.mpfile = mpfile;
    }
}
