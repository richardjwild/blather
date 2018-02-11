[![CircleCI](https://circleci.com/gh/richardjwild/blather/tree/master.svg?style=shield&circle-token=761a13c0a67f184295191d2c4b50c5629645edae)](https://circleci.com/gh/richardjwild/blather/tree/master)

Blather is a solution to a coding exercise used by Codurance for evaluating job applicants for craftsperson or apprentice positions.

The program runs on the command line and implements a simplified chat room program. No networking or client/server separation is required.

To run the end-to-end test, be in the project directory and execute:

```bash
gradle build
src/test/resources/test-end-to-end.sh
```

To run Blather, build the 'fat jar' which includes all necessary dependencies:

```bash
gradle fatJar
```

then execute:

```bash
java -jar build/libs/blather-all.jar
```

To post a message to a user, execute:

```
rich -> Hello world!
sarah -> Omg it's snowing!
```

To read messages posted to a user, enter just their name:

```
rich
Hello world! (1 minute ago)
sarah
Omg it's snowing! (3 seconds ago)
```

To make a user follow another user, execute:

```
jolene follows rich
jolene follows sarah
```

To read all messages posted to users followed by a particular user, execute:

```
jolene wall
rich - Hello world! (3 minutes ago)
sarah - Omg it's snowing! (1 minute ago)
```

To exit Blather, execute:

```
quit
```