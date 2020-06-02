package vn.dylanbui.dbkotlinapp.commons


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/2/20
 * To change this template use File | Settings | File and Code Templates.
 */

sealed class DbResult<out T : Any> {
    class Success<out T : Any>(
        val data: T
    ) : DbResult<T>()

    object Loading : DbResult<Nothing>()
    object Empty : DbResult<Nothing>()
    class Error(val code: Int, val message: String) : DbResult<Nothing>()
}

inline fun <reified T : Any> successResult(data: T) = DbResult.Success(data)
fun loading() = DbResult.Loading
fun emptyResult() = DbResult.Empty
fun errorResult(code: Int, message: String) = DbResult.Error(code, message)


/*

interface IConvertableTo<T> {
    fun convertTo(): T?
}

inline fun <reified O : Any, reified I : IConvertableTo<O>> SResult<List<I>>.mapListTo(): SResult<List<O>> {
    return when (this) {
        is SResult.Success -> {
            successResult(this.data.mapNotNull { it.convertTo() })
        }
        is SResult.Empty -> this
        is SResult.Loading -> this
        is SResult.Error -> this
    }
}

inline fun <reified O : Any, reified I : IConvertableTo<O>> SResult<I>.mapTo(): SResult<O> {
    return when (this) {
        is SResult.Success -> {
            this.data.convertTo()?.let {
                successResult(it)
            } ?: emptyResult()
        }
        is SResult.Empty -> this
        is SResult.Loading -> this
        is SResult.Error -> this
    }
}



* */
