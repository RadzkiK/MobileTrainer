package com.engineer.mobiletrainer

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.databinding.FragmentPlansBinding
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory

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
            plans.add(Plans("Nastepny plan"))
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
        })


    }
}