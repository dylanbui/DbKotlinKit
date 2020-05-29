package vn.dylanbui.android_core_kit.utils_extentions

import java.text.Normalizer
import java.util.regex.Pattern

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@dylanbui.com
 * Date: 5/15/20
 * To change this template use File | Settings | File and Code Templates.
 */

// Remove tieng viet co dau thang khong dau
fun String.deAccent(): String {
    val nfdNormalizedString = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(nfdNormalizedString).replaceAll("").replace("đ", "d", true)
}


