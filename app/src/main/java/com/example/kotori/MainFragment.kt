package com.example.kotori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotori.databinding.FragmentMainBinding
import com.nanaten.customdialog.CustomDialog

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        // バトル準備画面に遷移
        binding.battleButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDeckFragment()
            findNavController().navigate(action)
        }

        // バトル準備画面に遷移
        binding.galleryButton.setOnClickListener {
            CustomDialog.Builder(this)
                .setTitle("カスタムタイトル")
                .setMessage("カスタムメッセージ")
                .setPositiveButton("はい") {  }
                .build()
                .show(childFragmentManager, CustomDialog::class.simpleName)
        }
        return view
    }

    // 終わったら破棄を忘れない
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
