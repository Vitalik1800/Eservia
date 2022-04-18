package com.eservia.model.local;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ContentChangesObservable {

    private static final PublishSubject<SyncEvent> BUS = PublishSubject.create();

    public static void send(SyncEvent o) {
        BUS.onNext(o);
    }

    private static Observable<SyncEvent> toObservable() {
        return BUS;
    }

    private static Observable<SyncEvent> createInitWith(SyncEvent event, boolean initWith) {
        if (initWith) {
            return Observable.just(event);
        } else {
            return Observable.empty();
        }
    }

    public static Observable<SyncEvent> sync(SyncEvent syncEvent, boolean startWithEvent){
        return toObservable().filter(event -> event == syncEvent)
                .startWith(createInitWith(syncEvent, startWithEvent));
    }

    public static Observable<SyncEvent> authorization() {
        return sync(SyncEvent.NOT_AUTHORIZED, false);
    }

    public static Observable<SyncEvent> outdatedVersion() {
        return sync(SyncEvent.OUTDATED_VERSION, false);
    }

    public static Observable<SyncEvent> rateUs() {
        return sync(SyncEvent.RATE_US, false);
    }

    public static Observable<SyncEvent> profile(boolean startWithEvent) {
        return sync(SyncEvent.PROFILE, startWithEvent);
    }

    public static Observable<SyncEvent> bookings(boolean startWithEvent) {
        return sync(SyncEvent.BOOKINGS, startWithEvent);
    }

    public static Observable<SyncEvent> favorite(boolean startWithEvent) {
        return sync(SyncEvent.FAVORITE, startWithEvent);
    }

    public static Observable<SyncEvent> comment(boolean startWithEvent) {
        return sync(SyncEvent.COMMENT, startWithEvent);
    }

    public static Observable<SyncEvent> addedComment(boolean startWithEvent) {
        return sync(SyncEvent.ADDED_COMMENT, startWithEvent);
    }
}
