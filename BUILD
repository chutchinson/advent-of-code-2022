load("@rules_java//java:defs.bzl", "java_binary")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "adventofcode.lib",
    srcs = glob(["src/**/*.kt"])
)

java_binary(
    name = "adventofcode",
    main_class = "com.cshutchinson.adventofcode2022.App",
    runtime_deps = [":adventofcode.lib"]
)