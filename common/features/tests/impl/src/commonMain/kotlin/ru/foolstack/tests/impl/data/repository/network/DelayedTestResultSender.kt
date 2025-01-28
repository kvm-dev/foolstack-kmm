package ru.foolstack.tests.impl.data.repository.network

import ru.foolstack.utils.PlatformContext

expect class DelayedTestResultSender(context: PlatformContext){

   fun sendTestResult()
}