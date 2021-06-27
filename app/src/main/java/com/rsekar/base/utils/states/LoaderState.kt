package com.rsekar.base.utils.states

enum class LoaderState {

    NONE {
        override val stateType : Int = 2
    },MAIN_PROGRESS_ON {
        override val stateType : Int = 3
    },TOP_PROGRESS_ON {
        override val stateType : Int = 4
    },BOTTOM_PROGRESS_ON {
        override val stateType : Int = 5
    };

    abstract val stateType : Int
}