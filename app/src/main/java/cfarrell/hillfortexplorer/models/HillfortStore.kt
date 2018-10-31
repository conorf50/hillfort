package cfarrell.hillfortexplorer.models

interface HillfortStore {
  fun findAll(): List<HillfortModel>
  fun create(hillfort: HillfortModel)
  fun update(hilfort: HillfortModel)
  fun delete(hillfort: HillfortModel)
}