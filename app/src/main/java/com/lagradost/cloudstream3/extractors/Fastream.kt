package com.lagradost.cloudstream3.extractors

import com.lagradost.cloudstream3.amap
import com.lagradost.cloudstream3.apmap
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.M3u8Helper.Companion.generateM3u8
import com.lagradost.cloudstream3.utils.getAndUnpack

class Fastream: ExtractorApi() {
    override var mainUrl = "https://fastream.to"
    override var name = "Fastream"
    override val requiresReferer = false


    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        val response = app.get(url, referer = url).document
        response.select("script").amap { script ->
            if (script.data().contains(Regex("eval\\(function\\(p,a,c,k,e,[rd]"))) {
                val unpacked = getAndUnpack(script.data())
                //val m3u8regex = Regex("((https:|http:)\\/\\/.*\\.m3u8)")
                val newm3u8link = unpacked.substringAfter("file:\"").substringBefore("\"")
                //val m3u8link = m3u8regex.find(unpacked)?.value ?: return@forEach
                generateM3u8(
                    name,
                    newm3u8link,
                    mainUrl
                ).forEach { link ->
                    sources.add(link)
                }
            }
        }
        return sources
    }
}
