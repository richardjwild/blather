#!/bin/bash

if [ "$1" == "" ]; then
    WORKING_DIRECTORY=.
else
    WORKING_DIRECTORY=$1
fi

INPUT="Bob -> Hello world!
Bob
quit"

EXPECTED="Welcome to Blather
Hello world! (0 seconds ago)
Bye!"

BLATHER_EXECUTABLE=${WORKING_DIRECTORY}/build/libs/blather.jar
echo "Checking for Blather executable at: ${BLATHER_EXECUTABLE}..."
if [ ! -f ${BLATHER_EXECUTABLE} ]; then
    echo "Test failed! Blather executable was not found"
    exit 1
fi

echo "Testing Blather..."

ACTUAL=`echo "${INPUT}" | java -jar ${BLATHER_EXECUTABLE}`

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
