package com.tufar.mahallemdeapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tufar.mahallemdeapp.databinding.ActivityFirmaEkleBinding
import kotlinx.android.synthetic.main.activity_firma_ekle.*
import java.io.IOException
import java.util.*

class FirmaEkleActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var database : FirebaseFirestore
    private lateinit var binding: ActivityFirmaEkleBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    var secilenGorsel : Uri? = null
    var secilenBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_firma_ekle)

        binding = ActivityFirmaEkleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        registerLauncher()

        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
    }
    fun firmaEkle(view: View){

        val uuid = UUID.randomUUID()
        val gorselIsmi = "${uuid}.jpg"
        val reference = storage.reference
        val gorselReference = reference.child("images").child(gorselIsmi)

        if (secilenGorsel != null){
            gorselReference.putFile(secilenGorsel!!).addOnSuccessListener { taskSnapshot ->
                // Depoya yüklenen görselin linki alındı ve o link ile veritabanına kayıt edilecek.
                val yuklenenGorselReference = FirebaseStorage.getInstance().reference.child("images").child(gorselIsmi)
                yuklenenGorselReference.downloadUrl.addOnSuccessListener { uri ->
                    // Veritabanı işlemleri
                    val downloadUrl = uri.toString()
                    val firmaAdi = editTextFirmaAdi.text.toString()
                    val il =editTextIl.text.toString()
                    val ilce = editTextIlce.text.toString()
                    val mahalle = editTextMahalle.text.toString()
                    val telefon = editTextTelefon.text.toString()
                    val adres = editTextAdres.text.toString()
                    val tarih = com.google.firebase.Timestamp.now()

                    val firmaHashMap = hashMapOf<String,Any>()

                    if (firmaAdi == null && il == null && ilce == null && mahalle == null && telefon == null && adres == null){
                        Toast.makeText(this,"Hiçbir alan boş olamaz!!! ",Toast.LENGTH_LONG).show()
                    }
                    else{
                        firmaHashMap.put("gorselurl",downloadUrl)
                        firmaHashMap.put("firmaadi",firmaAdi)
                        firmaHashMap.put("il",il)
                        firmaHashMap.put("ilce",ilce)
                        firmaHashMap.put("mahalle",mahalle)
                        firmaHashMap.put("telefon",telefon)
                        firmaHashMap.put("adres",adres)
                        firmaHashMap.put("tarih",tarih)

                        val post = database.collection("Post")

                        database.collection("Post").add(firmaHashMap).addOnCompleteListener{ task ->
                        //post.document(il).set(firmaHashMap).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                finish()
                            }
                        }.addOnFailureListener { exception ->
                            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                        }
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
        val intent = Intent(this,AnasayfaActivity::class.java)
        startActivity(intent)
    }
    fun gorselSec(view: View){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                    View.OnClickListener {
                        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()
            } else {
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(galeriIntent)

        }
        imageViewFirmaEkle.visibility = View.VISIBLE
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    secilenGorsel = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(
                                this@FirmaEkleActivity.contentResolver,
                                secilenGorsel!!
                            )
                            secilenBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageViewFirmaEkle.setImageBitmap(secilenBitmap)
                        } else {
                            secilenBitmap = MediaStore.Images.Media.getBitmap(
                                this@FirmaEkleActivity.contentResolver,
                                secilenGorsel
                            )
                            binding.imageViewFirmaEkle.setImageBitmap(secilenBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                //permission granted
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(galeriIntent)
            } else {
                //permission denied
                Toast.makeText(this@FirmaEkleActivity, "Permisson needed!", Toast.LENGTH_LONG).show()
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
        if (item.itemId == R.id.mahalle_gor){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == R.id.anasayfa_git){
            val intent = Intent(applicationContext,AnasayfaActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}