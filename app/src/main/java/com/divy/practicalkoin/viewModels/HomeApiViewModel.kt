package com.divy.practicalkoin.viewModels

import com.divy.practicalkoin.repository.HomeApiRepo

class HomeApiViewModel constructor(private val homeRepo: HomeApiRepo) : BaseViewModel() {

//    val homeMyMatchData: MutableLiveData<MatchRS> = MutableLiveData()

//    fun getHomeMatches(categoryId: String, status: String, page: Int, limit: Int, type: String) {
//        launch {
//            if (type == QueryConstants.typeMyMatch) {
//                postValue(
//                    homeRepo.getHomeMatches(categoryId, status, page, limit, type),
//                    homeMyMatchData
//                )
//            } else {
//                when (status) {
//                    QueryConstants.statusLive -> {
//                        postValue(
//                            homeRepo.getHomeMatches(categoryId, status, page, limit, type),
//                            homeLiveMatchData
//                        )
//                    }
//
//                    else -> {
//                        //upcoming
//                        postValue(
//                            homeRepo.getHomeMatches(categoryId, status, page, limit, type),
//                            homeUpcomingMatchData
//                        )
//                    }
//                }
//            }
//        }
//    }

//    fun getMatchQuestionAll(matchId: String, page: Int, limit: Int, type: String) {
//        launch {
//            postValue(
//                homeRepo.getMatchQuestion(matchId, page, limit, type),
//                matchQuestionLiveDataAll
//            )
//        }
//    }

}