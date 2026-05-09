package pt.atec.casino.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "casino_prefs")

class PreferencesManager(private val context: Context) {

    companion object {
        val BALANCE_KEY = doublePreferencesKey("balance")
        const val STARTING_BALANCE = 1000.0
    }

    val balanceFlow: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[BALANCE_KEY] ?: STARTING_BALANCE
    }

    suspend fun updateBalance(newBalance: Double) {
        context.dataStore.edit { prefs ->
            prefs[BALANCE_KEY] = newBalance
        }
    }
}