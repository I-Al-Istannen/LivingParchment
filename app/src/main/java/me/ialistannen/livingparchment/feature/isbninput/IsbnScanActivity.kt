package me.ialistannen.livingparchment.feature.isbninput

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.google.zxing.integration.android.IntentIntegrator
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.feature.BaseActivity

abstract class IsbnScanActivity : BaseActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_isbn_scan_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scan_barcode -> {
                IntentIntegrator(this).initiateScan()
                true
            }
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                ?: return

        if (!result.contents.isNullOrBlank()) {
            onIsbnScanned(result.contents)
        }
    }

    protected abstract fun onIsbnScanned(isbn: String)
}