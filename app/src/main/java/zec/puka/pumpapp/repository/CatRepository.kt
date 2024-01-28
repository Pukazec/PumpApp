package zec.puka.pumpapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import zec.puka.pumpapp.api.CatApi
import zec.puka.pumpapp.db.CatDatabase
import zec.puka.pumpapp.model.Cat
import zec.puka.pumpapp.paging.CatRemoteMediator
import javax.inject.Inject


@ExperimentalPagingApi
class CatRepository @Inject constructor(
    private val catApi: CatApi,
    private val catDatabase: CatDatabase
) {
    fun getCats() : Flow<PagingData<Cat>> {

        val pagingSourceFactory = { catDatabase.catDao().getCats() }

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = CatRemoteMediator(
                catApi,
                catDatabase
            ),
            pagingSourceFactory = pagingSourceFactory

        ).flow

    }

    fun update(cat: Cat) = catDatabase.catDao().update(cat)

    fun delete(cat: Cat) = catDatabase.catDao().delete(cat)
}