package com.example.localized_font_test

import android.os.Bundle
import android.provider.CalendarContract
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

    ///////////////////////////////////////////////////////////////////////////
    // LIFECYCLE
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readCalendarEntries()
    }

    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////

    private fun readCalendarEntries() {
        val contentResolver = requireContext().contentResolver
        val providerUri = CalendarContract.Events.CONTENT_URI
        val projection = arrayOf(
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_LOCATION
        )
        val cursor = contentResolver.query(providerUri, projection, null, null, null)

        cursor?.apply {
            moveToFirst()
            val events = arrayOfNulls<String>(count)
            events.map {
                val id = getInt(0)
                val title = getString(1)
                val desc = getString(2)
                val startDate = Date(getLong(3))
                val endDate = Date(getLong(4))
                val location = getString(5)
                eventsTv.append(
                    "Id : $id \nTitle : $title \nDesc : $desc \nStart Date : $startDate \nEnd Date : $endDate \nLocation : $location" +
                            "\n----------\n"
                )
                moveToNext()
            }
            close()
        }
    }
}