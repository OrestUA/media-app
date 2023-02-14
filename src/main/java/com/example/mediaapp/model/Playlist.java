package com.example.mediaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NamedNativeQuery;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Data
@Entity
@Table(name = "playlist")
public class Playlist {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private BigInteger id;

    private String name;

    @Column(name = "created_on")
    @JsonProperty("created_on")
    private Date createdOn;

    @ElementCollection(targetClass = HashSet.class)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private Collection<Song> songs;
}
