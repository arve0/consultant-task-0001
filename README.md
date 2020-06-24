# Transformer

## Notes to reviewer
I'm aware the solution have some flaws and that the project does not build
due to the coverage check. Gherkin, Cucumber and Picocli was new to me,
so I started experimenting first. Found the other checks after making
the cucumber specification work.

Regarding coverage, I tried adding `excludes` to the jacoto-configuration, 
but that did not work. Jackson requires the uncovered methods to deserialize 
the objects, and therefore not used in tests. The coverage can
be passed by lowering threshold, or finding out how to make `excludes` work.

I've noted flaws, ideas and thoughts underway, and would gladly discuss them:

- Streaming
- State and mutation in `StepDefinitions`
- JSON input supports two different formats, top-level array or object.
- Strong coupling for serialization library Jackson (also used in tests)
- Gherkin background assertions seemed strange
- Project setup.
  - In a normal project setting, project setup would be done proper when onboarding. 
  - I should have added a git pre-commit hook to avoid cleaning up after experimentation. 
    Though, a bit hard with the clean sheet and "everything" failing.
  - Took a while to get cucumber-reporting in my editor.
- The commit log have scattered timestamps, as I've been working on other
  tasks in between.
- `git rebase -i --exec "./gradlew check" master`

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
