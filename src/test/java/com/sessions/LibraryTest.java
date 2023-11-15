package com.sessions;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class LibraryTest {

    Library library;
    @BeforeAll
    public static void beforeAll(){
        System.out.println("At beforeAll");
    }
    @AfterAll
    public  static void afterAll(){
        System.out.println("At afterAll");
    }

    @BeforeEach
    public void setup(){
        library=new Library();
        System.out.println("At beforeEach");
    }

    @AfterEach
    public  void afterEach(){
        System.out.println("At afterEach");
    }

    @Test
    public  void the_default_number_of_books_in_Library_should_be_1_Initially(){

        int totalNumberofBooks=library.getBooks().size();
        assertEquals(1,totalNumberofBooks);

    }
@Test
    public  void adding_to_catalogue_should_increase_the_size_of_books_and_newly_created_book_id_should_be_2(){

        Book newlyCreatedBook=library.addToCatalogue("Discovery of India","Jawahararlal Nwhru",432,11.99);


        int totalBooks=library.getBooks().size();
        List<Book> availableBooks=library.getBooks();

        assertEquals(2,newlyCreatedBook.getId());

        assertThat(totalBooks,equalTo(2));

        assertThat(availableBooks,hasItem(newlyCreatedBook));
}

@Test
    public  void findBookByName_called_whit_bookname_available_in_library_should_return_book_object(){

    Book book=library.findBookByName("The God Of Small Things");
    assertNotNull(book);
}
@Test
    public  void findBookByName_called_whit_non_existant_bookname_should_return_null(){

    Book book=library.findBookByName("some invalid name");
    assertNull(book);

}

   @Test
   public void caculateBookRent_should_return_2_dollars_if_number_of_day_is_4(){
       RentedBook rentedBook= Mockito.mock(RentedBook.class);
       LocalDate fourDaysBeforeToday=LocalDate.now().minusDays(4);
       Mockito.when(rentedBook.getRentedDate()).thenReturn(fourDaysBeforeToday);
       Double calculatedPrice=library.calculateBookRent(rentedBook);
       assertThat(calculatedPrice,equalTo(Double.valueOf(2)));
       Mockito.verify(rentedBook,Mockito.times(2)).getRentedDate();
   }

    @Test
    public void caculateBookRent_should_return_6_dollars_if_number_of_day_is_6(){
        RentedBook rentedBook= Mockito.mock(RentedBook.class);
        LocalDate sixDaysBeforeToday=LocalDate.now().minusDays(6);
        Mockito.when(rentedBook.getRentedDate()).thenReturn(sixDaysBeforeToday);
        Double calculatedPrice=library.calculateBookRent(rentedBook);
        assertThat(calculatedPrice,equalTo(Double.valueOf(6)));
        Mockito.verify(rentedBook,Mockito.times(2)).getRentedDate();
    }


    // When returning book along with amount,
    // The receipt should be returned  , it should have
        //The date of receipt - should be current date
        //book name
        //amountGiven , actualAmount
        //balanceToBeRerurned
    //and the book should be available again in library

    //if the amount provided is less than the amount ,
        //An Exception should be thrown mentioning lower amount

    @Test
    public void when_returning_book_receipt_should_be_returned(){
        RentedBook rentedBook= library.rent("The God Of Small Things");

        Double amount=3.0;
        Receipt receipt=library.returnBook(rentedBook,amount);
        assertNotNull(receipt);
        assertThat(receipt.bookname,equalTo("The God Of Small Things"));
        assertThat(receipt.receiptDate,equalTo(LocalDate.now()));

    }

}