package com.example.mpbackend.masterpiece

import javax.persistence.*

@Entity(name = "Masterpiece")
@Table(name = "masterpiece")
class Masterpiece {
    @Id
    @SequenceGenerator(name = "masterpiece_sequence", sequenceName = "masterpiece_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterpiece_sequence")
    @Column(name = "user_id", updatable = false)
    var userId: Long? = null

    @Column(name = "song_id", updatable = false)
    var songId: Long? = null

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    var title: String? = null

    @Column(name = "neededInstruments", nullable = false, columnDefinition = "TEXT")
    var neededInstruments: String? = null

    @Column(name = "bpm", nullable = false)
    var bpm: Int? = null

    @Column(name = "key", nullable = false, columnDefinition = "TEXT")
    var key: String? = null

    @Column(name = "paths_to_audio", nullable = false, columnDefinition = "TEXT")
    var pathsToAudio: String? = null

    @Column(name = "snippet_titles", nullable = false, columnDefinition = "TEXT")
    var snippetTitles: String? = null

    @Column(name = "volumes", nullable = false, columnDefinition = "TEXT")
    var volumes: String? = null

    constructor(
        songId: Long?,
        title: String?,
        pathsToAudio: String?,
        snippetTitles: String?,
        neededInstruments: String?,
        bpm: Int?,
        key: String?,
        volumes: String?,
    ) {
        this.songId = songId
        this.title = title
        this.pathsToAudio = pathsToAudio
        this.snippetTitles = snippetTitles
        this.neededInstruments = neededInstruments
        this.bpm = bpm
        this.key = key
        this.volumes = volumes
    }

    constructor()

    override fun toString(): String {
        return "Masterpiece{" +
                "userId=" + userId +
                ", songId='" + songId + '\'' +
                ", title='" + title + '\'' +
                ", pathsToAudio='" + pathsToAudio + '\'' +
                ", snippetTitles='" + snippetTitles + '\'' +
                ", neededInstruments='" + neededInstruments + '\'' +
                ", bpm='" + bpm + '\'' +
                ", key='" + key + '\'' +
                ", volumes='" + volumes + '\'' +
                '}'
    }
}