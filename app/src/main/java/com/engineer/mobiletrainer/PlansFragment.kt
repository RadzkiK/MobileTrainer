package com.engineer.mobiletrainer

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.viewmodels.PlansViewModel

class PlansFragment : Fragment() {

    companion object {
        fun newInstance() = PlansFragment()
    }

    private val viewModel: PlansViewModel by viewModels()
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_plans, container, false)

        addButton = view.findViewById(R.id.plans_add)
        recyclerView = view.findViewById(R.id.plans_recyclerview)
        searchView = view.findViewById(R.id.plans_searchview)


        return view
    }
}