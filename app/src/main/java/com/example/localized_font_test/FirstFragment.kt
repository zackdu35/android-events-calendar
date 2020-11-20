package com.example.localized_font_test

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readCalendarEntries()
    }

    private fun readCalendarEntries() {
        val cr = requireContext().contentResolver
        val cursor = cr.query(
            Uri.parse("content://com.android.calendar/events"),
            arrayOf(
                "calendar_id",
                "title",
                "description",
                "dtstart",
                "dtend",
                "eventLocation"
            ),
            null,
            null,
            null
        )
        //Cursor cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "name" }, null, null, null);
        //Cursor cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "name" }, null, null, null);
        cursor?.apply {
            var add: String? = null
            moveToFirst()
            val CalNames = arrayOfNulls<String>(cursor.getCount())
            val CalIds = IntArray(cursor.getCount())
            for (i in CalNames.indices) {
                CalIds[i] = cursor.getInt(0)
                CalNames[i] =
                    """
                    Event${cursor.getInt(0).toString()}: 
                    Title: 
                    """.trimIndent() + cursor.getString(1)
                        .toString() + "\nDescription: " + cursor.getString(2)
                        .toString() + "\nStart Date: " + Date(cursor.getLong(3)).toString() + "\nEnd Date : " + Date(
                        cursor.getLong(4)
                    ).toString() + "\nLocation : " + cursor.getString(5)
                if (add == null) add = CalNames[i] else {
                    add += CalNames[i]
                }
                events.text = add
                cursor.moveToNext()
            }
            close()
        }

    }
}