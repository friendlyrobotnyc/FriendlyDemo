package nyc.friendlyrobot.demo.data.local

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by brianplummer on 2/20/16.
 */
@Singleton
class AppPreferences
@Inject
constructor(context: Application) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
    val AUTH_TOKEN="authToken"
    @SuppressLint("CommitPrefEdits")
    fun applyPreference(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getPreference(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    @SuppressLint("CommitPrefEdits")
    fun applyPreference(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getPreference(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun registerPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    companion object {
        val AUTH_TOKEN = "token"
        val LOGIN_REQUEST = "login"
    }

}
