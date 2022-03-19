package com.example.bankodemia.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankodemia.R
import com.example.bankodemia.UI.adapters.LadaAdapter
import com.example.bankodemia.UI.view.OnClikListener
import com.example.bankodemia.core.types.FieldTypeEnum
import com.example.bankodemia.core.activateButton
import com.example.bankodemia.core.utils.ParseJson
import com.example.bankodemia.core.validateField
import com.example.bankodemia.data.model.Lada
import com.example.bankodemia.databinding.FragmentTelephoneBinding
import java.io.IOException
import java.io.InputStream

class TelephoneFragment : Fragment(), OnClikListener, Fields {
    private var _binding: FragmentTelephoneBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mLinearLayoutManager: RecyclerView.LayoutManager
    private lateinit var mLadaAdapter: LadaAdapter

    private var re: List<Lada>? = null
    private var parse: ParseJson? = null
    private var isActiveRvLada: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTelephoneBinding.inflate(inflater, container, false)
        initializeComponents()
        validationFields()
        setRecyclerViewLadas()
        return mBinding.root
    }

    private fun initializeComponents() {
        with(mBinding) {
            telephoneBtnBackToData.setOnClickListener {
                findNavController().navigate(R.id.action_telephoneFragment_to_dataFragment)
            }

            telephoneBtnContinueProcess.setOnClickListener {
                findNavController().navigate(R.id.action_telephoneFragment_to_identityFragment)
            }

            telephoneBtnLadas.setOnClickListener {
                if (!isActiveRvLada){
                    isActiveRvLada = true
                    telephoneRvLada.visibility = View.VISIBLE
                }else{
                    isActiveRvLada = false
                    telephoneRvLada.visibility = View.GONE
                }
            }
        }

        val `is`: InputStream = resources.openRawResource(R.raw.ladas_internacionales)
        try {
            parse = ParseJson()
            re = parse!!.readJsonStream(`is`)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerViewLadas() {
        if (re != null) {

            mLadaAdapter = LadaAdapter(re!!,this)
            mLinearLayoutManager = LinearLayoutManager(activity?.applicationContext)

            mBinding.telephoneRvLada.apply {
                setHasFixedSize(true)
                adapter = mLadaAdapter
                layoutManager = mLinearLayoutManager
            }
        }
    }


    override fun validationFields() {
        var telephone: Boolean
        with(mBinding) {
            telephoneTietTelephone.addTextChangedListener {
                telephone = validateField(
                    fragment = this@TelephoneFragment,
                    typeEnum = FieldTypeEnum.PHONE,
                    telephoneTilTelephone
                )
                activateButton(
                    fragment = this@TelephoneFragment,
                    telephoneBtnContinueProcess,
                    telephone
                )
            }
        }
    }

    override fun onClick(lada: Lada, position: Int) {
        with(mBinding){
            telephoneBtnLadas.text = lada.lada
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}