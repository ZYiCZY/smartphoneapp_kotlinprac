package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentLedgerListBinding

class LedgerListFragment : Fragment() {

    private var _binding: FragmentLedgerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLedgerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLunch.setOnClickListener {
            openDetail("午餐", 120)
        }

        binding.btnMetro.setOnClickListener {
            openDetail("捷運", 35)
        }

        binding.btnBook.setOnClickListener {
            openDetail("書籍", 450)
        }
    }

    private fun openDetail(title: String, amount: Int) {
        val detailFragment = LedgerDetailFragment.newInstance(title, amount)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, detailFragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}