package com.tufar.mahallemdeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.getField
import kotlinx.android.synthetic.main.activity_anasayfa.*

class AnasayfaActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter : AnasayfaRecyclerAdapter

    var firmaList = ArrayList<Firma>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anasayfa)

        database = FirebaseFirestore.getInstance()
/*
        if (intent.getBooleanExtra("main",false)){
            secilenVerileriAl()
        }
        else{
            tumVerileriAl()
        }
 */
        tumVerileriAl()
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = AnasayfaRecyclerAdapter(firmaList)

        recyclerView.adapter = recyclerViewAdapter
    }
/*
    fun secilenVerileriAl(){

        val docRef = database.collection("Post").document(intent.getStringExtra("il").toString())
        docRef.get().addOnSuccessListener { doc ->
            firmaList.clear()
            val data = doc
            for (d in data)
            firmaList.add(data)
        }

        database.collection("Post").orderBy("tarih", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null){
                    Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
                else{
                    if (snapshot != null){
                        if (snapshot.isEmpty == false){

                            val documents = snapshot.documents

                            firmaList.clear()
                            for (document in documents){
                                val firmaAdi = document.get("firmaadi") as String
                                val adres = document.get("adres") as String
                                val telefon = document.get("telefon") as String
                                val gorselUrl = document.get("gorselurl") as String

                                val indirilenPost = Firma(firmaAdi,telefon,adres,gorselUrl)
                                firmaList.add(indirilenPost)
                            }
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

    }

 */

    fun tumVerileriAl(){

        database.collection("Post").orderBy("tarih", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null){
                    Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
                else{
                    if (snapshot != null){
                        if (snapshot.isEmpty == false){

                            val documents = snapshot.documents

                            firmaList.clear()
                            for (document in documents){
                                val firmaAdi = document.get("firmaadi") as String
                                val adres = document.get("adres") as String
                                val telefon = document.get("telefon") as String
                                val gorselUrl = document.get("gorselurl") as String

                                val indirilenPost = Firma(firmaAdi,telefon,adres,gorselUrl)
                                firmaList.add(indirilenPost)
                            }
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean {
        //inflater!!.inflate(R.menu.secenekler_menu,menu)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.secenekler_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId

        if(id == R.id.firma_ekle){
            val intent = Intent(this,FirmaEkleActivity::class.java)
            startActivity(intent)
        }
        else if ( id == R.id.mahalle_gor){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}