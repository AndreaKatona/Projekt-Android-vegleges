package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentAddTaskBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.viewmodel.CreateTaskViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.CreateTaskViewModelFactory

class AddTaskFragment: Fragment()  {



    private lateinit var binding : FragmentAddTaskBinding
    private lateinit var createTaskViewModel : CreateTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddTaskBinding.inflate(inflater)

        val factory = CreateTaskViewModelFactory(ThreeTrackerRepository())
        createTaskViewModel = ViewModelProvider(requireActivity(),factory)[CreateTaskViewModel::class.java]

        return  binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button2.setOnClickListener{
            setValues()

            Toast.makeText(activity, "Task Created", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.listFragment)



        }

    }

    fun setValues()
    {
        val title = binding.addTitle.text.toString()
        val description = binding.addDescription.text.toString()
        val userId = binding.addAssignedId.text.toString()
        val priority = binding.addPriority.text.toString()
        val deadline = binding.addDeadline.text.toString()
        val department = binding.addDepartment.text.toString()

        createTaskViewModel.create(title,description,userId.toInt(),priority.toInt(),deadline.toLong(),department.toInt(),0)
    }
}