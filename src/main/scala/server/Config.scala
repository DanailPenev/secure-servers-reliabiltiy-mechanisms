package server

object Config {
  val users: Map[String, String] = Map("User" -> "ObfuscatedPassword", "User2" -> "notEvenAThing")
  val cookies: Map[String, String] = Map("RandomlyGeneratedCookie" -> "User")
  val admins: Set[String] = Set("User")
}
