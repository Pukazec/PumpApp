package zec.puka.pumpapp.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zec.puka.pumpapp.api.CatApi
import zec.puka.pumpapp.db.CatDatabase
import zec.puka.pumpapp.model.Cat
import zec.puka.pumpapp.model.CatRemoteKeys

@ExperimentalPagingApi
class CatRemoteMediator (
    private val catApi: CatApi,
    private val catDatabase: CatDatabase
) : RemoteMediator<Int, Cat>(){
    private val catDao = catDatabase.catDao()
    private val catRemoteKeysDao = catDatabase.catRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Cat>): MediatorResult {
        Log.d("MEDIATOR", loadType.toString())
        return try {
            val currentPage = when(loadType) {
                LoadType.REFRESH -> {
                    val catRemoteKeys : CatRemoteKeys? =
                        withContext(Dispatchers.IO) {
                            getCatRemoteKeysClosestToCurrentPosition(state)
                        }
                    catRemoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val catRemoteKeys : CatRemoteKeys? =
                        withContext(Dispatchers.IO) {
                            getCatRemoteKeysForFirstItem(state)
                        }
                    val prevPage = catRemoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = catRemoteKeys != null)
                    prevPage

                }
                LoadType.APPEND -> {
                    val catRemoteKeys : CatRemoteKeys? =
                        withContext(Dispatchers.IO) {
                            getCatRemoteKeysForLastItem(state)
                        }
                    val nextPage = catRemoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = catRemoteKeys != null)
                    nextPage

                }
            }

            val response = catApi.getCats(page = currentPage)
            val endOfPaginationReached = response.cats.isEmpty()

            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            catDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    catDao.deleteCat()
                    catRemoteKeysDao.deleteCatRemoteKeys()
                }

                val catRemoteKeys = response.cats.map { pump ->
                    CatRemoteKeys(
                        id = pump.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                catRemoteKeysDao.addCatRemoteKeys(catRemoteKeys = catRemoteKeys)
                catDao.addCat(cats = response.cats)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getCatRemoteKeysClosestToCurrentPosition(state: PagingState<Int, Cat>)
            : CatRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                catRemoteKeysDao.getCatRemoteKeys(id = id)
            }
        }
    }

    private fun getCatRemoteKeysForFirstItem(state: PagingState<Int, Cat>)
            : CatRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()?.let { movie ->
                catRemoteKeysDao.getCatRemoteKeys(id = movie.id)
            }
    }
    private fun getCatRemoteKeysForLastItem(state: PagingState<Int, Cat>)
            : CatRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()?.let { movie ->
                catRemoteKeysDao.getCatRemoteKeys(id = movie.id)
            }
    }


}