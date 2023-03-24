package com.example.mpbackend.masterpiece

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
public class UserDetails {
    @Id
    @Column(name = "user_id")
    var userId: String? = null
    // other user fields

    @JsonIgnore
    @ManyToMany(mappedBy = "userContributions") // we will probably need this for most of our use cases
    var masterpieces: MutableList<Masterpiece> = mutableListOf()

    @ElementCollection
    val visitedMasterpieces: MutableSet<Long> = mutableSetOf()

    constructor(id: String) {
        this.userId = id
    }

    constructor()
}