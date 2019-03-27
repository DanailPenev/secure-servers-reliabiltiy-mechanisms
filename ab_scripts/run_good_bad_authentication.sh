#!/usr/bin/env bash

# warm-up run

for i in {1..3}
do
	ab -t 300 -n 100000000000 -c 50 -r -A "User:Password" http://gpgnode-02:8080/exceptions/login >> results.log
done

for mode in "actors" "exceptions" "futures"
do
        for c in 50 100 200 400 800 1600 3200
        do
                echo "RUNNING with mode $mode and c $c"
                echo "RUNNING with mode $mode and c $c" >> results.log
                for i in {1..3}
                do
			ab -t 300 -n 100000000000 -c $c -r -A "User:Password" http://gpgnode-02:8080/$mode/login >> results.log
                done
        done
done

for mode in "actors" "exceptions" "futures"
do
        for c in 50 100 200 400 800 1600 3200
        do
                echo "RUNNING with mode $mode and c $c"
                echo "RUNNING with mode $mode and c $c" >> results.log
                for i in {1..3}
                do
			ab -t 300 -n 100000000000 -c $c -r -A "User:Passwod" http://gpgnode-02:8080/$mode/login >> results.log
                done
        done
done