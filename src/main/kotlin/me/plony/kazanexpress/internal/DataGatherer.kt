package me.plony.kazanexpress.internal

abstract class DataGatherer<T, R> {
    abstract suspend fun gather(input: T): R
}