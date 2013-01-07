#!/bin/sh
mkdir -p bld
find algebird/ src/ -type f -name "*.scala" -o -name "*.java" | xargs scalac -d bld
scala -cp bld test/example.scala