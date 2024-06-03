package id.inixindo.myapplication

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.inixindo.myapplication.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()
        binding.submitButton.setOnClickListener { submitForm() }
    }

    private fun submitForm() {
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()
        binding.phoneContainer.helperText = validPhone()

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null

        if (validEmail && validPassword && validPhone)
            resetForm()
        else
            invalidForm()
    }

    private fun resetForm() {
        var message = "Email: " + binding.emailEditTxt.text
        message += "\nPassword: " + binding.passwordEditTxt.text
        message += "\nPhone: " + binding.phoneEditTxt.text

        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                binding.emailEditTxt.text = null
                binding.passwordEditTxt.text = null
                binding.phoneEditTxt.text = null
                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)
            }.show()
    }

    private fun invalidForm() {
        var message = ""
        if (binding.emailContainer.helperText != null)
            message += "\n\nEmail: " + binding.emailContainer.helperText
        if (binding.passwordContainer.helperText != null)
            message += "\n\nPassword: " + binding.passwordContainer.helperText
        if (binding.phoneContainer.helperText != null)
            message += "\n\nPhone: " + binding.phoneContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                // do nothing
            }.show()
    }

    private fun emailFocusListener() {
        binding.emailEditTxt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailTxt = binding.emailEditTxt.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            return "Invalid email address"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.passwordEditTxt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordTxt = binding.passwordEditTxt.text.toString()
        if (passwordTxt.length < 8) {
            return "Password length at least 8 characters"
        }
        if (!passwordTxt.matches(".*[A-Z].*".toRegex())) {
            return "Password at least 1 Uppercase character"
        }
        if (!passwordTxt.matches(".*[a-z].*".toRegex())) {
            return "Password at least 1 Lowercase character"
        }
        if (!passwordTxt.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Password at least 1 Special character (@#\$%^&+=)"
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneEditTxt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val phoneTxt = binding.phoneEditTxt.text.toString()
        if (!phoneTxt.matches(".*[0-9].*".toRegex())) {
            return "Phone must be numbers"
        }
        if (phoneTxt.length < 12) {
            return "Phone number must be 12 digits"
        }
        return null
    }
}