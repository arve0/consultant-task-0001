# Transformer

## Run
```bash
./gradlew run
# with arguments
./gradlew run --args="--input=src/test/resources/input.json --input-format=json --output=out.json --output-format=json"
# via jar
./gradlew jar
java -jar build/libs/consultant-task-0001.jar
# with arguments
java -jar build/libs/consultant-task-0001.jar \
    --input=src/test/resources/input.json --input-format=json --output=out.json
```

## Task

  1. Clone this repository to your own account
  1. Read the features in ```test/resources/features/mvp.feature```
  1. Write code to make the tests pass
  1. Commit the code to your repository, send us the URL
  
## Hints
  - Spend time understanding the requirements, they might not be clear…when are they ever?
  - There's probably a library for that
  - Take hints from the code
  - We aren't keen on code comments, preferring tests and suitable naming above having to read miles of code
  - We work extensively with testing, developer-friendliness and usability
    - What can you do so that someone else can take over this code?
    - Clever is good, but understandable is better
    
## What we are looking for
  - We want to see 
    - how you work with git, so you might not want to make one massive commit right at the end
    - how you interpret the functional requirements
    - how you structure your code
    - how you solve the problem you are given
    - you may not have time to complete everything, what you choose to focus on is important
