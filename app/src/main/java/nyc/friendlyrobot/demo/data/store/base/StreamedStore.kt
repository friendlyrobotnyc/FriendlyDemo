package nyc.friendlyrobot.demo.data.store.base

import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * Base class for handling Raw and Parsed data subscription logic

 * @param  data type after parsing
 */
abstract class StreamedStore<Response, Request> : BaseStore<Response, Request>() {

    private val subject: BehaviorSubject<Response>

    init {
        subject = BehaviorSubject.create<Response>()
    }

    /**
     * To be exposed to clients for subscribing to endless data streams.
     * Any client that subscribers to the steam will receive updates for all data of
     * type [Response]
     */
    fun stream(): Observable<Response> {
        return subject.asObservable()
    }

    override fun getNetworkResponse(request: Request): Observable<Response> {
        return super.getNetworkResponse(request).doOnNext({ this.notifySubscribers(it) })
    }

    /**
     * Notify the subscribers of the subject of data change

     * @param data to be emitted to subscribers of Subject
     */
    protected fun notifySubscribers(data: Response) {
        subject.onNext(data)
    }
}
