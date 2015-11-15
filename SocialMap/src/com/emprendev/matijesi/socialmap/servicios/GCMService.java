package com.emprendev.matijesi.socialmap.servicios;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.emprendev.matijesi.socialmap.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

@SuppressLint("Wakelock")
public class GCMService extends IntentService {

	private static final int NOTIF_ALERTA_ID = 1599;

	private static final int ALARM_REQUEST_CODE = 1;

	public static final String BROADCAST_ACTION_ALERTA = "com.emprendev.matijesi.socialmap";

	private static String[] aux;

	public GCMService() {
		super("GCMService");
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onHandleIntent(Intent intent) {

		try {
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

			String messageType = gcm.getMessageType(intent);
			Bundle extras = intent.getExtras();

			if (!extras.isEmpty()) {
				if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
						.equals(messageType)) {

					String msj = extras.getString("msg");
					Log.i("GCMService", "Push: " + msj);
					sendBroadcast(new Intent(BROADCAST_ACTION_ALERTA));
					mostrarNotification(msj);
					vibrarYSonar();

					GCMBroadcastReceiver.completeWakefulIntent(intent);

				}

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	private void vibrarYSonar() {
		try {
		    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
		    r.play();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		Vibrator vibrator = (Vibrator) getApplicationContext()
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(300);
	}

	private void mostrarNotification(String msg) {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(getResources().getString(R.string.app_name))
				.setContentText(msg);

		Intent notIntent = new Intent(this,
				com.emprendev.matijesi.socialmap.MainActivity.class);
		// set intent so it does not start a new activity
		notIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contIntent = PendingIntent.getActivity(this, 0,
				notIntent, PendingIntent.FLAG_CANCEL_CURRENT); // FLAG_UPDATE_CURRENT
																// o //
																// FLAG_ONE_SHOT

		mBuilder.setContentIntent(contIntent);
		mBuilder.setAutoCancel(true);
		mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
	}
}