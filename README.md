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

The project also contains two directories named `wrk_scripts` and `ab_scripts`. `wrk_scripts` contains Lua scripts that are used by the `wrk` load-testing tool for the evaluation and bash scripts for running the experiments. `ab_scripts` has the bash scripts used for the preliminary Apache Bench experiments. These scripts assume the `wrk` and `ab` binaries are appended to the system PATH.

The `results` directory contains the raw result files obtained from the experiment runs. The first 3 lines of each can be discarded as trial runs. The data then follows the format 3 runs of 7 concurrency levels (50, 100, 200, 400, 800, 1600, 3200) in the order: actors, exceptions, futures.