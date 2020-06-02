package vn.dylanbui.dbkotlinapp.app_models

import com.vicpin.krealmextensions.equalToValue
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryFirst
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StreetUnit: RealmObject() {

    @PrimaryKey
    var streetId: Int? = null

    var countryId: Int? = null
    var regionId: Int? = null
    var cityId: Int? = null
    var districtId: Int? = null
    var wardId: Int? = null
    var streetName: String? = null
    var streetNameEn: String? = null
    var price: Long? = null
    var widthValue: Float? = null
    var streetNameShortName: String? = null

    companion object {

        fun getListStreetFromWard(wardId: Int): ArrayList<StreetUnit> {
            return ArrayList(StreetUnit().query {
                equalToValue("wardId", wardId)
            })
        }

        fun getStreetByWardAndName(wardId: Int, name: String): StreetUnit? {
            return StreetUnit().queryFirst {
                equalToValue("wardId", wardId)
                    .and().like("streetName", name)
                // like("districtName", name, Case.INSENSITIVE)
                // equalToValue("cityId", 1)
            }
        }

    }
}