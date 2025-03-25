package ru.foolstack.authorization.impl.data.repository.network

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import ru.foolstack.utils.PlatformContext

actual class DelayedAuthByTokenLogger actual constructor(context: PlatformContext) {

    private val androidContext = context.androidContext
    actual fun sendLog() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SendAuthByTokenOfflineLog>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(androidContext).enqueue(workRequest)
    }

}