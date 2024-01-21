package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.*;;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestLibrary {
    @Mock // we use mock because there is no implementation of the DatabaseService interface
    DatabaseService mockDataBaseService;
    @Mock // we use mock because there is no implementation of the NotificationService interface
    NotificationService mockNotificationService;
    @Mock // we use mock because there is no implementation of the ReviewService interface
    ReviewService mockReviewService;
    @Mock
    Book mockBook;
    @Mock
    User mockUser;
//    @Spy // we use spy here because there is a full implementation of the Book class
//    Book mockBook;
//    @Spy // we use spy here because there is a full implementation of the User class
//    User mockUser;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenBookIsNull_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);

        //2. Action
        //2.1 call the method under test will Book value being Null
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid book.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(null);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid book.");
    }

    // All the tests for different types of invalid ISBN
    @Test
    public void givenBookISBNIsNull_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn(null);
        //Book book = new Book(null,"title", "author");
        //2. Action
        //2.1 call the method under test will ISBN value being Null
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid ISBN.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenBookISBNisLessThenLengthThirdteen_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        //Book book = new Book("978-0-13-12456-7","title", "author");
        when(mockBook.getISBN()).thenReturn("978-0-13-12456-7");

        //2. Action
        //2.1 call the method under test will ISBN length is less then 13
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid ISBN.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }
    @Test
    public void givenBookISBNIsmoreThanThirdteen_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        //Book book = new Book("978-0-13-12456-7","title", "author");
        when(mockBook.getISBN()).thenReturn("978-0-13-12456-712");

        //2. Action
        //2.1 call the method under test will ISBN length is more than 13
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid ISBN.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenISBNNumberHasLetter_whenAddBook_ThenRaiseIllegalArgumentException()
    {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        //Book book = new Book("978-0-13-12456-7","title", "author");
        when(mockBook.getISBN()).thenReturn("978-0-13-124b6-7");

        //2. Action
        //2.1 call the method under test will ISBN length is less than 13
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid ISBN.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenBookISBNDOESnotMatchTheISBNrule_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
//        Book book = new Book("978-92-95055-02-7","title", "author");
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-7");
        //2. Action
        //2.1 call the method under test will ISBN value being an illegal number
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid ISBN.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenAnIllegalISBNNumberWithTheSumOfTheDigitsModuluTenIsZero_whenAddBook_ThenRaiseIllegalArgumentException()
    {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
//        Book book = new Book("978-92-95055-02-7","title", "author");
        when(mockBook.getISBN()).thenReturn("678-0-13-123456-4");
        //2. Action
        //2.1 call the method under test will ISBN value being an illegal number
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid ISBN.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    // title tests
    @Test
    public void givenBookWithNullForTitle_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        //Book book = new Book("978-92-95055-02-5",null, "author");
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn(null);

        //2. Action
        //2.1 call the method under test with title value being an null
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid title.");
    }

    @Test
    public void givenBookWithEmptyStringForTitle_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        //Book book = new Book("978-92-95055-02-5","", "author");
        when(mockBook.getTitle()).thenReturn("");

        //2. Action
        //2.1 call the method under test with title value being an empty string
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid title.");
    }

    // author tests
    @Test
    public void givenBookWithAuthorNameIsNulll_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        //Book book = new Book("978-92-95055-02-5","title", null);
        when(mockBook.getAuthor()).thenReturn(null);
        //2. Action
        //2.1 call the method under test with author being null
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid author.");
    }
    @Test
    public void givenBookWithEmptyStringForAuthor_whenAddBook_ThenRaiseIllegalArgumentException(){
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        //Book book = new Book("978-92-95055-02-5","title", null);
        when(mockBook.getAuthor()).thenReturn("");
        //2. Action
        //2.1 call the method under test with author being null
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid author.");
    }

    @Test
    public void givenBookWithNumbersInAuthorName_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        //Book book = new Book("978-92-95055-02-5","title", "author1");
        when(mockBook.getAuthor()).thenReturn("author1");
        //2. Action
        //2.1 call the method under test with author string having a number
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid author.");
    }

    @Test
    public void givenBookWithNonAlphaBeticFirstCharacterInAuthorName_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        //Book book = new Book("978-92-95055-02-5","title", "'author");
        when(mockBook.getAuthor()).thenReturn("'author");
        //2. Action
        //2.1 call the method under test with author string starting with non alphabetic character
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid author.");
    }

    @Test
    public void givenBookWithNonAlphaBeticLastCharacterInAuthorName_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
//        Book book = new Book("978-92-95055-02-5","title", "author'");
        when(mockBook.getAuthor()).thenReturn("author'");
        //2. Action
        //2.1 call the method under test with author string ending with non alphabetic character
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid author.");
    }

    @Test
    public void givenBookWithNonAlphaBeticMiddleCharacterInAuthorName_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
//        Book book = new Book("978-92-95055-02-5","title", "author'");
        when(mockBook.getAuthor()).thenReturn("aut,hor");
        //2. Action
        //2.1 call the method under test with author string ending with non alphabetic character
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid author.");
    }

    @Test
    public void givenBookWithConsecutiveSpecialCharactersInAuthorName_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        //Book book = new Book("978-92-95055-02-5","title", "Mark--Twein");
        when(mockBook.getAuthor()).thenReturn("Mark--Twein");
        //2. Action
        //2.1 call the method under test with author having consecutive special characters
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid author.");
    }


    // test invalid borrowed state
    @Test
    public void givenBookWithTrueForBorrowedState_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        when(mockBook.getAuthor()).thenReturn("Mark-Twein");
        when(mockBook.isBorrowed()).thenReturn(true);
        //2. Action
        //2.1 call the method under test with book being borrowed
        //3. Assertion
        // 3.1 Verify IllegalArgumentException("Invalid title.") being thrown
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Book with invalid borrowed state.");
    }

    @Test
    public void givenABookThatIsInTheDataBaseAlready_whenAddBook_ThenRaiseIllegalArgumentException() {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        when(mockBook.getAuthor()).thenReturn("Mark-Twein");
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(mockBook);
        });
        verify(mockDataBaseService, never()).addBook(anyString(),any());
        assertEquals(testException.getMessage(),"Book already exists.");
    }

    @Test
    public void givenAllTheCorrectInfo_whenAddBook_theFunctionShouldBeExecutesFully()
    {
        // 1. Arrange
        // 1.1 create an instance of Library with mocks
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockBook.getISBN()).thenReturn("978-92-95055-02-5");
        when(mockBook.getTitle()).thenReturn("title");
        when(mockBook.getAuthor()).thenReturn("Mark-Twein");
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(null);
        library.addBook(mockBook);
        verify(mockDataBaseService).addBook("978-92-95055-02-5",mockBook);

    }

    //Test registerUser method
    @Test
    public void givenAUserThatIsNull_whenRegisterUser_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(null);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid user.");
    }

    @Test
    public void givenUserIdIsNull_whenRegisterUser_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn(null);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenUserIdIsLessThenTwelveDigits_whenRegisterUser_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("123456789");
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenUserIdIsMoreThenTwelveDigits_whenRegisterUser_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("1234567891012");
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenUserIdIsAnEmptyString_whenRegisterUser_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("");
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }
    @Test
    public void givenUserNameIsNull_whenRegisterUser_ThenRaiseIllegalArgumentException(){
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("123456789101");
        when(mockUser.getName()).thenReturn(null);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid user name.");
    }

    @Test
    public void givenUserNameIsEmptyString_whenRegisterUser_ThenRaiseIllegalArgumentException(){
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("123456789101");
        when(mockUser.getName()).thenReturn("");
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid user name.");
    }

    @Test
    public void givenUserGetNotificationIsNull_whenRegisterUser_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("123456789101");
        when(mockUser.getName()).thenReturn("name");
        when(mockUser.getNotificationService()).thenReturn(null);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"Invalid notification service.");
    }

    @Test
    public void givenUserIdExistsInDataBase_whenRegisterUser_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("123456789101");
        when(mockUser.getName()).thenReturn("name");
        when(mockUser.getNotificationService()).thenReturn(mockNotificationService);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.registerUser(mockUser);
        });
        verify(mockDataBaseService, never()).registerUser(anyString(),any());
        assertEquals(testException.getMessage(),"User already exists.");
    }

    @Test
    public void givenAllInfoCorrect_whenRegisterUser_thenFunctionFllowsCompletly()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockUser.getId()).thenReturn("123456789101");
        when(mockUser.getName()).thenReturn("name");
        when(mockUser.getNotificationService()).thenReturn(mockNotificationService);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(null);
        library.registerUser(mockUser);
        verify(mockDataBaseService).registerUser("123456789101",mockUser);
    }

    //Test for borrow book
    @Test
    public void givenISBNisNotNfLengthThirdteen_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-92-95055-02-", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }
    @Test
    public void givenISBNIsNull_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook(null, "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid ISBN.");

    }
    @Test
    public void givenBookISBNisLessThenLengthThirdteen_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-0-13-12456-7", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid ISBN.");

    }

    @Test
    public void givenBookISBNIsmoreThanThirdteen_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-0-13-12456-712", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid ISBN.");

    }

    @Test
    public void givenISBNNumberHasLetter_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-0-13-124b6-7", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenBookISBNDOESnotMatchTheISBNrule_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-92-95055-02-7", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenAnIllegalISBNNumberWithTheSumOfTheDigitsModuluTenIsZero_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("678-0-13-123456-4", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenBookWithValidISBNTheBookIsNull_whenBorrowBook_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(null);
        Exception testException = assertThrows(BookNotFoundException.class, () -> {
            library.borrowBook("978-92-95055-02-5", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Book not found!");
    }

    @Test
    public void givenUserWithIDLessThenTwelveNumbers_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-92-95055-02-5", "123");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }
    @Test
    public void givenUserWithIDMoreThenTwelveNumbers_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-92-95055-02-5", "1234567891012");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenUserWithNullID_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("978-92-95055-02-5", null);
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenUserNotInTheDataBase_whenBorrowBook_ThenRaiseUserNotRegisteredException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(null);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        Exception testException = assertThrows(UserNotRegisteredException.class, () -> {
            library.borrowBook("978-92-95055-02-5", "123456789101");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"User not found!");
    }

    @Test
    public void givenBorrowedBookIsAlreadyBorrowed_whenBorrowBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(true);
        Exception testException = assertThrows(BookAlreadyBorrowedException.class, () -> {
            library.borrowBook("978-92-95055-02-5", "123456789101");
        });
        verify(mockBook, never()).borrow();
        verify(mockDataBaseService, never()).borrowBook(anyString(),anyString());
        assertEquals(testException.getMessage(),"Book is already borrowed!");
    }

    @Test
    public void givenAllInfoIsCorrect_whenBorrowBook_thenTheFunctionShouldWork()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(false);
        library.borrowBook("978-92-95055-02-5", "123456789101");
        verify(mockBook).borrow();
        verify(mockDataBaseService).borrowBook("978-92-95055-02-5", "123456789101");

    }

    //Test returnBook
    @Test
    public void givenISBNisNotNfLengthThirdteen_whenReturnBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("978-92-95055-02-");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-92-95055-02-");
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("978-92-95055-02-");
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }
    @Test
    public void givenISBNIsNull_whenReturnBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook(null);
        });
        verify(mockDataBaseService, never()).getBookByISBN(null);
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook(null);
        assertEquals(testException.getMessage(),"Invalid ISBN.");

    }
    @Test
    public void givenBookISBNisLessThenLengthThirdteen_whenReturnBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("978-0-13-12456-7");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-0-13-12456-7");
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("978-0-13-12456-7");
        assertEquals(testException.getMessage(),"Invalid ISBN.");

    }

    @Test
    public void givenBookISBNIsmoreThanThirdteen_whenReturnBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("978-0-13-12456-712");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-0-13-12456-712");
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("978-0-13-12456-712");
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenISBNNumberHasLetter_whenReturnBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("978-0-13-124b6-7");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-0-13-124b6-7");
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("978-0-13-124b6-7");
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenBookISBNDOESnotMatchTheISBNrule_whenReturnBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("978-92-95055-02-7");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-92-95055-02-7");
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("978-92-95055-02-7");
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenAnIllegalISBNNumberWithTheSumOfTheDigitsModuluTenIsZero_whenReturnBook_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("678-0-13-123456-4");
        });
        verify(mockDataBaseService, never()).getBookByISBN("678-0-13-123456-4");
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("678-0-13-123456-4");
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }


    @Test
    public void givenBookWithValidISBNIsNull_whenReturnBook_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(null);
        Exception testException = assertThrows(BookNotFoundException.class, () -> {
            library.returnBook("978-92-95055-02-5");
        });
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("978-92-95055-02-5");
        assertEquals(testException.getMessage(),"Book not found!");
    }

    @Test
    public void givenBookWasntBorrowed_whenReturnBook_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(false);
        Exception testException = assertThrows(BookNotBorrowedException.class, () -> {
            library.returnBook("978-92-95055-02-5");
        });
        verify(mockBook, never()).returnBook();
        verify(mockDataBaseService, never()).returnBook("978-92-95055-02-5");
        assertEquals(testException.getMessage(),"Book wasn't borrowed!");
    }

    @Test
    public void givenAllInfoIsCorrect_whenReturnBook_thenTheFunctionShouldWorkCompletely()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(true);
        library.returnBook("978-92-95055-02-5");
        verify(mockBook).returnBook();
        verify(mockDataBaseService).returnBook("978-92-95055-02-5");
    }

    //Test notify user with book reviews
    @Test
    public void givenInvalidISBNnumber_whenNotifyUserWithBookReviews_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-", "123");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-92-95055-02-");
        verify(mockDataBaseService,never()).getUserById("123");
        verify(mockReviewService,never()).getReviewsForBook("978-92-95055-02-");
        verify(mockReviewService,never()).close();
        assertEquals(testException.getMessage(),"Invalid ISBN.");
    }

    @Test
    public void givenUserWithInvalidID_whenNotifyUserWithBookReviews_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", "123");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-92-95055-02-");
        verify(mockDataBaseService,never()).getUserById("123");
        verify(mockReviewService,never()).getReviewsForBook("978-92-95055-02-");
        verify(mockReviewService,never()).close();
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenUserWithNullID_whenNotifyUserWithBookReviews_ThenRaiseIllegalArgumentException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", null);
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-92-95055-02-");
        verify(mockDataBaseService,never()).getUserById("123");
        verify(mockReviewService,never()).getReviewsForBook("978-92-95055-02-");
        verify(mockReviewService,never()).close();
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenBookNotFoundInDataBase_whenNotifyUserWithBookReviews_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(null);
        Exception testException = assertThrows(BookNotFoundException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", "123456789101");
        });
        verify(mockDataBaseService,never()).getUserById("123");
        verify(mockReviewService,never()).getReviewsForBook("978-92-95055-02-");
        verify(mockReviewService,never()).close();
        assertEquals(testException.getMessage(),"Book not found!");
    }

    @Test
    public void givenUserNotFoundInTheDataBase_whenNotifyUserWithBookReviews_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(null);
        Exception testException = assertThrows(UserNotRegisteredException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", "123456789101");
        });
        verify(mockReviewService,never()).getReviewsForBook("978-92-95055-02-");
        verify(mockReviewService,never()).close();
        assertEquals(testException.getMessage(),"User not found!");
    }

    @Test
    public void givenNoReviewsForTheBookInTheReviewList_whenNotifyUserWithBookReviews_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        List<String> reviews = new ArrayList<>();
        when(mockReviewService.getReviewsForBook("978-92-95055-02-5")).thenReturn(reviews);
        Exception testException = assertThrows(NoReviewsFoundException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", "123456789101");
        });
        assertEquals(testException.getMessage(),"No reviews found!");
    }

    @Test
    public void givenNullForReviewsForTheBookList_whenNotifyUserWithBookReviews_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        when(mockReviewService.getReviewsForBook("978-92-95055-02-5")).thenReturn(null);
        Exception testException = assertThrows(NoReviewsFoundException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", "123456789101");
        });
        assertEquals(testException.getMessage(),"No reviews found!");
    }

    @Test
    public void givenAnIssueFetchingTheReviews_whenNotifyUserWithBookReviews_ThenRaiseBookNotFoundException() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        when(mockReviewService.getReviewsForBook("978-92-95055-02-5")).thenThrow(new ReviewException("Error"));
        Exception testException = assertThrows(ReviewServiceUnavailableException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", "123456789101");
        });
        assertEquals(testException.getMessage(),"Review service unavailable!");

    }

    @Test
    public void givenTheReviewsWereFetchedAndTheNotificationWasSentSuccessfully_whenNotifyUserWithBookReviews_thenReturn() {
        Library library = new Library(mockDataBaseService, mockReviewService);
        List<String> reviews = new ArrayList<>();
        reviews.add("great book");
        when(mockReviewService.getReviewsForBook("978-92-95055-02-5")).thenReturn(reviews);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        when(mockBook.getTitle()).thenReturn("title");
        library.notifyUserWithBookReviews("978-92-95055-02-5", "123456789101");
        verify(mockDataBaseService).getBookByISBN("978-92-95055-02-5");
        verify(mockDataBaseService).getUserById("123456789101");
        verify(mockReviewService).getReviewsForBook("978-92-95055-02-5");
        verify(mockReviewService).close();
    }

    @Test
    public void givenNotificationFailedToBeSent_whenNotifyUserWithBookReviews_thenNotificationException()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        List<String> reviews = new ArrayList<>();
        reviews.add("great book");
        when(mockReviewService.getReviewsForBook("978-92-95055-02-5")).thenReturn(reviews);
        when(mockDataBaseService.getBookByISBN("978-92-95055-02-5")).thenReturn(mockBook);
        when(mockDataBaseService.getUserById("123456789101")).thenReturn(mockUser);
        when(mockBook.getTitle()).thenReturn("title");

        doThrow(new NotificationException("error")).when(mockUser).sendNotification(anyString());
        Exception testException = assertThrows(NotificationException.class, () -> {
            library.notifyUserWithBookReviews("978-92-95055-02-5", "123456789101");
        });
        verify(mockDataBaseService).getBookByISBN("978-92-95055-02-5");
        verify(mockDataBaseService).getUserById("123456789101");
        verify(mockReviewService).getReviewsForBook("978-92-95055-02-5");
        verify(mockReviewService).close();
        assertEquals(testException.getMessage(),"Notification failed!");

    }

    //Tests for getBookByISBN(String ISBN, String userId)
    @Test
    public void givenInvalidISBNnumber_whenGetBookByISBN_thenIllegalArgumentException()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        assertThrows(IllegalArgumentException.class, () -> {
            library.getBookByISBN("978-92-95055-02-", "123456789101");
        });
    }

    @Test
    public void givenInvalidUserIdIsNullISBNisValid_whenGetBookByISBN_thenIllegalArgumentException()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.getBookByISBN("978-92-95055-02-5", null);
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-92-95055-02-5");
        assertEquals(testException.getMessage(),"Invalid user Id.");

    }

    @Test
    public void givenInvalidUserIdISBNisValid_whenGetBookByISBN_thenIllegalArgumentException()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        Exception testException = assertThrows(IllegalArgumentException.class, () -> {
            library.getBookByISBN("978-92-95055-02-5", "12345678");
        });
        verify(mockDataBaseService, never()).getBookByISBN("978-92-95055-02-5");
        assertEquals(testException.getMessage(),"Invalid user Id.");
    }

    @Test
    public void givenBookDoesntExistInTheDataBase_whenGetBookByISBN_thenBookNotFoundException()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN(anyString())).thenReturn(null);
        Exception testException = assertThrows(BookNotFoundException.class, () -> {
            library.getBookByISBN("978-92-95055-02-5", "123456789101");
        });
        assertEquals(testException.getMessage(),"Book not found!");
    }

    @Test
    public void givenBookIsBorrowed_whenGetBookByISBN_thenBookAlreadyBorrowedException()
    {
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN(anyString())).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(true);
        Exception testException = assertThrows(BookAlreadyBorrowedException.class, () -> {
            library.getBookByISBN("978-92-95055-02-5", "123456789101");
        });
        assertEquals(testException.getMessage(),"Book was already borrowed!");
    }

    @Test
    public void givenThereIsAnErrorWithTheNotificationTheBookWillBeReturned_whenGetBookByISBN_thenBookAlreadyBorrowedException()
    {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Library library = new Library(mockDataBaseService, mockReviewService);
        when(mockDataBaseService.getBookByISBN(anyString())).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(false);
        when(mockDataBaseService.getUserById(anyString())).thenReturn(mockUser);
        assertTrue(library.getBookByISBN("978-92-95055-02-5","123456789101") instanceof Book);
    }
}
