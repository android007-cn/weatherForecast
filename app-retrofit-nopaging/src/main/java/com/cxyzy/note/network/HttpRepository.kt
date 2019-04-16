package com.cxyzy.note.network

import com.cxyzy.note.utils.PAGE_SIZE

object HttpRepository : BaseHttpRepository() {
    suspend fun getRepo() = api.repos(user = "cxyzy1", page = 1, perPage = PAGE_SIZE).await()
}