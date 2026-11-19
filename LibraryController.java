public class LibraryController 
{
    private final Library library;
    private final LibraryView view;

    public LibraryController(Library library, LibraryView view) {
        this.library = library;
        this.view = view;
        library.addListener(view);
    }

    public void run() 
    {
        boolean running = true;
        while (running) 
        {
            view.displayMenu();
            try 
            {
                int choice = view.getMenuChoice();
                switch (choice) 
                {
                    case 1:
                        handleBorrow(view.getUserId(), view.getBookId());
                        break;
                    case 2:
                        handleReturn(view.getBookId());
                        break;
                    case 3:
                        handleReserve(view.getUserId(), view.getBookId());
                        break;
                    case 4:
                        handleAddUser(
                                view.getUserName(),
                                view.getUserId(),
                                view.getVipStatus());
                        break;
                    case 5:
                        handleView(view.getBookId());
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        view.displayMessage("Please enter a number from 1 to 6.");
                }
            } catch (NumberFormatException e) 
            {
                view.displayMessage("Please enter a valid number.");
            }
        }
    }

    public void handleAddUser(String name, int userId, boolean vip) {
        try {
            library.addUser(new User(
                    name,
                    userId,
                    vip,
                    library.getInventorySize()));
            view.displayMessage("Added user " + name + " with ID " + userId + ".");
        } catch (IllegalArgumentException e) {
            view.displayMessage(e.getMessage());
        }
    }

    public void handleBorrow(int userId, int bookId) 
    {
        try 
        {
            library.checkOut(bookId, userId);
        } catch (BookNotFoundException | UserNotFoundException
                 | AlreadyOnWaitlistException | InventoryFullException e) {
            view.displayMessage(e.getMessage());
        }
    }

    public void handleReturn(int bookId) 
    {
        try 
        {
            library.returnBook(bookId);
        } 
        
        catch (BookNotFoundException | BookNotCheckedOutException e) 
        {
            view.displayMessage(e.getMessage());
        }
    }

    public void handleReserve(int userId, int bookId) {
        try
         {
            library.reserve(bookId, userId); 
        } 
        
        catch (BookNotFoundException | UserNotFoundException
                 | BookUnavailableException | InventoryFullException e) {
            view.displayMessage(e.getMessage());
        }
    }

    private void handleView(int bookId) {
        try {
            Book b = library.findBook(bookId);
            view.displayBook(b.getId(), b.getTitle(), b.getAuthor(), b.getStatus());
        } catch (BookNotFoundException e) {
            view.displayMessage(e.getMessage());
        }
    }


} // end of library controller
