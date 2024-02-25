package com.divy.practicalkoin.utility

import com.pixplicity.easyprefs.library.Prefs

object PrefKeys {

    const val isIntroShown = "isIntroShown"
    const val AuthKey = "AuthKey"

    var authKey: String
        get() = Prefs.getString(AuthKey, "")
        set(value) {
            Prefs.putString(AuthKey, value)
        }

    fun setIsIntroShown(boolean: Boolean) {
        Prefs.putBoolean(isIntroShown, boolean)
    }

    fun getIsIntroShown(): Boolean {
        return Prefs.getBoolean(isIntroShown)
    }
}