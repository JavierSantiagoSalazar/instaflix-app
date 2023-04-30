package com.javierestudio.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
