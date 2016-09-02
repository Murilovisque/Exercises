#!/bin/bash

IFS=$'\n' arr=(`cat "$1"`)

arrSort=($(for el in ${arr[@]}
do
	echo "${el[0]}"
done | sort))

for el in ${arrSort[@]}
do
	echo "$el"
done
