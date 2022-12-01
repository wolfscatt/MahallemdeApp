package com.tufar.mahallemdeapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.getField
import com.tufar.mahallemdeapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore
    private lateinit var binding : ActivityMainBinding

    private var ulkeList = arrayOf<String>("Türkiye")
    private var ilList = listOf<String>("Adana",
        "Adıyaman",
        "Afyonkarahisar",
        "Ağrı",
        "Amasya",
        "Ankara",
        "Antalya",
        "Artvin",
        "Aydın",
        "Balıkesir",
        "Bilecik",
        "Bingöl",
        "Bitlis",
        "Bolu",
        "Burdur",
        "Bursa",
        "Çanakkale",
        "Çankırı",
        "Çorum",
        "Denizli",
        "Diyarbakir",
        "Edirne",
        "Elazığ",
        "Erzincan",
        "Erzurum",
        "Eskişehir",
        "Gaziantep",
        "Giresun",
        "Gümüşhane",
        "Hakkari",
        "Hatay",
        "Isparta",
        "Mersin",
        "İstanbul",
        "İzmir",
        "Kars",
        "Kastamonu",
        "Kayseri",
        "Kırklareli",
        "Kırşehir",
        "Kocaeli",
        "Konya",
        "Kütahya",
        "Malatya",
        "Manisa",
        "Kahramanmaraş",
        "Mardin",
        "Muğla",
        "Muş",
        "Nevşehir",
        "Niğde",
        "Ordu",
        "Rize",
        "Sakarya",
        "Samsun",
        "Siirt",
        "Sinop",
        "Sivas",
        "Tekirdağ",
        "Tokat",
        "Trabzon",
        "Tunceli",
        "Şanlıurfa",
        "Uşak",
        "Van",
        "Yozgat",
        "Zonguldak",
        "Aksaray",
        "Bayburt",
        "Karaman",
        "Kırıkkale",
        "Batman",
        "Şırnak",
        "Bartın",
        "Ardahan",
        "Iğdır",
        "Yalova",
        "Karabük",
        "Kilis",
        "Osmaniye",
        "Düzce")

    var secilenIl = String.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        database = FirebaseFirestore.getInstance()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (binding.ulkeSpinner != null){
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item,ulkeList)
            binding.ulkeSpinner.adapter = adapter
            binding.ulkeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }

        if (binding.ilSpinner != null){
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,ilList)
            binding.ilSpinner.adapter = adapter
            binding.ilSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    secilenIl = ilList[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    fun mahalleyeGotur(view: View){

        val intent = Intent(this,AnasayfaActivity::class.java)
        /*
        val ilce = ilceEditText.text.toString()
        val mahalle = mahalleEditText.text.toString()

        val mainActivity = true

        intent.putExtra("main",mainActivity)
        intent.putExtra("il",secilenIl)
        intent.putExtra("ilce",ilce)
        intent.putExtra("mahalle",mahalle)

         */
        startActivity(intent)
/*
        val docRef = database.collection("Post").document(secilenIl)
        docRef.get().addOnSuccessListener { doc ->
            if (doc != null){

                val dIlce = doc.getField<String>("ilce")
                val dMahalle = doc.getField<String>("mahalle")

                if (ilce == dIlce && mahalle == dMahalle ){
                    val intent = Intent(this,AnasayfaActivity::class.java)
                    startActivity(intent)
                }
            }
        }

 */
    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean {
        //inflater!!.inflate(R.menu.secenekler_menu,menu)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(com.tufar.mahallemdeapp.R.menu.secenekler_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId

        if(id == com.tufar.mahallemdeapp.R.id.firma_ekle){
            val intent = Intent(this,FirmaEkleActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == com.tufar.mahallemdeapp.R.id.anasayfa_git){
            val intent = Intent(applicationContext,AnasayfaActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}