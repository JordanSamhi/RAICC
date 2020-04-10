package lu.uni.trux.indirecticcresolver.utils;

import java.io.File;

public class Constants {

	/**
	 * Strigns
	 */
	public static final String TARGET_TYPE = "targetType";
	public static final String ACTIVITY = "a";
	public static final String RECEIVER = "r";
	public static final String SERVICE = "s";
	
	/**
	 * Files
	 */
	public static final String INDIRECT_ICC_METHODS = "/indirectIccMethods.txt";
	
	/**
	 * Classes
	 */
	public static final String ANDROID_APP_ALARMMANAGER = "android.app.AlarmManager";
	public static final String ANDROID_CONTENT_CONTEXT = "android.content.Context";
	
	/**
	 * Method signatures
	 */
	public static final String ANDROID_APP_ALARMMANAGER_SET = "<android.app.AlarmManager: void set(int,long,android.app.PendingIntent)>";
	public static final String ANDROID_APP_PENDING_INTENT_GET_ACTIVITY = "<android.app.PendingIntent: android.app.PendingIntent getActivity(android.content.Context,int,android.content.Intent,int)>";
	public static final String ANDROID_APP_PENDING_INTENT_GET_SERVICE = "<android.app.PendingIntent: android.app.PendingIntent getService(android.content.Context,int,android.content.Intent,int)>";
	public static final String ANDROID_APP_PENDING_INTENT_GET_BROADCAST = "<android.app.PendingIntent: android.app.PendingIntent getBroadcast(android.content.Context,int,android.content.Intent,int)>";

	/**
	 * Method subsignatures
	 */
	public static final String START_ACTIVITY = "void startActivity(android.content.Intent)";
	public static final String START_SERVICE = "void startService(android.content.Intent)";
	public static final String SEND_BROADCAST = "void sendBroadcast(android.content.Intent)";
	
	
	/**
	 * Directories
	 */
	public static final String TARGET_TMP_DIR = String.format("%s%s%s", System.getProperty("java.io.tmpdir"), File.separator, "indirectIccResolver");
}
