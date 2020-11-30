package vn.dylanbui.dbkotlinapp.networking

import com.vicpin.krealmextensions.delete
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


//interface ICacheManager {
//
//    fun existed(key: String): Boolean
//
//    fun get(key: String): Any?
//    // lifeTime is second
//    fun put(key: String, value: Any, lifeTime: Int, tag: String? = null): Boolean
//
//    fun remove(key: String)
//
//    fun removeTag(tag: String) {}
//
//    fun removeAll() {}
//
//    fun removeExpiryKey() {}
//
//
//}


open class DbRealmCache : RealmObject() {

    @PrimaryKey
    var key: String = ""

    var value: String = ""
    var tag: String? = null

    var expiryTimeSeconds: Int = 0
    var creationTimestamp: Int = 0
    var expiryTimestamp: Int = 0
    var softExpiry: Boolean = false

    open fun isExpired(): Boolean {
        return expiryTimeSeconds >= 0 && expiryTimestamp < (System.currentTimeMillis() / 1000L).toInt()
    }

    open fun isSoftExpired(): Boolean {
        return isExpired() && softExpiry
    }

    companion object {
        fun build(key: String = "", value: String = "", expiryTimeSeconds: Int = 0, tag: String? = null): DbRealmCache {
            val cache = DbRealmCache()
            cache.key = key
            cache.value = value
            cache.expiryTimeSeconds = expiryTimeSeconds
            cache.creationTimestamp = (System.currentTimeMillis() / 1000L).toInt()
            cache.expiryTimestamp = if (expiryTimeSeconds <= 0) -1 else cache.creationTimestamp + expiryTimeSeconds
            cache.tag = tag
            // cache.save()
            return cache
        }
    }

}



class DbRealmCacheManager: ICacheManager {

    override fun existed(key: String): Boolean {
        return get(key) != null
    }

    override fun get(key: String): Any? {
        // Condition
        // key = "" && expiryTimeSeconds >= 0 && expiryTimestamp < (System.currentTimeMillis() / 1000L).toInt()
        val obj: DbRealmCache? = DbRealmCache().queryFirst {
            equalTo("key", key)
                .and().greaterThanOrEqualTo("expiryTimeSeconds", 0)
                .and().greaterThanOrEqualTo("expiryTimestamp", (System.currentTimeMillis() / 1000L).toInt())
            // .and().equalToValue("tag", "sds")
            // .or().like("districtName", name, Case.INSENSITIVE)
            // .and().equalToValue("cityId", 1)
        }

        if (obj != null) {
            return  obj.value
        }
        return null
    }

    override fun put(key: String, value: Any, lifeTime: Int, tag: String?): Boolean {
        val data = value as? String
        if (data != null) {
            DbRealmCache.build(key, data, lifeTime, tag).save()
            return true
        }
        return false
    }

    override fun remove(key: String) {
        DbRealmCache().delete {
            equalTo("key", key)
        }
    }

    override fun removeTag(tag: String) {
        DbRealmCache().delete {
            equalTo("tag", tag)
        }
    }

    override fun removeAll() {
        DbRealmCache().deleteAll()
    }

    override fun removeExpiryKey() {
        // Condition ExpiryKey
        // expiryTimeSeconds >= 0 && expiryTimestamp >= (System.currentTimeMillis() / 1000L).toInt()
        DbRealmCache().delete {
            greaterThanOrEqualTo("expiryTimeSeconds", 0)
                .and().greaterThanOrEqualTo("expiryTimestamp", (System.currentTimeMillis() / 1000L).toInt())
        }
    }

}

