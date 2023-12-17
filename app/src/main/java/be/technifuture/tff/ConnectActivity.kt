package be.technifuture.tff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.technifuture.tff.databinding.ActivityConnectBinding

class ConnectActivity : AppCompatActivity() {
    lateinit var binding: ActivityConnectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
