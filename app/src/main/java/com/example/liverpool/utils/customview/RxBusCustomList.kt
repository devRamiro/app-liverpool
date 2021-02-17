package com.example.liverpool.utils.customview

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object RxBusCustomList{
    private val bus: PublishSubject<String> = PublishSubject.create()

    fun setEvent(keyword: String) {
        bus.onNext(keyword)
    }

    fun getEvent(): Observable<String>? {
        return bus
    }
}