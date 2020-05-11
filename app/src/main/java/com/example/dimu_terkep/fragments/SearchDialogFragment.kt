package com.example.dimu_terkep.fragments

import android.app.Activity
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
import com.example.dimu_terkep.model.IntezmenyTipus
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchDialogFragment : DialogFragment() {

    private lateinit var listener: SearchListener

    private lateinit var etSearchName:EditText
    private lateinit var etSearchAddr:EditText
    private lateinit var etSearchHead:EditText
    private lateinit var etSearchEvent:EditText
    private lateinit var viewAct: View
    private var listOfSelectedTypes= ArrayList<IntezmenyTipus>()
    private var cbList=ArrayList<CheckBox>()
    private lateinit var a: Activity


    interface SearchListener{
        fun searchParamChanged(name:String,addr: String,  head: String,event:String, list:ArrayList<IntezmenyTipus>)
        fun getList():ArrayList<IntezmenyTipus>
        fun getSearchName():String
        fun getSearchAddr():String
        fun getSearchHead():String
        fun getSearchEventParam(): String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val act=this.activity
        a=this.activity as Activity
        if(act is SearchListener){
            listener=act
        }
        else{
            throw RuntimeException("Activity must implement SearchListener")
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
        builder.setPositiveButton("Keresés", DialogInterface.OnClickListener { dialogInterface, i ->
            checkList()
            listener.searchParamChanged(etSearchName.text.toString(),etSearchAddr.text.toString(),etSearchHead.text.toString(),etSearchEvent.text.toString(), listOfSelectedTypes)
        })
        builder.setNegativeButton("Mégse", null)
        return builder.create()

    }
    private fun getContentView(): View {
        viewAct = LayoutInflater.from(context).inflate(R.layout.fragment_search, null)

        etSearchName=viewAct.findViewById(R.id.et_searchName)
        etSearchName.setText(listener.getSearchName())
        etSearchAddr=viewAct.findViewById(R.id.et_searchAddr)
        etSearchAddr.setText(listener.getSearchAddr())
        etSearchHead=viewAct.findViewById(R.id.et_searchHead)
        etSearchHead.setText(listener.getSearchHead())
        etSearchEvent=viewAct.findViewById(R.id.et_searchEvent)
        etSearchEvent.setText(listener.getSearchEventParam())


        makeList()
        return viewAct
    }

    private fun checkList(){
        for(cb in cbList){
            if(cb.isChecked) listOfSelectedTypes.add(IntezmenyTipus.desc(cb.text.toString(), a))
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
            if(listener.getList().size==0) {
                cb.isChecked=false
            }
            else cb.isChecked = listener.getList().contains(IntezmenyTipus.desc(cb.text.toString(),a))
        }

    }
}