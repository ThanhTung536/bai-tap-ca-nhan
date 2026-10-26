package com.example.bai_tap_2.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
interface IBorrowable {
    fun borrowItem(studentId: String): Boolean
    fun returnItem(): Boolean
    fun isAvailable(): Boolean
}

abstract class LibraryItem(
    val id: String,
    open val title: String,
    open val author: String
) : IBorrowable {

    private var _borrowedByStudentId: String? by mutableStateOf(null)

    val borrowedByStudentId: String?
        get() = _borrowedByStudentId

    override fun isAvailable(): Boolean {
        return _borrowedByStudentId == null
    }

    override fun borrowItem(studentId: String): Boolean {
        if (isAvailable()) {
            _borrowedByStudentId = studentId
            return true
        }
        return false
    }

    override fun returnItem(): Boolean {
        if (!isAvailable()) {
            _borrowedByStudentId = null
            return true
        }
        return false
    }

    abstract fun getDisplayDetails(): String
}

class Book(
    id: String,
    title: String,
    author: String,
    val category: String = "Giáo trình"
) : LibraryItem(id, title, author) {
    override fun getDisplayDetails(): String {
        return "Sách: $title | Tác giả: $author | Thể loại: $category"
    }
}

class ReferenceBook(
    id: String,
    title: String,
    author: String,
    val isSpecialEdition: Boolean = false
) : LibraryItem(id, title, author) {

    override fun getDisplayDetails(): String {
        val editionInfo = if (isSpecialEdition) "[Bản đặc biệt]" else "[Bản thường]"
        return "Sách tham khảo: $title | Tác giả: $author $editionInfo"
    }
}

abstract class Person(
    val id: String,
    open var name: String
) {
    abstract fun getRoleDescription(): String
}

class Student(
    id: String,
    name: String,
    val studentClass: String = "CNTT K65"
) : Person(id, name) {

    private val _borrowedBookIds = mutableStateListOf<String>()
    val borrowedBookIds: List<String> get() = _borrowedBookIds

    fun addBorrowedBook(bookId: String) {
        if (!_borrowedBookIds.contains(bookId)) {
            _borrowedBookIds.add(bookId)
        }
    }

    fun removeBorrowedBook(bookId: String) {
        _borrowedBookIds.remove(bookId)
    }

    fun setBorrowedBooks(bookIds: List<String>) {
        _borrowedBookIds.clear()
        _borrowedBookIds.addAll(bookIds)
    }

    override fun getRoleDescription(): String {
        return "Sinh viên: $name (Mã SV: $id - Lớp: $studentClass)"
    }
}
