package com.accepto.docall.network.models.core

import java.io.Serializable


class APIResponseBean<T>() : Serializable {
    var status: Boolean = false
    var responseBody: T? = null
    var type: String = ""

    constructor(
        status: Boolean = false,
        responseBody: T? = null
    ) : this() {
        this.status = status
        this.responseBody = responseBody
        this.type = ""
    }

    constructor(
        status: Boolean = false,
        responseBody: T? = null,
        type: String = ""
    ) : this() {
        this.status = status
        this.responseBody = responseBody
        this.type = type
    }

    fun isErrorResponse(): Boolean {
        return (status && responseBody != null)
    }
}
