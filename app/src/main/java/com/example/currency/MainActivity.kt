package com.example.currency

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
        fun get(view: View)
        {
            val downloadData=Download()
            try {
                val url="https://api.exchangeratesapi.io/latest?base="
                val chosenbase=editText.text.toString()
                downloadData.execute(url+chosenbase)
            }
            catch (e:Exception)
            {
                e.printStackTrace()

            }
        }

    inner class Download:AsyncTask<String,Void,String>()
    {
        override fun doInBackground(vararg params: String?): String {
            var result=""
            var url:URL
            var httpURLConnection:HttpURLConnection
            try {
                url=URL(params[0])
                httpURLConnection=url.openConnection() as HttpURLConnection
                val inputStream=httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)
                var data=inputStreamReader.read()
                while(data>0)
                {
                    val character=data.toChar()
                    result=result+character
                    data=inputStreamReader.read()
                }
                return result



            }
            catch (e:Exception)
            {
                e.printStackTrace()
                println("error")
                return result
            }

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jSONObject=JSONObject(result)
                println(jSONObject)
                val base=jSONObject.getString("base")
                println(base)
                val date=jSONObject.getString("date")
                println(date)
                val rates=jSONObject.getString("rates")
                println(rates)
                val newJSONObject=JSONObject(rates)
                val eur=newJSONObject.getString("EUR").toDouble()
                val inr=newJSONObject.getString("INR").toDouble()
                val aud=newJSONObject.getString("AUD").toDouble()
                val usd=newJSONObject.getString("USD").toDouble()

                val amt=editText2.text.toString().toDouble()
                var res=inr*amt
                res=String.format("%.0f", res).toDouble()

                var r=aud*amt
                r=String.format("%.0f", r).toDouble()

                var res2=eur*amt
                res2=String.format("%.0f", res2).toDouble()
                var res3=usd*amt
                res3=String.format("%.0f", res3).toDouble()





                textView.text="INR: " + res
                textView2.text="AUD: "+ r
                textView3.text="EUR: "+ res2
                textView5.text="USD: "+ res3


                println(eur)




            }
            catch (e:Exception)
            {
                e.printStackTrace()
                println("error2")

            }


        }
    }
}
