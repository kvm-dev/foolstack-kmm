package ru.foolstack.tests.impl.data.repository.network

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import ru.foolstack.utils.PlatformContext

actual class DelayedTestResultSender actual constructor(context: PlatformContext) {

    private val androidContext = context.androidContext
    actual fun sendTestResult() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SendTestResultWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(androidContext).enqueue(workRequest)
    }

}