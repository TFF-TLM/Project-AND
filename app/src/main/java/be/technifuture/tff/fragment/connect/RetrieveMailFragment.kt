package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentRetrieveMailBinding
import be.technifuture.tff.fragment.connect.viewController.CreateUserController
import be.technifuture.tff.service.AlertDialogCustom

class RetrieveMailFragment : Fragment() {

    private lateinit var binding: FragmentRetrieveMailBinding
    private lateinit var viewController: CreateUserController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRetrieveMailBinding.inflate(layoutInflater)

        binding.header.title.text = getString(R.string.retrieve_title)

        binding.buttonSendMail.setOnClickListener {
            AlertDialogCustom(requireContext()).getAlert(AlertDialogCustom.ErrorValidation.RETRIEVE_SEND)
            val direction = RetrieveMailFragmentDirections.actionRetrieveMailFragmentToLoginFragment()
            findNavController().navigate(direction)
        }
        return binding.root
    }
}