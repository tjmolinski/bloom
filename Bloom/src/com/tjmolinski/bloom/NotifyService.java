package com.tjmolinski.bloom;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.tjmolinski.bloom.util.Day;
import com.tjmolinski.bloom.util.Utils;

public class NotifyService extends Service {

	/**
	 * Class for clients to access
	 */
	public class ServiceBinder extends Binder {
		NotifyService getService() {
			return NotifyService.this;
		}
	}

	// Unique id to identify the notification.
	private static final int NOTIFICATION = 123;
	// Name of an intent extra we can use to identify if this service was
	// started to create a notification
	public static final String INTENT_NOTIFY = "com.tjmolinski.bloom.INTENT_NOTIFY";
	// The system notification manager
	private NotificationManager mNM;

	@Override
	public void onCreate() {
		Log.i("NotifyService", "onCreate()");
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("LocalService", "Received start id " + startId + ": " + intent);

		// If this service was started by out AlarmTask intent then we want to
		// show our notification
		if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
			Bloom bloom = (Bloom) intent.getParcelableExtra("BLOOM");
			if(isReminderDayForBloom(bloom)) {
				showNotification(bloom);
			}
		}

		// We don't care if this service is stopped as we have already delivered
		// our notification
		return START_NOT_STICKY;
	}

	private boolean isReminderDayForBloom(Bloom bloom) {
		int days = bloom.getReminderDays();
		int currentDay = Utils.getDayFromId(new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1).getFlag();
		if(Utils.isDayFlagSet(days, currentDay)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// This is the object that receives interactions from clients
	private final IBinder mBinder = new ServiceBinder();

	/**
	 * Creates a notification and shows it in the OS drag-down status bar
	 */
	private void showNotification(Bloom bloom) {
		// This is the 'title' of the notification
		CharSequence title = "Bloom";
		// This is the icon to use on the notification
		int icon = R.drawable.ic_launcher;
		// This is the scrolling text of the notification
		CharSequence description = "Time to Bloom!";
		// Vibration pattern
		long[] pattern = {400, 200, 400, 100, 400, 200};
		
		// The PendingIntent to launch our activity if the user selects this notification
		Intent intent = new Intent(this, BloomCamera.class);
		intent.putExtra("BLOOM", bloom);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification notification = new Notification.Builder(this)
				.setContentIntent(contentIntent)
		        .setContentTitle(title)
		        .setContentText(description)
		        .setSmallIcon(icon)
		        .setVibrate(pattern)
		        .setLights(Color.WHITE, 1000, 1000)
		        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
		        .build();
		
		// Send the notification to the system.
		mNM.notify(NOTIFICATION, notification);

		// Stop the service when we are finished
//		stopSelf();
	}
}