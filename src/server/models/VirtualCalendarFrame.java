package server.models;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import server.Main;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;

public class VirtualCalendarFrame extends VirtualFrame {

	DateTime wakeUpHour = null;
	DateTime currentTime = null;
	boolean mUpdateSuccess;

	public VirtualCalendarFrame(Long id, Long idSensor, String type) {
		super(id, idSensor, type);
		mUpdateSuccess = false;
	}

	public String getCurrentDate() {
		Calendar cal1 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal1.getTime());
	}

	public DateTime getCurrentDateTime() {
		return DateTime.now();

	}

	static CalendarService myService = new CalendarService("GHOME GHOME");

	public VirtualCalendarFrame createCalendarService()
			throws AuthenticationException {
		myService.setUserCredentials("ghomepowerhouse@gmail.com", "GHOME2013");
		return null;

	}

	public DateTime getWakeUpHour() throws Exception {
		return wakeUpHour;

	}
	public DateTime getCurrentTime() throws Exception {
		return currentTime;

	}

	public DateTime retrieveEvents(String startTime2, String endTime2)
			throws Exception {
		// Example startTime and endTime under format 2006-03-24T23:59:59
		DateTime retour = null;
		URL feedUrl = new URL(
				"https://www.google.com/calendar/feeds/default/private/full");

		CalendarQuery myQuery = new CalendarQuery(feedUrl);
		myQuery.setMinimumStartTime(DateTime.parseDateTime(startTime2));
		myQuery.setMaximumStartTime(DateTime.parseDateTime(endTime2));

		CalendarService myService = new CalendarService(
				"exampleCo-exampleApp-1");
		myService.setUserCredentials(Main.Configuration.getMailUserServer(), 
				Main.Configuration.getMailPassword() );
		// "ghomepowerhouse@gmail.com" "GHOME2013"
		// Send the request and receive the response:

		CalendarEventFeed myResultsFeed = myService.query(myQuery,
				CalendarEventFeed.class);

		List<When> timeEventRetrieved;

		if (myResultsFeed.getEntries().size() > 0) {
			CalendarEventEntry firstMatchEntry = (CalendarEventEntry) myResultsFeed
					.getEntries().get(0);
			timeEventRetrieved = firstMatchEntry.getTimes();

			retour = timeEventRetrieved.get(0).getStartTime();
		}

		return retour;

	}

	@Override
	public Sensor populate(Sensor s) {
		VirtualCalendarSensor vs = (VirtualCalendarSensor) s;
		if (mUpdateSuccess) {
			vs.setWakeUpHour(wakeUpHour.getValue());
			vs.setCurrentTime(currentTime.getValue());
		}
		return s;
	}

	@Override
	public void updateState() {
		try {
			wakeUpHour = retrieveEvents(getCurrentDate() + "T00:00:00", getCurrentDate() + "T23:59:59");
			if (wakeUpHour == null) {
				wakeUpHour = new DateTime(DateTime.now().getValue() + (300000 * 60 * 60));
			}
			else
			{
				wakeUpHour = new DateTime(wakeUpHour.getValue() - (1000 * 60 * 60));
			}
				currentTime = getCurrentDateTime();
				mUpdateSuccess = true;
		     			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
		

}
