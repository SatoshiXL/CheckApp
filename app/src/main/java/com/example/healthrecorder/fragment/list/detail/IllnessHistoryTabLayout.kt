package com.example.healthrecorder.fragment.list.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.list.detail.illnesshistory.DetailIllnessHistory
import com.example.healthrecorder.fragment.list.detail.illnesshistory.PreventionIllnessHistory
import com.example.healthrecorder.fragment.list.detail.illnesshistory.SymptomsIllnessHistory
import com.example.healthrecorder.fragment.list.detail.viewmodel.InfoViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_clinic_visit_tab_layout.view.bkbutton400
import kotlinx.android.synthetic.main.fragment_illness_history_tab_layout.view.*

class IllnessHistoryTabLayout : Fragment() {

    lateinit var pager2: ViewPager2
    private val args: ClinicVisitTabLayoutArgs by navArgs()
    val infoVm by activityViewModels<InfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_illness_history_tab_layout, container, false)
        view.apply {


            val listOfFragment = arrayListOf(
                DetailIllnessHistory(),
                SymptomsIllnessHistory(),
                PreventionIllnessHistory()
            )
            val adapter = ViewPagerAdapter(
                listOfFragment,
                requireActivity().supportFragmentManager,
                requireActivity(),
                lifecycle
            )
            viewPager2.adapter = adapter
            infoVm.dataset.value = args.id
            val tabsName = listOf(
                "Illness Detail", "Symptoms", "Prevention"
            )

            TabLayoutMediator(tabs2, viewPager2) { tab, p ->
                viewPager2.setCurrentItem(tab.position, true)
                tab.text = tabsName[p]
            }.attach()

            view.bkbutton400.setOnClickListener { findNavController().navigate(R.id.action_illnessHistoryTabLayout_to_listIllnessHistory) }


            view.IllnessHistoryFloatingAction.setOnClickListener {
                when (viewPager2.currentItem) {
                    0 -> {
                        val navigate =
                            IllnessHistoryTabLayoutDirections.actionIllnessHistoryTabLayoutToEditIllnessHistory(
                                args.id
                            )
                        findNavController().navigate(navigate)
                    }
                    1 -> {
                        val navigate =
                            IllnessHistoryTabLayoutDirections.actionIllnessHistoryTabLayoutToAddSymptomsIllnessHistory(
                                args.id
                            )
                        findNavController().navigate(navigate)
                    }
                    2 -> {
                        val navigate =
                            IllnessHistoryTabLayoutDirections.actionIllnessHistoryTabLayoutToAddPreventionIllnessHistory(
                                args.id
                            )
                        findNavController().navigate(navigate)
                    }


                }
            }
        }
        return view


    }
}