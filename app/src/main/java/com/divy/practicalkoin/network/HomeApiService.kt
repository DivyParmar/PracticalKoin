package com.divy.practicalkoin.network

interface HomeApiService {

//    @FormUrlEncoded
//    @POST(ApiConstant.ApiFaqsList)
//    suspend fun getFaqs(
//        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
//    ): FAQDataListRS


//    @GET(ApiConstant.ApiHomeMatches)
//    suspend fun getHomeMatches(
//        @Query(AppConstant.categoryId) categoryId: String,
//        @Query(AppConstant.status) status: String,
//        @Query(AppConstant.page) page: Int,
//        @Query(AppConstant.limit) limit: Int,
//        @Query(AppConstant.type) type: String
//    ): MatchRS


//    @POST(ApiConstant.ApiCreditBalance)
//    suspend fun creditBalance(@Body requestBody: BalanceRequestModel): WalletDataResponseModel


//    @GET("${ApiConstant.ApiScore}/{${AppConstant.matchId}}/${AppConstant.score}")
//    suspend fun getMatchScore(@Path(AppConstant.matchId) matchId: String): MatchScoreResponse

//    @Multipart
//    @POST(ApiConstant.ApiUpdatePhoto)
//    suspend fun uploadFile(
//        @Part(AppConstant.fileType) fileType: RequestBody,
//        @Part file: MultipartBody.Part?
//    ): UploadFileResponseModel
}