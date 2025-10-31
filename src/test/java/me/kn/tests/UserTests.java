package me.kn.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests covering the user form and table behaviours. Validation of required
 * fields and phone number formats are exercised, along with editing and
 * deleting an existing user. Where network operations would normally occur,
 * these tests stub the API methods to operate on in‑page state only.
 */
public class UserTests extends BaseTestGrid {

    @Test
    @DisplayName("Creating a user with all fields empty shows all validation errors")
    public void testCreateUserAllFieldsEmptyShowsValidationErrors() {
        // Ensure form fields are empty
        userPage.setName("");
        userPage.setEmail("");
        userPage.setPhone("");
        userPage.setTerms(false);

        // Trigger creation
        userPage.clickAdd();

        // Expect multiple error messages
        String message = userPage.getFormMessage();
        assertThat(message).contains("Họ tên không được để trống");
        assertThat(message).contains("Email không được để trống");
        assertThat(message).contains("Số điện thoại không được để trống");
        assertThat(message).contains("Vui lòng đồng ý với điều khoản");

        // Each field should be marked invalid
        assertThat(userPage.isInvalid("name")).isTrue();
        assertThat(userPage.isInvalid("email")).isTrue();
        assertThat(userPage.isInvalid("phone")).isTrue();
    }

    @Test
    @DisplayName("Missing email triggers only email validation error")
    public void testCreateUserMissingEmailOnlyShowsEmailValidation() {
        userPage.setName("Nguyễn Văn B");
        userPage.setEmail("");
        userPage.setPhone("0123456789");
        userPage.selectGender("Nữ");
        userPage.setTerms(true);

        userPage.clickAdd();

        String message = userPage.getFormMessage();
        assertThat(message).contains("Email không được để trống");
        assertThat(message).doesNotContain("Họ tên không được để trống");
        assertThat(message).doesNotContain("Số điện thoại không được để trống");

        // Only email should be invalid
        assertThat(userPage.isInvalid("email")).isTrue();
        assertThat(userPage.isInvalid("name")).isFalse();
        assertThat(userPage.isInvalid("phone")).isFalse();
    }

    @Test
    @DisplayName("Invalid phone number triggers correct validation message")
    public void testCreateUserInvalidPhoneShowsValidation() {
        userPage.setName("Nguyễn Văn C");
        userPage.setEmail("c@example.com");
        userPage.setPhone("12345"); // invalid length
        userPage.selectGender("Khác");
        userPage.setTerms(true);

        userPage.clickAdd();

        String message = userPage.getFormMessage();
        assertThat(message).contains("Số điện thoại phải có 10 chữ số");
        assertThat(userPage.isInvalid("phone")).isTrue();
    }

    @Test
    @DisplayName("Add user valid")
    public void testAddUserValid(){
        userPage.setName("Nguyễn Văn C");
        userPage.setEmail("c@example.com");
        userPage.setPhone("0901234567");
        userPage.selectGender("Khác");
        userPage.setTerms(true);

        userPage.clickAdd();
        userPage.waitForFormMessage("Thêm");
        userPage.waitUntilRowByEmail("c@example.com");

        String message = userPage.getFormMessage();
        assertThat(message).contains("Thêm người dùng thành công!");
    }

    @Test
    @DisplayName("Edit user ")
    public void testEditUser() {

        userPage.setName("Nguyễn Văn C");
        userPage.setEmail("c@example.com");
        userPage.setPhone("0901234567");
        userPage.selectGender("Khác");
        userPage.setTerms(true);
        userPage.clickAdd();

        userPage.waitForFormMessage("Thêm");
        userPage.waitUntilRowByEmail("c@example.com");

        assertTrue(userPage.clickEditByEmail("c@example.com"));

        userPage.setName("Nguyễn Văn C (Updated)");
        userPage.setEmail("c.updated@example.com");

        userPage.clickAdd();
        userPage.waitForFormMessage("Cập nhật");
        userPage.waitUntilRowByEmail("c.updated@example.com");

        assertThat(userPage.getFormMessage()).contains("Cập nhật thông tin thành công!");
        assertThat(userPage.getRowTextByEmail("c.updated@example.com")).contains("Nguyễn Văn C (Updated)");
    }

    @Test
    @DisplayName("Delete user")
    public void testDeleteUser() {

        userPage.setName("Nguyễn Văn C");
        userPage.setEmail("c@example.com");
        userPage.setPhone("0901234567");
        userPage.selectGender("Khác");
        userPage.setTerms(true);
        userPage.clickAdd();

        userPage.waitForFormMessage("Thêm");
        userPage.waitUntilRowByEmail("c@example.com");
        int size = userPage.getTableRows().size();

        assertTrue(userPage.clickDeleteByEmail("c@example.com"));
        userPage.waitForFormMessage("Xóa");

        assertThat(userPage.getFormMessage()).contains("Xóa người dùng thành công!");
        assertThat(userPage.getTableRows()).hasSize(size - 1);
    }
}