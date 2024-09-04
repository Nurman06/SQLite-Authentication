package com.example.rt
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.HideReturnsTransformationMethod
import android.widget.ImageView

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var ivShowHidePassword: ImageView
    private lateinit var dbHelper: DatabaseHelper
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvRegister = findViewById(R.id.tv_register)
        ivShowHidePassword = findViewById(R.id.iv_show_hide_password)

        dbHelper = DatabaseHelper(this)

        val spannableString = SpannableString(tvRegister.text)
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvRegister.text = spannableString

        ivShowHidePassword.setOnClickListener {
            if (isPasswordVisible) {
                // Hide Password
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                ivShowHidePassword.setImageResource(R.drawable.ic_visibility_off)
                isPasswordVisible = false
            } else {
                // Show Password
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                ivShowHidePassword.setImageResource(R.drawable.ic_visibility)
                isPasswordVisible = true
            }
            // Move the cursor to the end of the text
            etPassword.setSelection(etPassword.text.length)
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (dbHelper.getUser(username, password)) {
                // Login successful, redirect to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            } else {
                // Login failed, display error message
                Toast.makeText(this, "Username or password is incorrect. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegister.setOnClickListener {  // Menggunakan TextView baru
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}