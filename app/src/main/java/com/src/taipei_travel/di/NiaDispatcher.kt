package com.src.taipei_travel.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher (val niaDispatcher: NiaDispatchers)

enum class NiaDispatchers {
    Default,
    IO,
}