# Comparing Reliability Mechanisms for Secure Web Servers: Actors, Exceptions and Futures

A secure web application benchmark, developed in Scala with the [akka-http framework](https://github.com/akka/akka-http). It implements three different reliability mechanisms:

* Actor model
* Exceptions (try-catch)
* Futures

## Requirements

* [JDK 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [sbt (Scala Build Tool)](https://www.scala-sbt.org/)

## Installation

1. Clone this repository
1. Open a terminal in the created folder
1. Enter `sbt test` in the terminal to run the included tests
1. `sbt run` starts the web server locally

## Additional Files

The project also contains two directories named `wrk_scripts` and `bash_scripts`. `wrk_scripts` contains Lua scripts that are used by the `wrk` load-testing tool for the evaluation. `bash_scripts` has the bash scripts used for running the evaluation. This assumes the `wrk` binary is appended to the system PATH.