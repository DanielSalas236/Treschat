package com.uniajc.treschat.models.jwt

class JwtRequest {
    var username: String = ""
    var password: String = ""

    //necesita un constructor por defecto para el parseo en JSON
    constructor() {}
    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}