package unicauca.movil.museo

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unicauca.movil.museo.model.Pintura
import unicauca.movil.museo.net.ApiClient

class MainActivity : AppCompatActivity(), Callback<Pintura> {

    lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nfcManager: NfcManager
                = getSystemService(Context.NFC_SERVICE) as NfcManager

        nfcAdapter = nfcManager.defaultAdapter
    }

    //region NFC
    override fun onResume() {
        super.onResume()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(this,
                0, intent, 0)

        nfcAdapter.enableForegroundDispatch(this,
                pendingIntent, null, null)
    }

    override fun onStop() {
        super.onStop()
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tag: Tag = intent.extras.getParcelable(NfcAdapter.EXTRA_TAG)
        val id = tag.id
        val idHex = byteToStringHex(id)
        Log.i("NFCID", idHex)
        ApiClient.pinturas.getPictureInfo(idHex)
                .enqueue(this)
    }

    fun byteToStringHex(bytes: ByteArray): String {
        val buffer = StringBuffer()
        bytes.forEach { buffer.append(it.toString(16)) }
        return buffer.toString();
    }
    //endregion

    //region Pinturas Api
    override fun onResponse(call: Call<Pintura>?, response: Response<Pintura>) {
        when(response.code()){
            200->{
                val pintura = response.body()
                // Navegar a otro Activity y mandarle la info
            }
            404->Toast.makeText(this, "Pintura no encontrada", Toast.LENGTH_SHORT)
                    .show()
            else->Toast.makeText(this, "Error al recuperar información", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun onFailure(call: Call<Pintura>?, t: Throwable?) {
        Toast.makeText(this, "Error al recuperar información", Toast.LENGTH_SHORT)
                .show()
    }
    //endregion

}
