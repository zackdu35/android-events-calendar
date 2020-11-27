package com.example.localized_font_test

import android.database.Cursor
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

const val EVENTS_COUNT = 20

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
        val eventsUri = CalendarContract.Events.CONTENT_URI
        val remindersUri = CalendarContract.Reminders.CONTENT_URI
        val eventDays = CalendarContract.EventDays.CONTENT_URI
        val attendeesUri = CalendarContract.Attendees.CONTENT_URI
        val instancesUri = CalendarContract.Instances.CONTENT_URI
        val calendarsUri = CalendarContract.Calendars.CONTENT_URI

        val projection = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_LOCATION
        )
        val sortOrder = "${CalendarContract.Events._ID} DESC"

        val cursorEvent = contentResolver.query(eventsUri, projection, null, null, sortOrder)
        val cursorReminders = contentResolver.query(remindersUri, projection, null, null, null)
        val cursorEventDays = contentResolver.query(eventDays, projection, null, null, null)
        val cursorAttendees = contentResolver.query(attendeesUri, projection, null, null, null)
        val cursorInstances = contentResolver.query(instancesUri, projection, null, null, null)
        val cursorCalendars = contentResolver.query(calendarsUri, projection, null, null, null)

        queryEvents(cursorEvent)
    }

    private fun queryEvents(cursorEvent: Cursor?) {
        cursorEvent?.apply {
            moveToFirst()
            arrayOfNullEvents(count).map {
                val eventId = getInt(0)
                val calendarId = getInt(1)
                val title = getString(2)
                val desc = getString(3)
                val startDate = Date(getLong(4))
                val endDate = Date(getLong(5))
                val location = getString(6)
                eventsTv.append("EVENT_ID : $eventId\n")
                eventsTv.append("CALENDAR_ID : $calendarId\n")
                eventsTv.append("TITLE : $title\n")
                eventsTv.append("DESC : $desc\n")
                eventsTv.append("START DATE : $startDate\n")
                eventsTv.append("END DATE : $endDate\n")
                eventsTv.append("LOCATION : $location\n")
                eventsTv.append("\n")
                moveToNext()
            }
            close()
        }
    }

    private fun arrayOfNullEvents(count: Int): Array<String?> {
        return arrayOfNulls(/*if (count < EVENTS_COUNT) */count /*else EVENTS_COUNT*/)
    }
}