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

    @Column(name = "path_to_audio", nullable = false, columnDefinition = "TEXT")
    var pathToAudio: String? = null

    constructor(songId: Long?, title: String?, pathToAudio: String?) {
        this.songId = songId
        this.title = title
        this.pathToAudio = pathToAudio
    }

    constructor()

    override fun toString(): String {
        return "Masterpiece{" +
                "userId=" + userId +
                ", songId='" + songId + '\'' +
                ", title='" + title + '\'' +
                ", pathToAudio='" + pathToAudio + '\'' +
                '}'
    }
}