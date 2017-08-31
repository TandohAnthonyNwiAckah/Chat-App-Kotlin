package com.tanamo.app.model

import java.util.*

/**
* Created by ${TANDOH} on ${6/20/2017}.
*/


//This is my model class .


class Mod {
    var message: String? = null
    var name: String? = null
    var _Id: String? = null
    var time: Long = 0

    constructor() {}

    constructor(message: String, name: String, _Id: String) {
        this.message = message
        this.name = name
        this._Id = _Id
        time = Date().time
    }
}
