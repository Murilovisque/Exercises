#!/bin/bash


IFS=$'\n' arr=(`cat "$1"`)
arrFinal=($(for el in ${arr[@]}
do
	echo "${el[0]} ${el[1]} ${el[2]} ${el[3]} ${el[4]}"
done))

index=0;

for el in ${arr[@]}
do
	IFS=' ' read -r -a userArr <<< "$el"
	if [ "${userArr[2]}" -ge "$2" ] && [ "${userArr[2]}" -le "$3" ]
	then		
		arrFinal[index]="$strBetween${userArr[0]} ${userArr[2]} ${userArr[4]}\n"
		index=$((index+1))
	fi
done

for i in "${!arrFinal[@]}"; do 
	if [ "$i" -lt $index ]
	then
		printf "${arrFinal[$i]}"
	else
		break;
	fi;
done