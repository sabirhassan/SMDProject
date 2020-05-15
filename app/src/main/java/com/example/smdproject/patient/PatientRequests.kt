package com.example.smdproject.patient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.smdproject.R
import com.example.smdproject.model.Doctor
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.appointment_object.view.*

/**
 * A simple [Fragment] subclass.
 */
class PatientRequests : Fragment() {

    lateinit var adapter: FirestoreRecyclerAdapter<Doctor?, DoctorViewHolder?>
    private var firestoreDB: FirebaseFirestore? = null
    lateinit var mFirestoreList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    class DoctorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.Name
        val speciality: TextView = view.doctorSpeciality
        val experience: TextView = view.experianceID
        val likes: TextView = view.likesID
        val fees: TextView = view.feesID
        val TRating: TextView = view.TRating
        val ratingbar: RatingBar =view.ratingBar
        val profilepic: ImageButton =view.ProfilePictureButton
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter.stopListening()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.appointment_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            mFirestoreList = view.findViewById(R.id.RecyclerDoctorList)
            mFirestoreList.layoutManager = LinearLayoutManager(view.context)
            val rootRef = FirebaseFirestore.getInstance()
            val query: Query = rootRef.collection("Doctors")
            val options = FirestoreRecyclerOptions.Builder<Doctor>().setQuery(query, Doctor::class.java).build()

            adapter =
                object : FirestoreRecyclerAdapter<Doctor?, DoctorViewHolder?>(options) {

                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): DoctorViewHolder {
                        val view: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.appointment_object, parent, false)
                        return DoctorViewHolder(view)

                    }


                    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int, doc: Doctor) {
                        holder!!.name.text = doc!!.Name
                        holder.speciality.text = doc.Speciality
                        holder.experience.text = doc.Experience.toString()
                        holder.likes.text = doc.Likes.toString()
                        holder.fees.text = doc.Fees.toString()
                        val x = doc.TRating.toString()
                        val y = "(" + x + ")"
                        holder.TRating.text=y
                        holder.ratingbar.rating=doc.Rating!!.toFloat()
                        Picasso.get().load(doc.ProfilePic).into(holder.profilepic)
                        holder.itemView.setOnClickListener() {

                            val intent =
                                Intent(holder.itemView.context, BookAppointment::class.java)
                            intent.putExtra("Doctor", doc)
                            holder.itemView.context.startActivity(intent)
                        }
                    }
                }

            mFirestoreList?.setHasFixedSize(true)
            mFirestoreList?.adapter=adapter
        }
        return view
    }
}
