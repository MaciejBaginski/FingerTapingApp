package com.example.fingertapingapp.entities

import java.io.Serializable

data class User(val name: String, val surname: String, val age: Int) : Serializable {

    private var id: Long = -1
    var isCaretaker: Boolean = false

    constructor(name: String, surname: String, id: Long) : this(name, surname, 0) {
        this.id = id
    }
}