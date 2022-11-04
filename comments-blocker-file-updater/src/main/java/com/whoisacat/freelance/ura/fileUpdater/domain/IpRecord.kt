package com.whoisacat.freelance.ura.fileUpdater.domain

import javax.persistence.*

@Entity
@Table(name = "ip_records")
class IpRecord(id: Long? = null,
    ip: String,
    country: String? = null,
    city: String? = null) {

    @Id
    @SequenceGenerator(name = "ip_record_seq", sequenceName = "ip_record_seq", allocationSize = 1)
    @GeneratedValue(generator = "ip_record_seq")
    @Column(name = "id")
    var id: Long? = id
        private set

    @Column(name = "ip", unique = true)
    var ip: String = ip
        private set

    @Column(name = "country")
    var country: String? = country
        private set

    @Column(name = "city")
    var city: String? = city
        private set
}