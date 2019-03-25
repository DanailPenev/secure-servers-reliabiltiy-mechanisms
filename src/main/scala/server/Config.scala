package server

object Config {
  val ActorsPath = "actors"
  val ExceptionsPath = "exceptions"
  val FuturesPath = "futures"
  val LoginPath = "login"
  val AdminPath = "admin"
  val CookieName = "userName"

  val users: Map[String, String] = Map("User" -> "ObfuscatedPassword", "User2" -> "notEvenAThing")
  val cookies: Map[String, String] = Map("RandomlyGeneratedCookie" -> "User")
  val admins: Set[String] = Set("User")
}
