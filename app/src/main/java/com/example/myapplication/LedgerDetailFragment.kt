package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentLedgerDetailBinding

class LedgerDetailFragment : Fragment() {

    private var _binding: FragmentLedgerDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLedgerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(ARG_TITLE) ?: ""
        val amount = arguments?.getInt(ARG_AMOUNT, 0) ?: 0

        binding.tvTitle.text = title
        binding.tvAmount.text = "金額：${amount} 元"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_AMOUNT = "arg_amount"

        fun newInstance(title: String, amount: Int): LedgerDetailFragment {
            val fragment = LedgerDetailFragment()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putInt(ARG_AMOUNT, amount)
            }
            fragment.arguments = args
            return fragment
        }
    }
}