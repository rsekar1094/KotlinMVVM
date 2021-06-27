package com.rsekar.base.utils.states

enum class DataRequestState  {

    IDLE { ///Fresh one - no data requested or present
        override val stateType : Int = 2
    },WAITING_FOR_RESPONSE { ///Requested to data and waiting for the response
        override val stateType : Int = 3
    },DATA_RECEIVED { ///Data received
        override val stateType : Int = 4
    };

    abstract val stateType : Int
}