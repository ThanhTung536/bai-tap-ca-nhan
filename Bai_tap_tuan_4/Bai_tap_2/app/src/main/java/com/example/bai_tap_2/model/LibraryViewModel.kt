package com.example.bai_tap_2.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LibraryViewModel : ViewModel() {

    val students = mutableStateListOf<Student>(
        Student(id = "SV001", name = "Nguyen Van A", studentClass = "CNTT1"),
        Student(id = "SV002", name = "Tran Thi B", studentClass = "CNTT2"),
        Student(id = "SV003", name = "Le Van C", studentClass = "KTPM")
    )

    var selectedStudent by mutableStateOf<Student>(students[0])
        private set

    val books = mutableStateListOf<LibraryItem>(
        Book(id = "B01", title = "Sách 01", author = "Nguyễn Văn X", category = "Lập trình"),
        Book(id = "B02", title = "Sách 02", author = "Trần Thị Y", category = "Cơ sở dữ liệu"),
        Book(id = "B03", title = "Sách 03", author = "Lê Văn Z", category = "Mạng máy tính"),
        ReferenceBook(id = "B04", title = "Sách Tham Khảo 01", author = "Phạm Văn K", isSpecialEdition = true),
        Book(id = "B05", title = "Sách 05", author = "Hoàng Thị M", category = "Kỹ thuật phần mềm")
    )

    fun selectStudent(student: Student) {
        selectedStudent = student
    }

    fun updateSelectedStudentName(newName: String) {
        if (newName.isNotBlank()) {
            selectedStudent.name = newName
            val index = students.indexOfFirst { it.id == selectedStudent.id }
            if (index != -1) {
                students[index] = selectedStudent
            }
        }
    }

    fun updateBorrowState(selectedBookIds: List<String>) {
        val currentStudentId = selectedStudent.id

        selectedStudent.setBorrowedBooks(selectedBookIds)

        for (book in books) {
            if (selectedBookIds.contains(book.id)) {
                book.borrowItem(currentStudentId)
            } else if (book.borrowedByStudentId == currentStudentId) {
                book.returnItem()
            }
        }
    }

    fun addNewBook(title: String, author: String, isReference: Boolean, categoryOrEdition: String) {
        val newId = "B0${books.size + 1}"
        val newBook: LibraryItem = if (isReference) {
            ReferenceBook(id = newId, title = title, author = author, isSpecialEdition = categoryOrEdition.contains("đặc biệt", ignoreCase = true))
        } else {
            Book(id = newId, title = title, author = author, category = if (categoryOrEdition.isBlank()) "Giáo trình" else categoryOrEdition)
        }
        books.add(newBook)
    }

    fun addNewStudent(name: String, studentClass: String) {
        val newId = "SV00${students.size + 1}"
        val newStudent = Student(id = newId, name = name, studentClass = if (studentClass.isBlank()) "CNTT K65" else studentClass)
        students.add(newStudent)
    }
}
