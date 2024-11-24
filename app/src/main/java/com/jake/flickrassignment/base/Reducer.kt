package com.jake.flickrassignment.base

interface Reducer<State: Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect> {
    interface ViewState

    interface ViewEvent

    interface ViewEffect

    fun reduce(previous:State, event: Event): Pair<State, Effect?>
}