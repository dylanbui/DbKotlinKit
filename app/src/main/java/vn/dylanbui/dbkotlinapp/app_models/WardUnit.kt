package vn.dylanbui.dbkotlinapp.app_models

import com.vicpin.krealmextensions.equalToValue
import com.vicpin.krealmextensions.querySorted
import io.realm.RealmObject
import io.realm.Sort
import io.realm.annotations.PrimaryKey

open class WardUnit : RealmObject() {

    @PrimaryKey
    var wardId: Int? = null

    var countryId: Int? = null
    var regionId: Int ? = null
    var cityId: Int ? = null
    var districtId: Int ? = null
    var wardName: String? = null
    var wardNameEn: String? = null
    var wardNameLower: String? = null
    var wardShortName: String? = null
    var orders: Int? = null

    companion object {

        fun getListWardFromDistrict(districtId: Int): List<WardUnit> {
            return WardUnit().querySorted("orders", Sort.DESCENDING) {
                equalToValue("districtId", districtId)
            }
        }

    }
}
