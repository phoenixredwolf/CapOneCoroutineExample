package com.example.caponecoroutineexample.ui.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.caponecoroutineexample.databinding.CatFragmentBinding
import com.example.caponecoroutineexample.ui.adapter.DataAdapter
import com.example.caponecoroutineexample.ui.viewmodel.MainViewModel
import com.example.caponecoroutineexample.utility.LOG_TAG
import com.example.caponecoroutineexample.utility.Resource

class CatFragment : Fragment() {

    private var _binding: CatFragmentBinding? = null
    val binding: CatFragmentBinding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var shareURL: String
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted: Boolean -> if (isGranted) {
            share()
        } else {
            binding.btnShare.isEnabled = false
            Toast.makeText(context,"Permissions required to use this feature", Toast.LENGTH_LONG).show()
        }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
            btnShare.isEnabled = false
            viewModel.cats.observe(viewLifecycleOwner){
                progressBar.isVisible = it is Resource.Loading
                when (it) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                        Log.i(LOG_TAG, "Resource Loading")
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                        Log.i(LOG_TAG, "Resource Error")
                    }
                    is Resource.Success -> {
                        catImageRv.adapter = DataAdapter(it.data) { url ->
                            requireContext().toast(url)
                        }
                        shareURL = it.data[0]
                        btnShare.isEnabled = true
                    }
                }
                btnShare.setOnClickListener {
                    when {
                        ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_CONTACTS)
                                == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(
                                    requireContext(),
                                    Manifest.permission.SEND_SMS)
                                == PackageManager.PERMISSION_GRANTED -> {
                            share()
                        }
                        shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {

                        }
                        else -> {
                            requestPermissionLauncher.launch(
                                Manifest.permission.READ_CONTACTS
                            )
                            requestPermissionLauncher.launch(
                                Manifest.permission.SEND_SMS
                            )
                        }

                    }
                }
            }

        }
    }

    private fun Context.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun share(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Check out this cute cat I found in my CattyCat app: $shareURL"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "CattyCat meme")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

}