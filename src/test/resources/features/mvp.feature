Feature:
  User transforms file

  Background:
    Given the user has an application "Transformer" that has a command line interface
    And "Transformer" has a flag "--input" that takes a single argument that is a filename
    And "Transformer" has a flag "--output" that takes a single argument that is a filename
    And "Transformer" has a flag "--input-format" that takes a single argument "xml" or "json"
    And "Transformer" has a flag "--output-format" that takes a single argument "xml" or "json"

  Scenario: User transforms a single object from format A to format B
    Given the user has an input file that contains an array that contains a single object
    And the object has field "name" with string value "Carstens, Ull"
    And the object has field "sequence" with string value "1"
    And the object has field "role" with string value "Executive"
    When the user transforms the data
    Then the user sees that the output file contains an array that contains a single object
    And the object has a field "id" with an integer value 1
    And the object has field "role" with string value "Executive"
    And the object has a field "identity"
    And the field "identity" contains an object with the fields "given" and "family"
    And the field "given" contains string value "Ull"
    And the field "family" contains string value "Carstens"

  @wip
  Scenario Outline: User transforms data from file to JSON
    Given the user has a file "input.json"
    And the data is formatted correctly
    When the user transforms the file from <serialization-a> to <serialization-b>
    And they open the file
    Then they see that the data is transformed to <serialization-b>
    And that the elements in "users" section of the file are ordered by element "sequence"

    Examples:
      | serialization-a | serialization-b |
      | xml             | xml             |
      | json            | json            |
      | xml             | json            |
      | json            | xml             |

  @wip
  Scenario Outline: User transforms file without specifying an output format
    Given the user has a <file> in <serialization>
    And the data is formatted correctly
    When they transform the file without specifying the output format flag
    And they open the transformed file
    Then the file is transformed to serialization
    And that the elements in "users" section of the file are ordered by element "sequence"

    Examples:
      | file       | serialization |
      | input.xml  | xml           |
      | input.json | json          |

  @wip
  Scenario: User attempts to transform badly formatted file
    Given the user has a file "badly_formatted.json"
    And the data is formatted badly
    When the user attempts to transform the file
    Then they see an error message telling them that the input file is badly formatted
