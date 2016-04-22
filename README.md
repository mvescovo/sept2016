Assignment part 1
-----------------
Software Engineering Process and Tools.
####Team members
- Kendall Taylor, s9004570
  - 25% contribution
  - Created the user interface, user guide, and how to.
- Stephen Wood, s3239401
  - 25% contribution
  - Implemented the backend and the javadocs.
- Daniel Peri, s3543146
  - 25% contribution
  - Created and managed the tests for the whole system.
- Michael Vescovo, s3459317
  - 25% contribution
  - Designed the "skeleton" architecture and associated diagrams -  and this readme.

####Tutor
- Aditya Jagtap
  - Wednesday 5:30 - 7:30

####Bonus marks
- Junit testing
  - We have a few basic junit tests, testing one of the presenter classes. More to come in assignment 2. Please see the tests directory in the source code.
- Continous integration
  - We have implemented Travis CI such that all junit tests are automatically run each time there is a new commit. There is a screenshot in the main folder.
- Singleton pattern
  - The "MainWindow" class at line 121 of "Main.java" is a singleton class using this pattern.
- MVP (model view presenter) pattern
  - The architecture is designed with the MVP pattern. A version of MVC, this pattern helps make it easy to test each component. Each "feature" has it's own presenter and view class and only the presenter can talk to the repository.
- Repository pattern
  - We have used the repository pattern so that each "feature" can grab it's own data, thereby reducing coupling between components.