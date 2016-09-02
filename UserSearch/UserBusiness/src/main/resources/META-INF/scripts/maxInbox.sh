#!/bin/bash
max=-1
user=""
while IFS='' read -r line || [[ -n "$line" ]]; do
    set -f
    IFS=' ' read -r -a array <<< "$line"        
        if [ "${array[2]}" -gt $max ]
        then
        	max="${array[2]}"
        	user="${array[0]} ${array[2]} ${array[4]}"
        fi
done < "$1"
echo $user