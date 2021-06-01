package com.example.krealogi.network.connection

import java.io.IOException

class ApiException(message: String): IOException(message)
class ConnectionException(message: String): IOException(message)