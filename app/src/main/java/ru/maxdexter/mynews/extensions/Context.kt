package ru.maxdexter.mynews.extensions

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

fun Context.createDataStore(name: String,
                            corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
                            migrations: List<DataMigration<Preferences>> = listOf(),
                            scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) : DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        produceFile = { File(this.filesDir, "datastore/$name.preferences.db") },
        corruptionHandler = corruptionHandler,
        migrations = migrations,
        scope = scope)
}