package com.example.dimu_terkep.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.graphics.Color
import com.example.dimu_terkep.R
import android.widget.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchDialogFragment : DialogFragment() {

    private lateinit var listener: SearchListener
    private lateinit var rg:RadioGroup

    private lateinit var etSearch:EditText
    private lateinit var viewAct: View

    interface SearchListener{
        fun searchParamChanged(param:String, value: String)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val act=this.activity
        if(act is SearchListener){
            listener=act
        }
        else{
            throw RuntimeException("Activity must implement Searchlistener")
        }

    }

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!)

        builder.setView(getContentView())
        builder.setTitle("Keresés")
        builder.setPositiveButton("Keresés", DialogInterface.OnClickListener { dialogInterface, i ->
            val rb = viewAct.findViewById(rg.checkedRadioButtonId) as RadioButton
            listener.searchParamChanged(rb.text.toString(),etSearch.text.toString())
        })
        builder.setNegativeButton("Cancel", null)
        return builder.create()

    }
    private fun getContentView(): View {
        viewAct = LayoutInflater.from(context).inflate(R.layout.fragment_search, null)
        rg=viewAct.findViewById(R.id.rg_SearchParam)
        etSearch=viewAct.findViewById(R.id.et_search)
        return viewAct
    }
}