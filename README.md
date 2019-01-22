# Comparing Cyber Security Programming Models in Scala

A simple Scala web server, developed with the [akka-http framework](https://github.com/akka/akka-http).

The server features three different authentication and authorisation models:

* Traditional try/catch model
* Actor-based model
* Future-based model

The main hypothesis that is being tested and evaluated is that the actor and future models can give comparable performance to the try/catch model while reducing the number of states in the program. This directly minimises the available vectors for cyber attacks.

## Installation

1. Clone this repository
1. Open a terminal in the created folder
1. Enter `sbt test` in the terminal to run the included tests
1. `sbt run` starts the web server locally

## Contributing

The application is still in a very early stage of development so external contributions are not accepted yet.