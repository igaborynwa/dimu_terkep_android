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
    private var listOfSelectedTypes= ArrayList<String>()
    interface SearchListener{
        fun searchParamChanged(param:String, value: String, list:ArrayList<String>)

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
            makeList()
            listener.searchParamChanged(rb.text.toString(),etSearch.text.toString(), listOfSelectedTypes)
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

    private fun makeList(){
        if(viewAct.findViewById<CheckBox>(R.id.cb_allmuz).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_allmuz).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_allkulkoz).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_allkulkoz).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_onkmuz).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_onkmuz).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_onkkulkozp).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_onkkulkozp).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_onkgal).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_onkgal).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_kergal).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_kergal).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_fugkulint).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_fugkulint).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_nonpgal).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_nonpgal).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_kultint).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_kultint).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_egyes).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_egyes).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_oktint).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_oktint).text.toString().toLowerCase())
        if(viewAct.findViewById<CheckBox>(R.id.cb_ettkocsgal).isChecked) listOfSelectedTypes.add(viewAct.findViewById<CheckBox>(R.id.cb_ettkocsgal).text.toString().toLowerCase())

    }
}