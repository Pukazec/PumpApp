package zec.puka.pumpapp.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zec.puka.pumpapp.api.PumpApi
import zec.puka.pumpapp.db.PumpDatabase
import zec.puka.pumpapp.model.Pump
import zec.puka.pumpapp.model.PumpRemoteKeys

@ExperimentalPagingApi
class PumpRemoteMediator (
    private val pumpApi: PumpApi,
    private val pumpDatabase: PumpDatabase
) : RemoteMediator<Int, Pump>(){
    private val pumpDao = pumpDatabase.pumpDao()
    private val pumpRemoteKeysDao = pumpDatabase.pumpRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Pump>): MediatorResult {
        Log.d("MEDIATOR", loadType.toString())
        return try {
            val currentPage = when(loadType) {
                LoadType.REFRESH -> {
                    val pumpRemoteKeys : PumpRemoteKeys? =
                        withContext(Dispatchers.IO) {
                            getPumpRemoteKeysClosestToCurrentPosition(state)
                        }
                    pumpRemoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val pumpRemoteKeys : PumpRemoteKeys? =
                        withContext(Dispatchers.IO) {
                            getPumpRemoteKeysForFirstItem(state)
                        }
                    val prevPage = pumpRemoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = pumpRemoteKeys != null)
                    prevPage

                }
                LoadType.APPEND -> {
                    val pumpRemoteKeys : PumpRemoteKeys? =
                        withContext(Dispatchers.IO) {
                            getPumpRemoteKeysForLastItem(state)
                        }
                    val nextPage = pumpRemoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = pumpRemoteKeys != null)
                    nextPage

                }
            }

            val response = pumpApi.getPumps(page = currentPage)
            val endOfPaginationReached = response.pumps.isEmpty()

            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            pumpDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pumpDao.deletePump()
                    pumpRemoteKeysDao.deletePumpRemoteKeys()
                }

                val pumpRemoteKeys = response.pumps.map {pump ->
                    PumpRemoteKeys(
                        id = pump.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                pumpRemoteKeysDao.addPumpRemoteKeys(pumpRemoteKeys = pumpRemoteKeys)
                pumpDao.addPump(pumps = response.pumps)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getPumpRemoteKeysClosestToCurrentPosition(state: PagingState<Int, Pump>)
            : PumpRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pumpRemoteKeysDao.getPumpRemoteKeys(id = id)
            }
        }
    }

    private fun getPumpRemoteKeysForFirstItem(state: PagingState<Int, Pump>)
            : PumpRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()?.let { movie ->
                pumpRemoteKeysDao.getPumpRemoteKeys(id = movie.id)
            }
    }
    private fun getPumpRemoteKeysForLastItem(state: PagingState<Int, Pump>)
            : PumpRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()?.let { movie ->
                pumpRemoteKeysDao.getPumpRemoteKeys(id = movie.id)
            }
    }


}