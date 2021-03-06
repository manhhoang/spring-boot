package com.spring_standalone.service;

import com.spring_standalone.model.Album;
import com.spring_standalone.model.AlbumMatches;
import com.spring_standalone.model.LastMusic;
import com.spring_standalone.model.Music;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class LastMusicServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LastMusicService lastMusicService;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(lastMusicService);
    }

    @Test(expected=ExecutionException.class)
    public void testSearchException() throws ExecutionException, InterruptedException {
        String url = "nullnull";
        String albumName = "Believe";
        when(restTemplate.getForEntity(url, LastMusic.class, albumName)).thenReturn(new ResponseEntity(new LastMusic(), HttpStatus.OK));
        CompletableFuture<LastMusic> future = lastMusicService.search(albumName);
        future.get();
    }

    @Test
    public void testSearchSuccess() throws ExecutionException, InterruptedException {
        String url = "nullnull";
        String albumName = "Believe";
        LastMusic lastMusic = new LastMusic();
        Music music = new Music();
        AlbumMatches albumMatches = new AlbumMatches();
        Album album = new Album();
        album.setName("Believe");
        album.setArtist("Justin Bieber");
        albumMatches.addAlbum(album);
        music.setAlbumMatches(albumMatches);
        lastMusic.setMusic(music);
        when(restTemplate.getForEntity(url, LastMusic.class, albumName)).thenReturn(new ResponseEntity(lastMusic, HttpStatus.OK));
        CompletableFuture<LastMusic> future = lastMusicService.search(albumName);
        LastMusic result = future.get();
        Assert.assertEquals(result.getMusic().getAlbumMatches().getAlbums().size(), 1);
        Assert.assertEquals(result.getMusic().getAlbumMatches().getAlbums().get(0).getName(), "Believe");
        Assert.assertEquals(result.getMusic().getAlbumMatches().getAlbums().get(0).getArtist(), "Justin Bieber");
    }
}
