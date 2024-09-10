package app.debugdesk.notebook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform