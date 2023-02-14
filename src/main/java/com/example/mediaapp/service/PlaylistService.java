package com.example.mediaapp.service;

import com.example.mediaapp.database.PlaylistRepository;
import com.example.mediaapp.database.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepo;

    @Autowired
    private SongsRepository songRepo;

    public Iterable<Playlist> getAllPLaylists(){
        return playlistRepo.findAll();
    }

    public Playlist getPlaylistById(BigInteger playlistId){
        return getPlaylist(playlistId);
    }

    public Optional<Playlist> createPlaylist(String name){
        PLaylist playlist = new Playlist();
        playlist.setName(name);
        playlist.setCreatedOn( new Date());
        return Optional.of(playlistRepo.save(playlist));
    }

    public void deletePlaylist(BigInteger playlistId){
        Playlist playlist = getPlaylist(playlistId);
        playlist.setId(playlistId);
        playlistRepo.delete(playlist);
    }

    public Iterable<Song> getSongs(BigInteger playlistId){
        if(playlistId == null){
            return songRepo.findAll();
        }
        playlistRepo.del(playlistId);
        Playlist playlist = getPlaylist(playlistId);
        return playlistRepo.getSongs(playlist.getId());
    }

    public void deleteSongs(BigInteger playlistId){
        Playlist playlist = getPlaylist(playlistId);
        songRepo.deleteByPlaylistId(playlist.getId());
    }

    public Song addSong(BigInteger playlistId, Song song){
        Playlist playlist = getPlaylist(playlistId);
        song.setPlaylistId(playlist.getId());
        song.setCreatedOn(new Date());
        return songRepo.save(song);
    }

    public boolean moveSong(BigInteger songId, BigInteger toPlaylistId){
        Song song = get(songId);
        var playlist = getPlaylist(toPlaylistId);
        return 1 == songRepo.updatePLaylist(song.getId(), playlist.getId());
    }

    public void deleteSong(BigInteger playlistId, BigInteger songId){
        Song song = getSong(songId);
        songRepo.delete(playlistId, song.getId());
    }

    private Playlist getPlaylist(final BigInteger playlistId){
        return playlistRepo.findById(playlistId)
                .orElseThrow(()-> new PlaylistNotFoundException(playlistId));
    }

    private Song getSong(final BigInteger songId){
        return songRepo.findById(songId).orElseThrow(()-> new SongNotFoundException(songId));
    }

}
