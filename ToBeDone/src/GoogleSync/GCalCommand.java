package googlesync;
import googlesync.GoogleCalendar;
import java.util.Vector;

import com.Storage;
import com.TaskItem;
import com.Command;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import googlesync.Constants;

public class GCalCommand extends Command {
	private String feedback = null;
	private GoogleCalendar googleCal = null;
	
	public GCalCommand(String commandType, Vector<String> commandParameters) {
		super(commandType, commandParameters);
	}
	Vector<TaskItem> allTasks;
	protected void executeCommand() throws Exception{
		try{
			googleCal = GoogleCalendar.getInstance();
			if(!googleCal.isAuthorized()){
				googleCal.initAuthenication();
			}
			// CalendarList
			String chosenCalendarId = googleCal.chooseCalendar();
			allTasks = googleCal.sync(allTasks, chosenCalendarId);
			//updateTaskIDs();
			googleCal.gSyncDao.updateFile(allTasks);
		}catch(ServiceNotAvailableException t){
			feedback = Constants.SERVER_UNAVAILABLE;
		}	
		feedback = Constants.MESSAGE_SYNC_SUCCESS;
	}

	public String getFeedback(){
		return feedback;
	}
}

