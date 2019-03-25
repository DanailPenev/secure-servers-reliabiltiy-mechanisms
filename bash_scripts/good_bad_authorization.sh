#!/usr/bin/env bash

hostname=$1
models="actors exceptions futures"
concurrency="50 100 200 400 800 1600 3200"

# warm-up run
for i in {1..3}
do
        ./tools/wrk/wrk -t32 -c1000 -d5m -s../wrk_scripts/csv_good_authorization.lua $hostname:8080/actors/admin
done

for mode in $models
do
        for c in $concurrency
        do
                for i in {1..3}
                do
                        wrk -t32 -c$c -d5m -s../wrk_scripts/csv_good_authorization.lua $hostname:8080/$mode/admin
                done
        done
done

for mode in $models
do
	for c in $concurrency
	do
		for i in {1..3}
		do
			wrk -t32 -c$c -d5m -s../wrk_scripts/csv_bad_authorization.lua $hostname:8080/$mode/admin
		done
	done
done
exit 0
