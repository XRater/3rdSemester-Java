bans=()

for FILE in `ls`
do
    if test -d $FILE
    then
        skip=false
        for BAN in ${bans[@]}
        do
            if [ "$BAN" == "$FILE" ]
            then
                skip=true
            fi
        done
        if [ $skip == false ]
        then
            echo "$FILE"
            cd "$FILE"
            result= ./gradlew build
            if ${result}
            then
                cd ../
            else 
                exit 1
            fi
        fi
    fi
done
