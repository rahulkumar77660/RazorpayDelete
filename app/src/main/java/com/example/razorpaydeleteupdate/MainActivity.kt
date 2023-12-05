package com.example.razorpaydeleteupdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.razorpaydeleteupdate.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() ,ClickListener{
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val header = "Basic " + Base64.encodeToString( "rzp_test_2XAQEnCY9O13rx:MpEmnhqgceL3PGIb6QZMvYgY".toByteArray(), Base64.NO_WRAP)
        val hasMap = HashMap<String,String?>()

        hasMap["Authorization"]=header

        binding.submit.setOnClickListener {
            val name = binding.itemName.text.toString()
            val des = binding.ItemDes.text.toString()
            val amount = binding.itemAmount.text.toString().toInt()*100
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.razorpay.com/v1/")
                .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter for JSON parsing, you can use other converters too
                .build()

            val data = ItemDataForPost(amount,"INR",des,name)

            val itemInterfaceApi = retrofit.create(ItemInterfaceApi::class.java)
            val myCall = itemInterfaceApi.postItemData(hasMap,data)
            myCall.enqueue(object : Callback<ItemDataForPostResponse?> {
                override fun onResponse(
                    call: Call<ItemDataForPostResponse?>,
                    response: Response<ItemDataForPostResponse?>
                ) {
                    if (response.isSuccessful){
                        binding.itemAmount.text.clear()
                        binding.itemName.text.clear()
                        binding.ItemDes.text.clear()
                        binding.itemName.requestFocus()
                        Toast.makeText(this@MainActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                        myAdapter.notifyDataSetChanged()
                    }

                }

                override fun onFailure(call: Call<ItemDataForPostResponse?>, t: Throwable) {

                }
            })
        }
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.razorpay.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter for JSON parsing, you can use other converters too
            .build()

        val interfaceApi = retrofit.create(ItemInterfaceApi::class.java)
        val myCall = interfaceApi.getItemData(hasMap)
        myCall.enqueue(object : Callback<ItemDataForGet?> {
            override fun onResponse(
                call: Call<ItemDataForGet?>,
                response: Response<ItemDataForGet?>
            ) {
                if (response.isSuccessful){
                    myAdapter= MyAdapter(baseContext,response.body()!!.items,this@MainActivity)
                    myAdapter.notifyDataSetChanged()
                    binding.recyclerView.adapter=myAdapter
                }
            }

            override fun onFailure(call: Call<ItemDataForGet?>, t: Throwable) {

            }
        })
    }

    override fun delete(id: String, position: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.razorpay.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter for JSON parsing, you can use other converters too
            .build()

        val header = "Basic " + Base64.encodeToString( "rzp_test_2XAQEnCY9O13rx:MpEmnhqgceL3PGIb6QZMvYgY".toByteArray(), Base64.NO_WRAP)
        val hasMap = HashMap<String,String?>()
        hasMap["Authorization"]=header

        val interfaceApi = retrofit.create(ItemInterfaceApi::class.java)
        val myCall = interfaceApi.delteItem(hasMap,id)
        myCall.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful){
                    Toast.makeText(this@MainActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                    myAdapter.notifyDataSetChanged()
                    myAdapter.notifyItemChanged(position)
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {

            }
        })
    }
}