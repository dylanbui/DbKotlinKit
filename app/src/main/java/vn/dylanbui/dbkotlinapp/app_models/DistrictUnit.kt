package vn.dylanbui.dbkotlinapp.app_models

import com.google.gson.annotations.SerializedName
import com.vicpin.krealmextensions.equalToValue
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.querySorted
import io.realm.RealmObject
import io.realm.Sort
import io.realm.annotations.PrimaryKey


open class DistrictUnit : RealmObject() {

    @PrimaryKey
    @SerializedName(value = "districtId", alternate = arrayOf("id"))
    var districtId: Int? = null

    var countryId: Int? = null
    var regionId: Int? = null
    var cityId: Int? = null
    var orders: Int? = null

    @SerializedName(value = "default_name", alternate = arrayOf("name", "districtName"))
    var districtName: String? = null
    var districtShortName: String? = null
    var districtNameEn: String? = null
    var districtNameEnLower: String? = null
    var districtNameLower: String? = null
    var districtShortNameLower: String? = null

    var isSelected: Boolean? = null

    var isPrefered: Boolean? = null

    override fun equals(other: Any?): Boolean {
        if (other !is DistrictUnit) return false
        return other.districtId == this.districtId
    }

    override fun hashCode(): Int {
        var result = districtId ?: 0
        result = 31 * result + (countryId ?: 0)
        result = 31 * result + (regionId ?: 0)
        result = 31 * result + (cityId ?: 0)
        result = 31 * result + (orders ?: 0)
        result = 31 * result + (districtName?.hashCode() ?: 0)
        result = 31 * result + (districtShortName?.hashCode() ?: 0)
        result = 31 * result + (districtNameEn?.hashCode() ?: 0)
        result = 31 * result + (districtNameEnLower?.hashCode() ?: 0)
        result = 31 * result + (districtNameLower?.hashCode() ?: 0)
        result = 31 * result + (districtShortNameLower?.hashCode() ?: 0)
        result = 31 * result + (isSelected?.hashCode() ?: 0)
        return result
    }

    /*
        val arrDistrict = DistrictUnit.getListDistrictFromCity(1)
        dLog(" district.size = ${arrDistrict.size.toString()}")

        val district = DistrictUnit.getDistrictByName("Quận 1")
        district?.let {
            dLog(" district.name = ${it.districtName}")
        }
    * */

    companion object {

        val DISTRICT_ALL_ID = -1

//        fun generateDistrictUnitAllSelection(context: Context): DistrictUnit {
//            val district = DistrictUnit()
//            district.districtId = DISTRICT_ALL_ID
//            district.districtName = context.getString(R.string.common_all)
//            district.isSelected = true
//            return district
//        }

        fun getListDistrictFromCity(cityId: Int): List<DistrictUnit> {
            return DistrictUnit().querySorted("orders", Sort.ASCENDING) {
                equalToValue("cityId", cityId)
            }
        }

        fun getListDistrictFromIds(districtIds: List<Int>): List<DistrictUnit> {
            return DistrictUnit().querySorted("orders", Sort.ASCENDING) {
                `in`("districtId", districtIds.toTypedArray())
            }
        }

        fun getDistrictByName(name: String): DistrictUnit? {
            return DistrictUnit().queryFirst {
                like("districtName", name)
                // like("districtName", name, Case.INSENSITIVE)
                // equalToValue("cityId", 1)
            }
        }

    }
}