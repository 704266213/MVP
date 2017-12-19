package com.mvp.glide.module

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory

import java.io.InputStream

import okhttp3.Call
import okhttp3.OkHttpClient

/**
 * A simple model loader for fetching media over http/https using OkHttp.
 */
class OkHttpUrlLoader(private val client: Call.Factory?) : ModelLoader<GlideUrl, InputStream> {

    override fun handles(url: GlideUrl): Boolean = true

    override fun buildLoadData(model: GlideUrl, width: Int, height: Int,
                               options: Options): ModelLoader.LoadData<InputStream>? =
            ModelLoader.LoadData(model, OkHttpStreamFetcher(client!!, model))

    /**
     * The default factory for [OkHttpUrlLoader]s.
     */
    class Factory : ModelLoaderFactory<GlideUrl, InputStream> {

        private val client: Call.Factory? = getInternalClient()

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> =
                OkHttpUrlLoader(client)

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }

        companion object {
            @JvmField
            var internalClient: Call.Factory? = null

            @JvmStatic
            private fun getInternalClient(): Call.Factory? {
                if (internalClient == null) {
                    synchronized(Factory::class.java) {
                        if (internalClient == null) {
                            internalClient = OkHttpClient()
                        }
                    }
                }
                return internalClient
            }
        }
    }

}
