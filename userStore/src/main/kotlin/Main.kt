package org.vikkio

import io.github.cdimascio.dotenv.dotenv
import org.vikkio.app.App


fun main() {
    val env = dotenv()
    val appSecret = env["APP_SECRET"] ?: "secret"
    val app = App(appSecret)
    app.run()
}