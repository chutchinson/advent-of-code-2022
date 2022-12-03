if [ $? -eq 0 ]; then
    time cat inputs/$1.txt | bazel run :adventofcode -- $1 $@
fi