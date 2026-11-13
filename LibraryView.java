import java.util.Scanner;

public class LibraryView implements LibraryListener 

{
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() 
    {
        System.out.println("\n********* The Smart Shelf *********");
        System.out.println("1. Borrow a book");
        System.out.println("2. Return a book");
        System.out.println("3. Reserve a book");
        System.out.println("4. View a book");
        System.out.println("5. Quit");
    }

    public int getMenuChoice() 
    {
        System.out.print("Choice: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int getBookId() 
    {
        System.out.print("Book ID: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int getUserId() 
    {
        System.out.print("User ID: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayBook(int id, String title, String author, BookStatus status) 
    {
        System.out.println("[" + id + "] " + title + " by " + author + " - " + status);
    }

    @Override
    public void bookChanged(int bookId, BookStatus status, String message) 
    {
        System.out.println(message); 
    }

} // this is the end of liubary view