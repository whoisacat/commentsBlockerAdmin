package com.whoisacat.freelance.ura.commentsBlockerAdmin.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class UserSettings {
    @Id
    @SequenceGenerator(name = "user_settings_seq", sequenceName = "user_settings_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_settings_seq")
    var id: Long? = null

    @Column(name = "rowsPerPage")
    var rowsPerPage: Int = 10

    @JsonIgnore
    @JoinColumn
    @OneToOne
    var user: User? = null
}