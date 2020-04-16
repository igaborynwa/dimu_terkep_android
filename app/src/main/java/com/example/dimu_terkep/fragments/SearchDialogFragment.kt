package com.example.dimu_terkep.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.graphics.Color
import android.text.Editable
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
    private var cbList=ArrayList<CheckBox>()

    interface SearchListener{
        fun searchParamChanged(param:String, value: String, list:ArrayList<String>)
        fun getList():ArrayList<String>
        fun getSearchParam():String
        fun getSearchValue():String


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
            checkList()
            listener.searchParamChanged(rb.text.toString(),etSearch.text.toString(), listOfSelectedTypes)
        })
        builder.setNegativeButton("Cancel", null)
        return builder.create()

    }
    private fun getContentView(): View {
        viewAct = LayoutInflater.from(context).inflate(R.layout.fragment_search, null)
        rg=viewAct.findViewById(R.id.rg_SearchParam)
        etSearch=viewAct.findViewById(R.id.et_search)
        etSearch.setText(listener.getSearchValue())
        when(listener.getSearchParam()){
            "Intézménynév" -> rg.check(R.id.rb_Intnev)
            "Cím" -> rg.check(R.id.rb_cim)
            "Intézményvezető" -> rg.check(R.id.rb_vezeto)
        }
        makeList()
        return viewAct
    }

    private fun checkList(){
        for(cb in cbList){
            if(cb.isChecked) listOfSelectedTypes.add(cb.text.toString().toLowerCase())
        }
    }

    private fun makeList(){
        cbList.add(viewAct.findViewById(R.id.cb_allmuz))
        cbList.add(viewAct.findViewById(R.id.cb_allkulkoz))
        cbList.add(viewAct.findViewById(R.id.cb_onkmuz))
        cbList.add(viewAct.findViewById(R.id.cb_onkkulkozp))
        cbList.add(viewAct.findViewById(R.id.cb_onkgal))
        cbList.add(viewAct.findViewById(R.id.cb_kergal))
        cbList.add(viewAct.findViewById(R.id.cb_fugkulint))
        cbList.add(viewAct.findViewById(R.id.cb_nonpgal))
        cbList.add(viewAct.findViewById(R.id.cb_kultint))
        cbList.add(viewAct.findViewById(R.id.cb_egyes))
        cbList.add(viewAct.findViewById(R.id.cb_oktint))
        cbList.add(viewAct.findViewById(R.id.cb_ettkocsgal))

        for(cb in cbList){
            if(listener.getList().size==0) cb.isChecked=true
            else cb.isChecked = listener.getList().contains(cb.text.toString().toLowerCase())
        }

    }
}