package ru.anisart.stravaflyby

import org.junit.Test

import org.junit.Assert.*

class UnitTest {

    @Test
    fun parseShortUrlTest() {
        val testString = "Моя тренировка на Strava: https://strava.app.link/6kq-5b?_&1v/0d=%W"
        val expectedResult = "https://strava.app.link/6kq-5b?_&1v/0d=%W"
        val actualResult = parseShortUrl(testString)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun parseShortUrlEmptyTest() {
        val testString = ""
        val actualResult = parseShortUrl(testString)

        assertNull(actualResult)
    }

    @Test
    fun parseFullUrlTest() {
        val testString ="https://www.strava.com/activities/2326041119/shareable_images/image?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1cmwiOm51bGwsInR5cGUiOiJtYXAiLCJ0b2tlbiI6IjZkYzBhMGRjN2Y2MWYzMDY1MGZkZDY5MGEzNTI1MzAyZmMyMjgyOGYiLCJ3aWR0aCI6MTA4MCwiaGVpZ2h0IjoyMDM0LCJpbWFnZV93aWR0aCI6bnVsbCwiaW1hZ2VfaGVpZ2h0IjpudWxsLCJ1bmlxdWVfaWQiOm51bGx9.KYJ3dAAZqh3cUJSzlMicY00Ohb5ZK2X-vOsGQOaEH_U&amp;hl=ru-RU&amp;utm_source=UNKNOWN&amp;utm_source=android_share&amp;utm_medium=referral&amp;utm_medium=social&amp;share_sig=Y0DPVHEE1556518992&amp;_branch_match_id=651294740754871177"
        val expectedResult = "2326041119"
        val actualResult = parseFullUrl(testString)

        assertEquals(expectedResult, actualResult)
    }
}
