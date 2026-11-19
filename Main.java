public class Main{
    public static void main(String[] args) {
        Library library = new Library(3);
        library.addBook(new Book(1, "1984", "George Orwell"));
        library.addBook(new Book(2, "Dune", "Frank Herbert"));
        library.addBook(new Book(3, "The Chronicles of Narnia", "C.S. Lewis"));
        library.addBook(new Book(4, "The Great Gasby", "F.Scott Fitzgerald"));
        library.addUser(new User("Alice", 10, false, library.getInventorySize()));
        library.addUser(new User("Bob", 11, false, library.getInventorySize()));
        library.addUser(new User("Vera", 12, true, library.getInventorySize()));
        library.addUser(new User("cs", 1, true, library.getInventorySize()));

        new LibraryController(library, new LibraryView()).run();
    }
}