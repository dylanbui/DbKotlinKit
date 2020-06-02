package vn.dylanbui.dbkotlinapp.app_models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CityUnit : RealmObject() {

    @PrimaryKey
    var cityId: Int = 0

    var regionId: Int = 0
    var countryId: Int = 0
    var cityName: String? = null
    var cityNameEn: String? = null
    var cityShortName: String? = null
    var cityCode: String? = null
    var cityNameLower: String? = null
    var cityShortNameLower: String? = null
    var orders: Int? = null
}