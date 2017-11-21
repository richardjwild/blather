#!/bin/bash

if [ "$1" == "" ]; then
    WORKING_DIRECTORY=.
else
    WORKING_DIRECTORY=$1
fi

BLATHER_EXECUTABLE=${WORKING_DIRECTORY}/build/libs/blather.jar
echo "Checking for Blather executable at: ${BLATHER_EXECUTABLE}..."
if [ ! -f ${WORKING_DIRECTORY}/build/libs/blather.jar ]; then
    echo "Test failed! Blather executable was not found"
    exit 1
fi

echo "Testing Blather..."

INPUT="Bob -> Hello world!
Bob
quit"

EXPECTED="Welcome to Blather
Hello world! (0 seconds ago)
Bye!"

ACTUAL=`echo "${INPUT}" | java -cp ${WORKING_DIRECTORY}/build/libs/blather.jar com.github.richardjwild.blather.Blather`

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
