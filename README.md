# Group-73-The-smart-shelf

**What changed from your specification?**

returnBook changed because instead of returning bookID and userID like the spec said, it now returns bookID because the borrower is stored on the book.

**Did the model really stand alone?**

Yes, the model does stand alone because it does not have any scanner or input systems for it. 

**What happened to your unknowns?**

One of the things we didn't know was how to properly implement the MVC so that it all works properly together. By using examples to learn how to implement them properly, we were able to create a decently working MVC. Some of the unknowns we have that we still need to figure out is how to properly catch illegal argument exceptions and empty queue exceptions.

**What doesn't work yet?**

Right now reserve stays as reserved forever, meaning it won't get checked out. There is also no proper way that you can get out of a waitlist, whch is somethign we wanted to have in the MVP. We have also not yet added the methods where we can add users and books on the main.