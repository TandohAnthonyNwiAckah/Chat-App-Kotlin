package com.tanamo.app.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseListAdapter
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tanamo.app.R
import com.tanamo.app.adaptor.Adapt
import com.tanamo.app.model.Mod
import kotlinx.android.synthetic.main.activity_main.*




//This is the MainActivity class.


class MainActivity : AppCompatActivity() {
    private var adapter: FirebaseListAdapter<Mod>? = null
    private val RC = 1000
    public var user = ""
    private val IMAGE_GALLERY = 1
    private val REQUEST_INVITE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {

        setSupportActionBar(toolbar)


        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .build(), RC)
        } else
            myInboxes()


        imag.setOnClickListener {
            if (messa.text.toString().trim { it <= ' ' } == "") {
                Toast.makeText(this@MainActivity, R.string.est, Toast.LENGTH_SHORT).show()
            } else {
                FirebaseDatabase.getInstance()
                        .reference
                        .push()
                        .setValue(Mod(messa.text.toString(),
                                FirebaseAuth.getInstance().currentUser!!.displayName!!,
                                FirebaseAuth.getInstance().currentUser!!.uid)
                        )
                messa.setText("")
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            R.id.sign_out -> signOut()

            R.id.invite -> invitation()

            R.id.gallery -> gallery()

        }
        return super.onOptionsItemSelected(item)

    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener {
                    Toast.makeText(this@MainActivity, "Log out Success!", Toast.LENGTH_SHORT).show()
                    finish()
                }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show()
                myInboxes()
            } else {
                Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show()

                finish()
            }
        }
    }

    private fun myInboxes() {
        user = FirebaseAuth.getInstance().currentUser!!.uid
        adapter = Adapt(this, Mod::class.java, R.layout.item1, FirebaseDatabase.getInstance().reference)
        lise!!.adapter = adapter
    }

    private fun invitation() {
        val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build()
        startActivity(intent)
    }

    private fun gallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_GALLERY)
    }


}
