package um.edu.ar.data

import android.content.Context
import android.util.Log

object SessionStore {
    fun save(ctx: Context, sessionId: String) {
        Log.d("SessionStore", "SAVE sessionId=$sessionId")
        ctx.getSharedPreferences("session", Context.MODE_PRIVATE)
            .edit()
            .putString("sessionId", sessionId)
            .apply()
    }

    fun load(ctx: Context): String? {
        val s = ctx.getSharedPreferences("session", Context.MODE_PRIVATE)
            .getString("sessionId", null)
        Log.d("SessionStore", "LOAD sessionId=$s")
        return s
    }

    fun clear(ctx: Context) {
        Log.d("SessionStore", "CLEAR")
        ctx.getSharedPreferences("session", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}

