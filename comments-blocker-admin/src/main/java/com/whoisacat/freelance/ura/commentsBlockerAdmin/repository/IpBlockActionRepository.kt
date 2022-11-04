package com.whoisacat.freelance.ura.commentsBlockerAdmin.repository

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpBlockAction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface IpBlockActionRepository : CrudRepository<IpBlockAction, Long?> {
    fun findPageByUser_FirstNameContainsOrUser_LastNameContains(textInFirstName: String,
        textInLasName: String, pageRequest: Pageable): Page<IpBlockAction>
    fun findPageByIsActiveIsTrueAndRecord_IdIsIn(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun findPageByRecord_IdIsIn(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun findAllByIsActiveIsTrueOrderByStartTimeDesc(pageRequest: Pageable): Page<IpBlockAction>
    fun getAllByOrderByStartTimeDesc(pageRequest: Pageable): Page<IpBlockAction>
    fun getOneByRecord_IdAndIsActiveIsTrue(id: Long): IpBlockAction?
    fun getOneById(id: Long): IpBlockAction?
}
