package com.example.studentprofilemanager

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.studentprofilemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Thông tin sinh viên
    private var student = Student(
        mssv = "241505312217",
        fullName = "Đoàn Thị Mai Hiền",
        className = "24T1",
        email = "241505312217@sv.ute.udn.vn",
        gpa = 8.7
    )

    // Nhận dữ liệu trả về từ EditProfileActivity
    private val editLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                @Suppress("DEPRECATION")
                val updatedStudent =
                    result.data?.getSerializableExtra("UPDATED") as? Student

                if (updatedStudent != null) {
                    student = updatedStudent
                    bindData()
                }
            }
        }

    // Chọn ảnh từ thư viện
    private val galleryLauncher =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            if (uri != null) {
                binding.imgAvatar.setImageURI(uri)
            }
        }

    // Xin quyền Camera
    private val cameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                Toast.makeText(
                    this,
                    "Đã được cấp quyền Camera",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Bạn chưa cấp quyền Camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hiển thị thông tin ban đầu
        bindData()

        // =========================
        // CHỈNH SỬA HỒ SƠ
        // =========================
        binding.btnEditProfile.setOnClickListener {

            val intent = Intent(
                this,
                EditProfileActivity::class.java
            )

            intent.putExtra("STUDENT", student)

            editLauncher.launch(intent)
        }

        // =========================
        // ĐỔI ẢNH ĐẠI DIỆN
        // =========================
        binding.btnChangeAvatar.setOnClickListener {

            galleryLauncher.launch("image/*")
        }

        // =========================
        // GỌI ĐIỆN
        // =========================
        binding.btnCallHotline.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:0905123456")
            )

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "Không tìm thấy ứng dụng gọi điện",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // =========================
        // XIN QUYỀN CAMERA
        // =========================
        binding.btnRequestCamera.setOnClickListener {

            cameraLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    // Hiển thị dữ liệu sinh viên lên giao diện
    private fun bindData() {

        binding.tvName.text = student.fullName

        binding.tvDetails.text =
            "MSSV: ${student.mssv}\n" +
                    "Lớp: ${student.className}\n" +
                    "Email: ${student.email}"

        binding.tvGpaBadge.text =
            "GPA: ${student.gpa}"
    }
}