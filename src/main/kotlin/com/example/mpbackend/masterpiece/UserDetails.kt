package com.example.mpbackend.masterpiece

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
public class UserDetails {
    @Id
    @Column(name = "user_id")
    var userId: Long? = null
    // other user fields

    @JsonIgnore
    @ManyToMany(mappedBy = "userContributions") // we will probably need this for most of our use cases
    var masterpieces: MutableSet<Masterpiece> = mutableSetOf()

    constructor(id: Long) {
        this.userId = id
    }

    constructor()
}