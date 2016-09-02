#!/bin/bash
while IFS='' read -r line || [[ -n "$line" ]]; do
    set -f
    IFS=' ' read -r -a array <<< "$line"
        contain="${array[0]##$2}"        
        if [ "${array[0]}" != "$contain" ]
        then
        	echo "${array[0]} ${array[2]} ${array[4]}"
        fi
done < "$1"