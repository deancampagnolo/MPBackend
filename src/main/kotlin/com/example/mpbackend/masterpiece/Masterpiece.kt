package com.example.mpbackend.masterpiece

import javax.persistence.*

@Entity(name = "Masterpiece")
@Table(name = "masterpiece")
class Masterpiece {
    @Id
    @SequenceGenerator(name = "masterpiece_sequence", sequenceName = "masterpiece_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterpiece_sequence")
    @Column(name = "song_id", updatable = false)
    var songId: Long? = null

    @Column(name = "user_id", updatable = false)
    var userId: String? = null

    @Column(name = "date_created", updatable = false)
    var dateCreated: Long? = null

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    var title: String? = null

    @Column(name = "neededInstruments", nullable = false, columnDefinition = "TEXT")
    var neededInstruments: String? = null

    @Column(name = "bpm", nullable = false)
    var bpm: Int? = null

    @Column(name = "key", nullable = false, columnDefinition = "TEXT")
    var key: String? = null

    @Column(name = "previewUrl", nullable = false, columnDefinition = "TEXT")
    var previewUrl: String? = null

    @Column(name = "paths_to_audio", nullable = false, columnDefinition = "TEXT")
    var pathsToAudio: String? = null

    @Column(name = "snippet_titles", nullable = false, columnDefinition = "TEXT")
    var snippetTitles: String? = null

    @Column(name = "volumes", nullable = false, columnDefinition = "TEXT")
    var volumes: String? = null

    @Column(name = "nudgeObjects", nullable = false, columnDefinition = "TEXT")
    var nudgeObjects: String? = null

    @ManyToMany(fetch = FetchType.EAGER) // We will probably need this for most of our use cases
    @JoinTable(
        name = "user_contributions",
        joinColumns = [JoinColumn(name = "masterpiece_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var userContributions: MutableList<UserDetails> = mutableListOf()


    constructor(
        userId: String?,
        dateCreated: Long?,
        title: String?,
        pathsToAudio: String?,
        snippetTitles: String?,
        neededInstruments: String?,
        bpm: Int?,
        key: String?,
        previewUrl: String?,
        volumes: String?,
        nudgeObjects: String?,
    ) {
        this.userId = userId
        this.dateCreated = dateCreated
        this.title = title
        this.pathsToAudio = pathsToAudio
        this.snippetTitles = snippetTitles
        this.neededInstruments = neededInstruments
        this.bpm = bpm
        this.key = key
        this.previewUrl = previewUrl
        this.volumes = volumes
        this.nudgeObjects = nudgeObjects
    }

    constructor()

    override fun toString(): String {
        return "Masterpiece{" +
                "songId=" + songId +
                ", userId='" + userId + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", title='" + title + '\'' +
                ", pathsToAudio='" + pathsToAudio + '\'' +
                ", snippetTitles='" + snippetTitles + '\'' +
                ", neededInstruments='" + neededInstruments + '\'' +
                ", bpm='" + bpm + '\'' +
                ", key='" + key + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", volumes='" + volumes + '\'' +
                ", nudgeObjects='" + nudgeObjects + '\'' +
                '}'
    }
}