package ru.anisart.stravaflyby

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        const val STRAVA_LINK = "https://strava.app.link/"
        const val STRAVA_ACTIVITY = "https://www.strava.com/activities/"
        const val STRAVA_FLYBY = "https://labs.strava.com/flyby/viewer/#"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent == null || intent.action == Intent.ACTION_MAIN) {
            return
        }

        val text = intent?.clipData?.getItemAt(0)?.text

        if (text == null) {
            notMatch()
            return
        }

        when (val stravaUrl = parseShortUrl(text)) {
            null -> {
                when (val activityId = parseFullUrl(text)) {
                    null -> {
                        notMatch()
                        return
                    }
                    else -> {
                        openFlyby(activityId)
                    }
                }
            }
            else -> {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(stravaUrl)
                    .build()
                client.newCall(request)
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            System.err.println(e)
                            httpError()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val activityId = response.body?.string()?.let { parseFullUrl(it) }
                            response.close()
                            openFlyby(activityId)
                        }
                    })
            }
        }
    }

    private fun notMatch() {
        Toast.makeText(this, getString(R.string.not_strava_link), Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    private fun httpError() {
        Toast.makeText(this, getString(R.string.http_error), Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    private fun openFlyby(activityId: String?) {
        activityId ?: return

        val flybyUrl = "$STRAVA_FLYBY$activityId"
        val viewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(flybyUrl))
        startActivity(viewIntent)
        finish()
    }
}

fun parseShortUrl(string: CharSequence): String? {
    return Regex(pattern = """${MainActivity.STRAVA_LINK}[\w-_?&=%/]+""")
        .find(input = string)?.value
}

fun parseFullUrl(string: CharSequence): String? {
    return Regex(pattern = """${MainActivity.STRAVA_ACTIVITY}([\d]+)""")
        .find(input = string)?.groupValues?.get(1)
}
