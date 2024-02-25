package com.divy.practicalkoin.repository

import com.divy.practicalkoin.network.HomeApiService

class HomeApiRepository constructor(
    private val homeApiService: HomeApiService,
) : HomeApiRepo, BaseRepository() {

//    override suspend fun getHomeMatches(
//        categoryId: String,
//        status: String,
//        page: Int,
//        limit: Int,
//        type: String
//    ): Either<MyException, MatchRS> {
//        return either {
//            homeApiService.getHomeMatches(categoryId, status, page, limit, type)
//        }
//    }

}