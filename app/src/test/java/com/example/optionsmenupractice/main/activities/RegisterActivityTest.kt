import com.example.optionsmenupractice.main.activities.RegisterActivity.Companion.performRegistration
import com.example.optionsmenupractice.main.activities.RegisterActivity.Companion.validateEmptyFields
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterActivityTest {

    @Test
    fun testEmptyFieldsValidation() {
        // Test case where all fields are empty
        assertFalse(validateEmptyFields("", "", "", ""))

        // Test case where some fields are empty
        assertFalse(validateEmptyFields("John", "", "", ""))
        assertFalse(validateEmptyFields("", "Doe", "", ""))
        assertFalse(validateEmptyFields("", "", "johndoe", ""))
        assertFalse(validateEmptyFields("", "", "", "password"))

        // Test case where all fields are filled
        assertTrue(validateEmptyFields("John", "Doe", "johndoe", "password"))
    }

    @Test
    fun testPerformRegistration() {
        // Test case where all fields are filled
        assertTrue(performRegistration("John", "Doe", "johndoe", "password"))


    }
}
