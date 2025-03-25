package ru.foolstack.authorization.impl.data.repository.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource

class SendAuthByTokenOfflineLog(context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams), KoinComponent {
    private val networkDataSource : NetworkDataSource by inject()
    private val authorizationLocalDataSource: LocalDataSource by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // Perform send log task
                val currentTime = (Clock.System.now().toEpochMilliseconds())/1000
                networkDataSource.authByTokenOfflineLog(timestamp = currentTime, userToken = authorizationLocalDataSource.getTokenFromLocal())
                Result.success()
            } catch (e: Exception) {
                Log.e("SyncCoroutineWorker", "Error syncing data", e)
                Result.failure()
            }
        }
    }
}