package ru.foolstack.tests.impl.data.repository.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.foolstack.tests.impl.data.repository.local.LocalDataSource
import ru.foolstack.tests.impl.model.TestResultRequest
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource as AuthorizationLocalDataSource

class SendTestResultWorker(context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams), KoinComponent {

    private val localDataSource : LocalDataSource by inject()
    private val networkDataSource : NetworkDataSource by inject()
    private val authorizationLocalDataSource: AuthorizationLocalDataSource by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // Perform send test task
                val currentTime = (Clock.System.now().toEpochMilliseconds() + 864000000)/1000//10 days
                val passedTestInfo = localDataSource.getPassedTests().passedTests.last()
                val request = TestResultRequest(

                    testId = passedTestInfo.testId,
                    testResult = passedTestInfo.testResult,
                    finishTestTime = currentTime
                )
                networkDataSource.sendTestResult(request = request, userToken = authorizationLocalDataSource.getTokenFromLocal())
                Result.success()
            } catch (e: Exception) {
                Log.e("SyncCoroutineWorker", "Error syncing data", e)
                Result.failure()
            }
        }
    }
}