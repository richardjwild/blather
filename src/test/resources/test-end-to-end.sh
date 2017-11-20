#!/bin/bash

WORKING_DIRECTORY=$1
BLATHER_EXECUTABLE=${WORKING_DIRECTORY}/build/libs/blather.jar

echo "Checking for Blather executable at: ${BLATHER_EXECUTABLE}"
if [ ! -f ${WORKING_DIRECTORY}/build/libs/blather.jar ]; then
    echo "Test failed! Blather executable was not found"
    exit 1
fi

EXPECTED="Welcome to Blather
Bye!"

ACTUAL=`echo quit | java -cp ${WORKING_DIRECTORY}/build/libs/blather.jar com.github.richardjwild.blather.Blather`

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
