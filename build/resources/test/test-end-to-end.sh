#!/bin/bash

EXPECTED="Welcome to Blather!"
ACTUAL=`java -cp build/libs/blather.jar com.github.richardjwild.blather.Blather`
if [ $? -ne 0 ]; then
    echo "Test failed! Error running Blather executable"
    exit 1
fi

if [ "${ACTUAL}" == "${EXPECTED}" ]; then
    echo "Test passed!"
else
    echo "Test failed! Expected: '${EXPECTED}' Actual: '${ACTUAL}'"
    exit 1
fi
