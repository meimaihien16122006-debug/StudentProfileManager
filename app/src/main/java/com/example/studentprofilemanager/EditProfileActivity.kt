package com.example.studentprofilemanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentprofilemanager.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // =========================
        // NHẬN STUDENT TỪ MAIN
        // =========================
        @Suppress("DEPRECATION")
        student =
            intent.getSerializableExtra("STUDENT") as? Student
                ?: run {
                    finish()
                    return
                }

        // =========================
        // HIỂN THỊ THÔNG TIN CŨ
        // =========================
        binding.edtName.setText(student.fullName)

        binding.edtClass.setText(student.className)

        binding.edtGpa.setText(
            student.gpa.toString()
        )

        // =========================
        // NÚT LƯU
        // =========================
        binding.btnSave.setOnClickListener {

            val name =
                binding.edtName.text.toString().trim()

            val className =
                binding.edtClass.text.toString().trim()

            val gpaText =
                binding.edtGpa.text.toString().trim()

            // Kiểm tra họ tên
            if (name.isEmpty()) {
                binding.edtName.error =
                    "Vui lòng nhập họ tên"
                return@setOnClickListener
            }

            // Kiểm tra lớp
            if (className.isEmpty()) {
                binding.edtClass.error =
                    "Vui lòng nhập lớp"
                return@setOnClickListener
            }

            // Chuyển GPA sang Double
            val gpa =
                gpaText.toDoubleOrNull()

            // Kiểm tra GPA
            if (gpa == null || gpa < 0.0 || gpa > 10.0) {
                binding.edtGpa.error =
                    "GPA phải từ 0 đến 10"
                return@setOnClickListener
            }

            // =========================
            // TẠO STUDENT MỚI
            // =========================
            val updatedStudent =
                student.copy(
                    fullName = name,
                    className = className,
                    gpa = gpa
                )

            // =========================
            // TRẢ DỮ LIỆU VỀ MAIN
            // =========================
            val resultIntent = Intent()

            resultIntent.putExtra(
                "UPDATED",
                updatedStudent
            )

            setResult(
                Activity.RESULT_OK,
                resultIntent
            )

            finish()
        }

        // =========================
        // NÚT HỦY
        // =========================
        binding.btnCancel.setOnClickListener {

            setResult(
                Activity.RESULT_CANCELED
            )

            finish()
        }
    }
}