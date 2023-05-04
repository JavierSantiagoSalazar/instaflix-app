package com.javierestudio.data.repository

import com.javierestudio.data.PermissionChecker
import com.javierestudio.data.PermissionChecker.Permission.COARSE_LOCATION
import com.javierestudio.data.RegionRepository
import com.javierestudio.data.datasource.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    private lateinit var locationDataSource: LocationDataSource

    @Mock
    private lateinit var permissionChecker: PermissionChecker

    private lateinit var regionRepository: RegionRepository

    @Before
    fun setup() {
        regionRepository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `Returns default region when coarse permission not granted`(): Unit = runBlocking {
        whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)

        val region = regionRepository.findLastRegion()

        assertEquals(RegionRepository.DEFAULT_REGION, region)
    }

    @Test
    fun `Returns region from location data source when permission granted`(): Unit = runBlocking {
        whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
        whenever(locationDataSource.findLastRegion()).thenReturn("US")

        val region = regionRepository.findLastRegion()

        assertEquals("US", region)
    }
}
