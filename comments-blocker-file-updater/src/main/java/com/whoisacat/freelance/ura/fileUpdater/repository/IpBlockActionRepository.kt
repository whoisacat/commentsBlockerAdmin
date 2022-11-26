package com.whoisacat.freelance.ura.fileUpdater.repository

import com.whoisacat.freelance.ura.fileUpdater.domain.IpBlockAction
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

@ConditionalOnProperty(value = ["com.whoisacat.commentsBlocker.service.use"], havingValue = "db")
interface IpBlockActionRepository : CrudRepository<IpBlockAction, Long?> {

    fun findPageByIsActiveIsTrueAndRecord_IdIsIn(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun findPageByRecord_IdIsIn(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun findAllByIsActiveIsTrueOrderByStartTimeDesc(pageRequest: Pageable): Page<IpBlockAction>
    fun getAllByOrderByStartTimeDesc(pageRequest: Pageable): Page<IpBlockAction>
    fun getPageByIsSynchronizedIsFalseAndEndTimeIsNullOrderByStartTimeDesc(pageRequest: Pageable)
            : Page<IpBlockAction>
    fun getPageByIsSynchronizedIsFalseAndEndTimeIsNotNullOrderByStartTimeDesc(pageRequest: Pageable)
            : Page<IpBlockAction>
}

