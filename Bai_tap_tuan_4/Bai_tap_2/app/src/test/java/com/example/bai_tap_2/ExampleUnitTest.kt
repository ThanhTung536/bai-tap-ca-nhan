package com.example.bai_tap_2

import com.example.bai_tap_2.model.Book
import com.example.bai_tap_2.model.LibraryItem
import com.example.bai_tap_2.model.LibraryViewModel
import com.example.bai_tap_2.model.ReferenceBook
import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun testOOP_Encapsulation() {
        val book = Book(id = "B01", title = "Sách 01", author = "Tác giả A")
        // Trạng thái mượn mặc định là null
        assertNull(book.borrowedByStudentId)
        assertTrue(book.isAvailable())

        // Mượn sách
        val success = book.borrowItem("SV001")
        assertTrue(success)
        assertEquals("SV001", book.borrowedByStudentId)
        assertFalse(book.isAvailable())

        // Không thể mượn lại sách đã mượn
        val doubleBorrow = book.borrowItem("SV002")
        assertFalse(doubleBorrow)
    }

    @Test
    fun testOOP_Polymorphism_And_Inheritance() {
        // Khai báo kiểu dữ liệu danh sách trừu tượng LibraryItem
        val items: List<LibraryItem> = listOf(
            Book(id = "B01", title = "Sách Giáo Trình", author = "Nguyễn Văn A", category = "CNTT"),
            ReferenceBook(id = "B02", title = "Sách Tham Khảo", author = "Trần Thị B", isSpecialEdition = true)
        )

        // Kiểm tra tính đa hình của getDisplayDetails()
        val bookDetails = items[0].getDisplayDetails()
        val refBookDetails = items[1].getDisplayDetails()

        assertTrue(bookDetails.contains("Sách: Sách Giáo Trình"))
        assertTrue(refBookDetails.contains("Sách tham khảo: Sách Tham Khảo"))
        assertTrue(refBookDetails.contains("[Bản đặc biệt]"))
    }

    @Test
    fun testViewModel_BorrowLogic() {
        val viewModel = LibraryViewModel()
        val student = viewModel.selectedStudent

        // Mượn Sách 01 và Sách 02
        viewModel.updateBorrowState(listOf("B01", "B02"))

        assertEquals(2, student.borrowedBookIds.size)
        assertTrue(student.borrowedBookIds.contains("B01"))
        assertTrue(student.borrowedBookIds.contains("B02"))

        // Kiểm tra trạng thái sách trong ViewModel
        val book1 = viewModel.books.first { it.id == "B01" }
        assertEquals(student.id, book1.borrowedByStudentId)
    }
}
