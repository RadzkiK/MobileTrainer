package com.engineer.mobiletrainer.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.MobileTrainerApplication
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.adapters.CalendarAdapter
import com.engineer.mobiletrainer.database.entity.TrainingSession
import com.engineer.mobiletrainer.databinding.CalendarDayBinding
import com.engineer.mobiletrainer.databinding.CalendarHeaderBinding
import com.engineer.mobiletrainer.databinding.FragmentCalendarBinding
import com.engineer.mobiletrainer.utils.displayText
import com.engineer.mobiletrainer.utils.getColorCompat
import com.engineer.mobiletrainer.utils.setTextColorRes
import com.engineer.mobiletrainer.viewmodels.PlansViewModel
import com.engineer.mobiletrainer.viewmodels.PlansViewModelFactory
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {

    private var selectedDate: LocalDate? = null

    private val calendarAdapter = CalendarAdapter()
    private var trainingSessions = emptyList<TrainingSession>().toMutableList()
    private var mapOfTrainingSessions = emptyMap<LocalDate, List<TrainingSession>>()

    private val plansViewModel: PlansViewModel by viewModels { PlansViewModelFactory(
        (requireActivity().application as MobileTrainerApplication).plansRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseRepository,
        (requireActivity().application as MobileTrainerApplication).trainingSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSessionRepository,
        (requireActivity().application as MobileTrainerApplication).exerciseSetRepository,
        (requireActivity().application as MobileTrainerApplication).plansExerciseCrossRefRepository) }

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)

        binding.calendarRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = calendarAdapter
        }
        calendarAdapter.notifyDataSetChanged()

        plansViewModel.allTrainingSessions.observe(viewLifecycleOwner, {list ->
            trainingSessions = list
            if(trainingSessions.isNotEmpty()) {
                mapOfTrainingSessions = trainingSessions.groupBy {
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(it.date!!),
                        ZoneId.systemDefault()
                    ).toLocalDate()
                }
            }
        })


        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)
        configureBinders(daysOfWeek)
        binding.calendarCalendarView.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarCalendarView.scrollToMonth(currentMonth)

        binding.calendarCalendarView.monthScrollListener = { month ->
            binding.calendarMonthYearText.text = month.yearMonth.displayText()

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                binding.calendarCalendarView.notifyDateChanged(it)
                updateAdapterForDate(null)
            }
        }

        binding.calendarNextMonthImage.setOnClickListener {
            binding.calendarCalendarView.findFirstVisibleMonth()?.let {
                binding.calendarCalendarView.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }

        binding.calendarPreviousMonthImage.setOnClickListener {
            binding.calendarCalendarView.findFirstVisibleMonth()?.let {
                binding.calendarCalendarView.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }
    }

    private fun updateAdapterForDate(date: LocalDate?) {
        calendarAdapter.trainingSessions.clear()
        calendarAdapter.trainingSessions.addAll(mapOfTrainingSessions[date].orEmpty())
        calendarAdapter.notifyDataSetChanged()
    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            val binding = this@CalendarFragment.binding
                            binding.calendarCalendarView.notifyDateChanged(day.date)
                            oldDate?.let { binding.calendarCalendarView.notifyDateChanged(it) }
                            updateAdapterForDate(day.date)
                        }
                    }
                }
            }
        }
        binding.calendarCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val context = container.binding.root.context
                val textView = container.binding.calendarDayText
                val layout = container.binding.calendarDayLayout
                textView.text = data.date.dayOfMonth.toString()

                val topView = container.binding.calendarDayTop
                val bottomView = container.binding.calendarDayBottom
                topView.background = null
                bottomView.background = null

                if (data.position == DayPosition.MonthDate) {
                    textView.setTextColorRes(R.color.black)
                    layout.setBackgroundResource(if (selectedDate == data.date) R.drawable.calendar_selected_bg else 0)

                    val sessions = mapOfTrainingSessions[data.date]
                    if (sessions != null) {
                        if (sessions.count() == 1) {
                            bottomView.setBackgroundColor(context.getColorCompat(R.color.background_dark))
                        } else {
                            topView.setBackgroundColor(context.getColorCompat(R.color.background_dark))
                            bottomView.setBackgroundColor(context.getColorCompat(R.color.background_dark))
                        }
                        calendarAdapter.onItemClick = {
                            var trainingSession = it
                            val dialog = Dialog(requireActivity())
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setCancelable(true)
                            dialog.setContentView(R.layout.trainingsession_is_completed)
                            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            val completedButton: Button = dialog.findViewById(R.id.trainingsession_is_completed_completedButton)
                            val notCompletedButton: Button = dialog.findViewById(R.id.trainingsession_is_completed_notCompletedButton)

                            completedButton.setOnClickListener {
                                trainingSession.completed = true

                                plansViewModel.updateTrainingSession(trainingSession).invokeOnCompletion {
                                    Log.d("CalendarFragment", "Training session: $trainingSession has been completed")
                                    updateAdapterForDate(data.date)
                                    dialog.dismiss()
                                }
                            }

                            notCompletedButton.setOnClickListener {
                                trainingSession.completed = false

                                plansViewModel.updateTrainingSession(trainingSession).invokeOnCompletion {
                                    Log.d("CalendarFragment", "Training session: $trainingSession has been not completed")
                                    updateAdapterForDate(data.date)
                                    dialog.dismiss()
                                }
                            }

                            dialog.show()
                        }
                    }
                } else {
                    textView.setTextColor(Color.GRAY)
                    layout.background = null
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }

        val typeFace = Typeface.create("sans-serif-light", Typeface.BOLD)
        binding.calendarCalendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = true
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].displayText(uppercase = true)
                                tv.setTextColorRes(R.color.black)
                                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                                tv.typeface = typeFace
                            }
                    }
                }
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
