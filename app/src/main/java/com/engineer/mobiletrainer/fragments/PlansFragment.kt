package com.engineer.mobiletrainer.fragments

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.adapters.PlansAdapter
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory
import java.util.Locale

class PlansFragment : Fragment() {

    companion object {
        fun newInstance() = PlansFragment()
    }

    private val plansViewModel: PlansViewModel by viewModels { PlansViewModelFactory(
        (requireActivity().application as MobileTrainerApplication).plansRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseRepository,
        (requireActivity().application as MobileTrainerApplication).trainingSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSetRepository) }
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var plans: MutableList<Plans> = emptyList<Plans>().toMutableList()
    private lateinit var plansAdapter: PlansAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_plans, container, false)

        addButton = view.findViewById(R.id.plans_add)
        recyclerView = view.findViewById(R.id.plans_recyclerview)
        searchView = view.findViewById(R.id.plans_searchview)
        addButton.setOnClickListener(View.OnClickListener {view ->
            //plans.add(Plans("Nastepny plan"))
            //plansAdapter = PlansAdapter(plans)
            //recyclerView.adapter = plansAdapter
            view.findNavController().navigate(R.id.action_plansFragment_to_planDetails)
            println(plans)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)

        plansViewModel.allPlans.observe(requireActivity(), Observer { list ->
            plans = list

            println(plans)
            recyclerView.layoutManager = layoutManager

            plansAdapter = PlansAdapter(plans)

            recyclerView.adapter = plansAdapter

            plansAdapter.onItemClick = {
                val bundle = bundleOf("plan" to it)
                view.findNavController().navigate(R.id.action_plansFragment_to_planDetails, bundle)
            }
        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

            private fun filterList(query: String?) {
                if (query != null) {
                    val filteredList = emptyList<Plans>().toMutableList()
                    for (i in plans) {
                        if(i.name?.toLowerCase(Locale.ROOT)?.contains(query) == true) {
                            filteredList.add(i)
                        }
                    }
                    if(filteredList.isEmpty()) {
                        Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                        plansAdapter.setFilteredList(filteredList)
                    }else {
                        plansAdapter.setFilteredList(filteredList)
                    }
                }
            }

        })


    }
}