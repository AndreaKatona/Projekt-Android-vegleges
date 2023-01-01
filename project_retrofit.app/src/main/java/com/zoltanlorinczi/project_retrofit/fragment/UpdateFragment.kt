package com.example.a3tracker.fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentUpdateBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.viewmodel.ProfileViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UpdateProfileViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UpdateProfileViewModelFactory

class UpdateFragment : Fragment() {


    private lateinit var binding : FragmentUpdateBinding
    private val profileViewModel : ProfileViewModel by activityViewModels()
    private lateinit var updateProfile : UpdateProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater)

        val factoryUpdate = UpdateProfileViewModelFactory(ThreeTrackerRepository())
        updateProfile= ViewModelProvider(requireActivity(),factoryUpdate)[UpdateProfileViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValues()

        binding.button.setOnClickListener {
            updateValues()
            Toast.makeText(
                activity, "Updated",
                Toast.LENGTH_LONG
            ).show()

            findNavController().navigate(R.id.settingsFragment2)
        }
    }

    fun setValues()
    {
        binding.updateFirstName.setText(profileViewModel.user.value?.first_name)
        binding.updateLastName.setText(profileViewModel.user.value?.last_name)
        binding.updateAddress.setText(profileViewModel.user.value?.location)
        binding.updateNumber.setText(profileViewModel.user.value?.number)
    }
    fun updateValues()
    {
        val first_name  = binding.updateFirstName.text.toString()
        val last_name = binding.updateLastName.text.toString()
        val address = binding.updateAddress.text.toString()
        val number = binding.updateNumber.text.toString()
        val image = profileViewModel.user.value?.image

        if (image != null) {
            Log.d("onclick: ",image)
        }
        image?.let { updateProfile.update(last_name,first_name,address,number, it) }





    }

}