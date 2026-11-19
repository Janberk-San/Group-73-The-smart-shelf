# Group-73-The-smart-shelf

## Steps to run program

Run `javac Main.java` then `java Main` to open the menu. It allows users to borrow or return books, reserve books, add new users, and view a book. It also allows the user to select if they are a VIP or not. Use number keys to choose an option, and choose 6 to quit. To add a user, enter a name, unique numeric ID, and whether the user is VIP (`true` or `false`).

## Steps to running the tests

In VS Code:

javac -cp junit-platform-console-standalone.jar *.java

java -jar junit-platform-console-standalone.jar --class-path . --scan-class-path

## Reflection

**How did your design absorb the change?**

we had to make edits to the array bag, controller, view, and main in order to add the full functionality of the "add user" option. This meant that it went through changes through different parts of the MVC. 

**What changed from your D3 plan?**

we originally thought we were fully done, but after continually checking our code, we realized there were some edits to make in order to make it as efficient as we can. Insetad of our original plan of changing one file, we changed various files beacause different parts were connected to each other. Also, we wrote down some of the tests that we were missing from the last deliverable.

**What doesn't work yet?**

As far as what we have tested so far, all the main aspects of our code are currently working. The user can add their own user information, they can view a book, they can reserve a book, and the system tells them if that book is available to be reserved or not. 

## Next Steps

**What is left for the final build?**

Something that is left is to make sure that all of our tests are working as properly as possible.

**Which stretch goals are you committing to? And which are you dropping?**

The VIP pass is the main stretch goal that we are committing to. Additionally, a stretch goal that we have implemented is full functionality of the library system. Some of the stretch goals that we are dropping is a password system that the librarian can use to have secure access and a time tracker for when a book was checked out by a user and a reminder that accompanies it for the user to return the book.

**What will your 3-minute demo show?**

It will show us going through the different choice options on the UI. We will walk through how to view a book, adding ourselves as users, and reserving books under our user name. it will also show any kinds of bad inputs that we purposely input, which should then show how the system catches those errors. 

**Who does what?**

Each team will continue working on the parts originally planned in the spec, where Janberk will work on view, Abigel on controler, and Blake on the model. Our roles might also change given the different requirements and through any discussions that occur.

## Attribution

| Class or test file | Who worked on it | How |
| --- | --- | --- |

| `Library.java` |  Abigel| wrote |

| `LibraryController.java` | Abigel | wrote|

| `LibraryView.java` | Abigel | wrote|

| `Waitlist.java` | Abigel| wrote |

| `BookStatus.java` | Abigel | wrote |

| `Main.java` | Abigel | wrote |

| `User.java` | Janberk | wrote|

| `Book.java` | Blake| wrote |

| `All test files` | Blake | wrote |

| `EmptyQueueException.java` | Blake | wrote |

GenAI Note: We used it as a co-teammate and asking it for more efficient ways to approach different parts of the MVC. We also used it to help out on the displyaMenu method of the library controller to make it efficient.

