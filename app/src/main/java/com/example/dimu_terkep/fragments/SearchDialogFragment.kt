package com.example.dimu_terkep.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.opengl.ETC1.isValid
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import android.graphics.Color


class SearchDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val act=this.activity

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Keresés")
            .setView(getContentView())
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(activity?.applicationContext,"this is toast message",Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("Cancel", null)
            .create()
    }

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }


    private fun getContentView(): View {
        var view = LayoutInflater.from(context).inflate(com.example.dimu_terkep.R.layout.fragment_search, null)
        return view
    }
}