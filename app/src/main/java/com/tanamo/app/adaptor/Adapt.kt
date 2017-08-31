package com.tanamo.app.adaptor

import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.database.DatabaseReference
import com.tanamo.app.R
import com.tanamo.app.model.Mod
import com.tanamo.app.ui.MainActivity

/**
* Created by ${TANDOH} on ${6/20/2017}.
*/

class Adapt(private val main: MainActivity, modelClass: Class<Mod>, modelLayout: Int, dref: DatabaseReference) : FirebaseListAdapter<Mod>(main, modelClass, modelLayout, dref) {

    override fun populateView(v: View, model: Mod, position: Int) {

        val txt1 = v.findViewById<TextView>(R.id.message)
        val txt2 = v.findViewById<TextView>(R.id.name)
        val txt3 = v.findViewById<TextView>(R.id.time)

        txt1.text = model.message
        txt2.text = model.name
        txt3.text = DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.time)
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val mod = getItem(position)

        if (mod._Id == main.user)
            view = main.layoutInflater.inflate(R.layout.item2, viewGroup, false)
        else
            view = main.layoutInflater.inflate(R.layout.item1, viewGroup, false)

        populateView(view, mod, position)

        return view
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

}
