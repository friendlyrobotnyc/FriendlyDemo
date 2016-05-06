package nyc.friendlyrobot.demo.data.store.base

import rx.Observable

/**
 * RxStore to be used for loading an object different data sources

 * @param  data type after parsing
 * *
 *
 *
 * *            get = cached data if not stale otherwise network, updates caches
 * *            fresh=skip memory and disk cache
 */
abstract class BaseStore<Response, Request> : Store<Response, Request> {
    private val cache: RxCache<Request, Response>


    init {
        cache = RxCache.create<Request, Response>()
    }

    /**
     * @return an observable from the first data source
     * * memory/disk/network that is available and not stale
     */
    override fun get(request: Request): Observable<Response> {
        return cache.get(request, getNetworkResponse(request))
    }

    /**
     * @return force network and update disk/memory
     */
    override fun fresh(request: Request): Observable<Response> {
        return fetch(request, "fresh and clean").doOnNext { data -> cache.update(request, data) }
    }

    protected open fun getNetworkResponse(request: Request): Observable<Response> {
        return fetch(request, null).doOnNext { response ->  //nothing
        }
    }

    protected abstract fun fetch(request: Request, forceNetwork: String?): Observable<Response>

    fun clearMemory() {
        cache.clearMemory()
    }

    fun clearMemory(request: Request) {
        cache.clearMemory(request)
    }

    companion object {
        val SUCCESS_RESPONSE = "SUCCESS"
    }

}
