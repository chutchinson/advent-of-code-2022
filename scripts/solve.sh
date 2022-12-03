# bazel build :adventofcode > /dev/null 2>&1
bazel build :adventofcode

if [ $? -eq 0 ]; then
time cat inputs/$1.txt | bazel-bin/adventofcode.exe $@
fi