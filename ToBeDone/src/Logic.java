
public class Logic {
	private static final String CREATED_CANCLE_MESSAGE = "Now create operation is cancled";
	static boolean isConfirm;
	
	static void createTask(String taskInfo){
		 Logic.isConfirm = TobeDoneUI.isConfirm();
		 if(Logic.isConfirm)
			 Storage.save(taskInfo);
		 else {
			TobeDoneUI.showToUser(CREATED_CANCLE_MESSAGE);
		}
	}
	
	void deleteTask(int TaskID){
		
	}
	
	int getTaskID(int index){
		return 1;
	}
	
	static void createDone(String message){
		TobeDoneUI.showToUser(message);
	}
}
