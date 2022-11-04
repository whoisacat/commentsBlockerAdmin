package com.whoisacat.freelance.ura.commentsBlockerAdmin.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*

@Entity
@Table(
    name = "who_user",
    uniqueConstraints = [UniqueConstraint(columnNames = ["email"])],
    indexes = [Index(columnList = "id"), Index(columnList = "email")]
)
class User {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq")
    @Column(name = "id")
    var id: Long? = null

    var firstName: String? = null

    var lastName: String? = null

    @Column(nullable = false, unique = true)
    var email: String? = null
    var password: String? = null

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "who_user_id")
    var roles: MutableSet<Role> = HashSet()

    @get:Transient
    val authorities: Collection<GrantedAuthority>
        get() {
            val authList: MutableSet<GrantedAuthority> = HashSet()
            for (role in roles) {
                authList.add(SimpleGrantedAuthority(role.roleName!!.name))
            }
            return authList
        }
}