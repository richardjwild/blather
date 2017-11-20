#!/bin/bash

if [ ! -f build/libs/blather.jar ]; then
    echo "Test failed! Blather executable was not found"
    exit 1
fi

EXPECTED="Welcome to Blather
Bye!"

ACTUAL=`echo quit | java -cp build/libs/blather.jar com.github.richardjwild.blather.Blather`

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
