package com.whoisacat.freelance.ura.commentsBlockerAdmin.domain

import javax.persistence.*

@Entity
@Table(name = "who_role")
class Role {
    @Id
    @SequenceGenerator(name = "who_role_seq", sequenceName = "who_role_seq", allocationSize = 1)
    @GeneratedValue(generator = "who_role_seq")
    @Column(name = "id")
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var roleName: ROLES? = null
}