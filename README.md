# Group-73-The-smart-shelf

## Reflection

**What changed from your specification?**

returnBook changed because instead of returning bookID and userID like the spec said, it now returns bookID because the borrower is stored on the book.

**Did the model really stand alone?** 

Yes, the model does stand alone because it does not have any scanner or input systems for it. 

**What happened to your unknowns?**

One of the things we didn't know was how to properly implement the MVC so that it all works properly together. By using examples to learn how to implement them properly, we were able to create a decently working MVC. Some of the unknowns we have that we still need to figure out is how to properly catch illegal argument exceptions and empty queue exceptions.

**What doesn't work yet?**

Right now reserve stays as reserved forever, meaning it won't get checked out. There is also no proper way that you can get out of a waitlist, whch is somethign we wanted to have in the MVP. We have also not yet added the methods where we can add users and books on the main.

## Next Steps

**What is left to finish the MVP?**

Catching a NoSuchElementException properly. Writing the null and empty queue cases properly. Additionally, adding book title lookup option since we originally planned to in the MVP. Splitting files into the model, view, and controller packages so they are kept separate and not all together in one file.

**Your change feature**

A change feature is where the user would be able to cancel a reservation. It will touch all parts of the MVC. LinkedQueue and Waitlist will have a remove() method and the library will have a cancelReservation method in the model. Additionally, in the Controller, we would need to add a handleCancel method and include new lines to the display in the View.

**Which stretch goals will you attempt?**

A stretch goal we would attempt is a VIP pass that allows the user to go to the front of the queue based on that pass. 

**What new tests will you need?**

So far, we need tests for the Waitlist class, Library class, and the controller. This might change as we continue developing our code and any of the new functions we add to fulfill stretch goals. 

**Who does what?**

Each team will continue working on the parts originally planned in the spec, where Janberk will work on view, Abigel on controler, and Blake on the model. Our roles might also change given the different requirements and through any discussions that occur.

## Attribution

| Class or test file | Who worked on it | How |
| --- | --- | --- |

| `Library.java` |  Abigel| wrote |

| `LibraryController.java` | Abigel | wrote|

| `LibraryView.java` | Abigel | wrote|

| `Waitlist.java` | Abigel| wrote |

| `User.java` | Janberk | wrote|

| `Book.java` | Janberk| wrote |

| `All test files` | Blake | wrote |

GenAI Note: We used it as a co-teammate and asking it for more efficient ways to approach different parts of the MVC. We also used it to help out on the displyaMenu method of the library controller to make it efficient.

