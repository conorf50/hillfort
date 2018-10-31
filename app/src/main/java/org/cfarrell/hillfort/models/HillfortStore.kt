package org.cfarrell.hillfort.models

interface HillfortStore {
  fun findAll(): List<HillfortModel>
  fun create(hillfort: HillfortModel)
  fun update(hillfort: HillfortModel)
}