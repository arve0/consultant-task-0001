# Transformer

## Notes to reviewer
I'm aware the solution have some flaws and that the project does not build
due to the coverage check. Gherkin, Cucumber and Picocli was new to me,
so I started experimenting first. Found the other checks after making
the cucumber specification work.

Regarding coverage, I tried adding `excludes` to the jacoto-configuration, 
but that did not work. Jackson requires the uncovered methods to deserialize 
the objects, and therefore not used in tests. The project builds when the
coverage check is temporary disabled.

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
- I like the setup, finding mistakes automatically increases the code quality.

> What can you do so that someone else can take over this code?

I try to follow these principles:

- ALWAYS refer to a case number (JIRA, github, etc) in commit log. This helps the
  next developer to find and understand the background and context for why the 
  code was written.
- Explain background in commit message, if something took a while to understand.
  Often, this is information is domain specific, like "Why does this transformer
  support multiple input formats?" I prefer having this information in the commit
  message over in the code as comments, as when you know the business domain you
  can read through the code with better flow. This information is often detailed,
  technical and tightly coupled to the current implementation, so it does not make 
  sense in the issue tracking platform.
- Add steps to setup and run project to README, script, etc. A few examples can
  save days and hours when new, and "jumping through the correct hoops" is
  necessary to get the project running.
- Name and structure things by business domain. Don't be afraid to rename, use the
  editor and compiler to make sure it does not break things. Do _not_ rename where
  you have weak coupling (HTTP, JSON, etc) without having versioning / contracts / 
  integration tests.
- It should be easy to find the relevant code when starting "in" the application.
  For example, take a web page. The URL should reflect the name of the code, so
  that one can lookup the HTTP-handler and work your way through the layers.

> Clever is good, but understandable is better

This is a good point, and I've been thinking a lot about how to make stuff
understandable when teaching.

A good example, I think, is the functional approach. Could you expect developers
to understand filter, map and reduce?

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
