package com.example.healthrecorder.fragment.list.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.list.detail.clinicvisit.DetailClinicVisit
import com.example.healthrecorder.fragment.list.detail.clinicvisit.DoctorsInformation
import com.example.healthrecorder.fragment.list.detail.clinicvisit.PrescribeMedicine
import com.example.healthrecorder.fragment.list.detail.clinicvisit.SymptomsClinicVisit
import com.example.healthrecorder.fragment.list.detail.viewmodel.InfoViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_clinic_visit_tab_layout.view.*


open class ClinicVisitTabLayout : Fragment() {

    lateinit var pager2: ViewPager2
    private val args: ClinicVisitTabLayoutArgs by navArgs()
    val infoVm by activityViewModels<InfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clinic_visit_tab_layout, container, false)
        view.apply {

            Log.d("message", "${args.id}")


            val listOfFragment = arrayListOf(
                DetailClinicVisit(),
                SymptomsClinicVisit(),
                PrescribeMedicine(), DoctorsInformation()
            )
            val adapter = ViewPagerAdapter(
                listOfFragment,
                requireActivity().supportFragmentManager,
                requireActivity(),
                lifecycle
            )
            viewPager.adapter = adapter
            infoVm.dataset.value = args.id
            val tabsName = listOf(
                "Details", "Symptoms", "Prescribe Medicine", "Doctors Information"
            )

            TabLayoutMediator(tabs, viewPager) { tab, p ->
                viewPager.setCurrentItem(tab.position, true)
                tab.text = tabsName[p]
            }.attach()

            view.bkbutton400.setOnClickListener { findNavController().navigate(R.id.action_clinicVisitTabLayout_to_listClinicVisit) }


            view.ClinicVisitFloatingAction.setOnClickListener {
                when (viewPager.currentItem) {
                    0 -> {
                        val navigate =
                            ClinicVisitTabLayoutDirections.actionClinicVisitTabLayoutToEditClinicVisit(
                                args.id
                            )
                        findNavController().navigate(navigate)
                    }
                    1 -> {
                        val navigate =
                            ClinicVisitTabLayoutDirections.actionClinicVisitTabLayoutToAddSymptomsClinicVisit(
                                args.id
                            )
                        findNavController().navigate(navigate)
                    }
                    2 -> {
                        val navigate =
                            ClinicVisitTabLayoutDirections.actionClinicVisitTabLayoutToAddPrescribeMedicine(
                                args.id
                            )
                        findNavController().navigate(navigate)
                    }
                    3 -> {
                        val navigate =
                            ClinicVisitTabLayoutDirections.actionClinicVisitTabLayoutToAddDoctorInformation(
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



