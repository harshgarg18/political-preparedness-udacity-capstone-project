package com.udacity.political.preparedness.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.political.preparedness.database.ElectionDatabase
import com.udacity.political.preparedness.repository.ElectionRepository
import retrofit2.HttpException

class RefreshDataWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = ElectionDatabase.getInstance(applicationContext)
        val repository = ElectionRepository(database.electionDao)

        return try {
            repository.refreshElections()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
