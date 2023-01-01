package com.zoltanlorinczi.project_retrofit.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentSettingsBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.viewmodel.ProfileViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.ProfileViewModelFactory

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var profileViewModel : ProfileViewModel
    private lateinit var binding : FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)

        val factory = ProfileViewModelFactory(ThreeTrackerRepository())
        profileViewModel = ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]

        val view = binding.root

        profileViewModel.user.observe(this.viewLifecycleOwner) {

            profileViewModel.getUsers()
            binding.personName.setText(profileViewModel.user.value?.last_name+" "+profileViewModel.user.value?.first_name)
            binding.phoneNum.setText(profileViewModel.user.value?.number)
            binding.address.setText(profileViewModel.user.value?.location)
            binding.emailAddress.setText(profileViewModel.user.value?.email)

            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)

            context?.let {
                Glide.with(it)
                    .load(profileViewModel.user.value?.image)
                    .apply(options)
                    .override(100, 100)
                    .into(binding.profileImageView)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonUpdate.setOnClickListener{

            findNavController().navigate(R.id.updateFragment)

        }
        binding.buttonLogout.setOnClickListener{
            val preferences = requireActivity().getSharedPreferences("ThreeTrackerSharedPreferences", Context.MODE_PRIVATE)
            val editor = preferences.edit()

            editor.remove("SHARED_PREFERENCES_KEY_TOKEN")
            editor.apply()

            findNavController().navigate(R.id.loginFragment)

        }
    }






}