package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.adapter.TasksListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.DepartmentResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.*

/**
 * Author:  Zoltan Lorinczi
 * Date:    12/2/2021
 */
class TasksListFragment : Fragment(R.layout.fragment_tasks_list), TasksListAdapter.OnItemClickListener,
        TasksListAdapter.OnItemLongClickListener {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksListAdapter
    private lateinit var getuserViewModel : GetUsersViewModel
    private lateinit var departmentsViewModel : DepartmentsViewModel
    private lateinit var users : ArrayList<UserResponse>
    private lateinit var departmentsName:ArrayList<DepartmentResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val factory1 = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(this, factory1)[TasksViewModel::class.java]

        val factory2 = GetUsersViewModelFactory(ThreeTrackerRepository())
        getuserViewModel = ViewModelProvider(this,factory2)[GetUsersViewModel::class.java]

        val factory3 = DepartmentsViewModelFactory(ThreeTrackerRepository())
        departmentsViewModel  = ViewModelProvider(this,factory3)[DepartmentsViewModel::class.java]


    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tasks_list, container, false)
        val addButton : Button = view.findViewById(R.id.addTask)


        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerView()
        tasksViewModel.products.observe(viewLifecycleOwner) {
            Log.d(TAG, "Tasks list = $it")
            adapter.setData(tasksViewModel.products.value as ArrayList<TaskResponse>)
            adapter.notifyDataSetChanged()
        }

        addButton.setOnClickListener{
            findNavController().navigate(R.id.addTaskFragment)
        }


        return view
    }

    private fun setupRecyclerView() {
        adapter = TasksListAdapter(ArrayList(), requireContext(), this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                )
        )
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {

        users = getuserViewModel.getUsers.value as ArrayList<UserResponse>
        departmentsName = departmentsViewModel.departments.value as ArrayList<DepartmentResponse>
        val clickedItem : TaskResponse ?= tasksViewModel.products.value?.get(position)
        val department_name = getDepartmentById(clickedItem?.departmentID, departmentsName  )
        val user_name = getNameById(clickedItem?.assignedToUserID,users)
        val createdBy  = getNameById(clickedItem?.createdByUserID,users)


        val  bundle = bundleOf(
            "title" to clickedItem?.title,
            "description" to clickedItem?.description,
            "createdBy" to createdBy,
            "createdTime" to clickedItem?.createdTime.toString(),
            "assigned_to_user_id" to user_name,
            "priority" to clickedItem?.priority.toString(),
            "deadline" to clickedItem?.deadline.toString(),
            "departmentId" to department_name,
            "status" to clickedItem?.status.toString(),
            "progress" to clickedItem?.progress.toString())

        findNavController().navigate(R.id.detailsFragment,bundle)
    }
    fun getNameById(Id : Int?, listUser : ArrayList<UserResponse>) : String
    {
        val user = listUser.find { it.id == Id}
        //Log.d("user", user.toString())
        if (user != null) {
            return user.last_name + " " +user.first_name
        }
        return "Anonymus"
    }
    fun getDepartmentById(Id : Int?,departments:ArrayList<DepartmentResponse>) : String {
        val value = departments.find { it.Id == Id }
        //Log.d("user", user.toString())
        if (value != null) {
            return value.departmentName
        }
        return "Anonymus"
    }
    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }
}