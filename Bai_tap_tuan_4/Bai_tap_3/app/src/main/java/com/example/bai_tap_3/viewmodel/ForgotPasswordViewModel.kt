package com.example.bai_tap_3.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bai_tap_3.model.ResetPasswordData

/**
 * ForgotPasswordViewModel - ViewModel (OOP: ViewModel layer trong mô hình MVVM).
 *
 * Nguyên lý OOP được áp dụng:
 * - Encapsulation (Đóng gói): Giấu state bên trong private setter (`private set`),
 *   chỉ cho phép sửa đổi dữ liệu qua các phương thức được định nghĩa rõ ràng.
 * - Single Source of Truth (Nguồn dữ liệu duy nhất): Quản lý toàn bộ state form
 *   và dữ liệu đã submit giữa 4 màn hình.
 * - Single Responsibility: Chịu trách nhiệm quản lý state và business logic của luồng reset password.
 */
class ForgotPasswordViewModel : ViewModel() {

    /**
     * Form state chứa dữ liệu đang nhập qua từng màn hình (Screen 1 -> Screen 2 -> Screen 3 -> Screen 4).
     * private set: Chỉ ViewModel có quyền cập nhật state này.
     */
    var formState by mutableStateOf(ResetPasswordData())
        private set

    /**
     * Dữ liệu đã Submit thành công từ Màn hình 4.
     * Dữ liệu này sẽ được truyền/hiển thị ở khối "email - otp - pass (từ màn hình 4)" tại Màn hình 1.
     */
    var submittedResult by mutableStateOf<ResetPasswordData?>(null)
        private set

    /**
     * Cập nhật Email từ Màn hình 1 (Forgot Password).
     */
    fun updateEmail(email: String) {
        formState = formState.copy(email = email)
    }

    /**
     * Cập nhật OTP từ Màn hình 2 (Verification).
     */
    fun updateOtp(otp: String) {
        formState = formState.copy(otp = otp)
    }

    /**
     * Cập nhật Password mới từ Màn hình 3 (Reset Password).
     */
    fun updatePassword(password: String) {
        formState = formState.copy(password = password)
    }

    /**
     * Xử lý khi nhấn nút "Summit" ở Màn hình 4 (Confirm).
     * Lưu trữ dữ liệu hiện tại vào `submittedResult` để hiển thị ở Màn hình 1,
     * đồng thời làm mới `formState`.
     */
    fun submitForm() {
        submittedResult = formState
        formState = ResetPasswordData()
    }

    /**
     * Reset lại toàn bộ form và dữ liệu submit.
     */
    fun resetAll() {
        formState = ResetPasswordData()
        submittedResult = null
    }
}
