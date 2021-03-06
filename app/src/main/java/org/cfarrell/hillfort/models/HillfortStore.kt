package org.cfarrell.hillfort.models

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun findFavs(): List<HillfortModel>
    fun findById(id:Long) : HillfortModel?

}