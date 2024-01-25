package zec.puka.pumpapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import zec.puka.pumpapp.api.PumpApi
import zec.puka.pumpapp.db.PumpDatabase
import zec.puka.pumpapp.model.Pump
import zec.puka.pumpapp.paging.PumpRemoteMediator
import javax.inject.Inject


@ExperimentalPagingApi
class PumpRepository @Inject constructor(
    private val pumpApi: PumpApi,
    private val pumpDatabase: PumpDatabase
) {
    fun getPumps() : Flow<PagingData<Pump>> {

        val pagingSourceFactory = { pumpDatabase.pumpDao().getPumps() }

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PumpRemoteMediator(
                pumpApi,
                pumpDatabase
            ),
            pagingSourceFactory = pagingSourceFactory

        ).flow

    }

    fun update(pump: Pump) = pumpDatabase.pumpDao().update(pump)

    fun delete(pump: Pump) = pumpDatabase.pumpDao().delete(pump)
}