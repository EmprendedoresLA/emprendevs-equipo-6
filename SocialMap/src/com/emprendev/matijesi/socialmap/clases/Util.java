package com.emprendev.matijesi.socialmap.clases;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.emprendev.matijesi.socialmap.MainActivity;
import com.emprendev.matijesi.socialmap.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Util {
	
	public static final String NAME_BROADCASTRECEIVER_UPDATE_LIST = "soft.oliveromatias.gcm.updatelist";

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	public static final String IS_CLIENTE_REGISTRADO = "is_cliente_registrado";
	public static final String IS_CLIENTE_AVISO_EN_CAMINO = "is_cliente_aviso_en_camino";
	public static final String IS_VISTA_NOTIF_EMERG = "is_vista_notif_emerg";
	public static final String FECHA_HORA_ULTIMO_MSJ_RECIBIDO = "fecha_hora_ultimo_msj_recibido";
	public static final String PROPERTY_APP_VERSION = "appVersion";
	public static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
	public static final String PROPERTY_USER = "user";
	public static final String DATOS_CLIENTE_NOMBRE = "cliente_nombre";
	public static final String DATOS_CLIENTE_APELLIDO = "cliente_apellido";
	public static final String DATOS_CLIENTE_TEL = "cliente_tel";
	public static final String DATOS_CLIENTE_MAIL = "cliente_mail";
	public static final String DATOS_CLIENTE_EDAD = "cliente_edad";
	public static final String DATOS_CLIENTE_LOCALIDAD = "cliente_localidad";
	public static final String DATOS_CLIENTE_SEXO = "cliente_sexo";

	
	
    public static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24 * 7;
	public static String SENDER_ID = "200951224651";
	
	public static void setRegistrationId(Context context, String idAndroid, String regId) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

		int appVersion = getAppVersion(context);

		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_USER, idAndroid);
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.putLong(PROPERTY_EXPIRATION_TIME, System.currentTimeMillis()
				+ EXPIRATION_TIME_MS);

		editor.commit();
	}
	
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Error al obtener versión: " + e);
		}
	}


	public static void setDatosClienteRegistrado(Context context,
			String nombre, String apellido, String tel, String mail, String edad, String sexo, String localidad) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(DATOS_CLIENTE_NOMBRE, nombre);
		editor.putString(DATOS_CLIENTE_APELLIDO, apellido);
		editor.putString(DATOS_CLIENTE_TEL, tel);
		editor.putString(DATOS_CLIENTE_MAIL, mail);
		editor.putString(DATOS_CLIENTE_EDAD, edad);
		editor.putString(DATOS_CLIENTE_SEXO, sexo);
		editor.putString(DATOS_CLIENTE_LOCALIDAD, localidad);

		editor.commit();
		
	}
	
	public static String getDatosClienteNombre(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getString(DATOS_CLIENTE_NOMBRE, "");		
	}
	
	public static String getDatosClienteApellido(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getString(DATOS_CLIENTE_APELLIDO, "");		
	}
	
	public static String getDatosClienteTel(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getString(DATOS_CLIENTE_TEL, "");		
	}
	
	public static String getDatosClienteMail(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getString(DATOS_CLIENTE_MAIL, "");		
	}
	
	public static boolean getIsClienteRegistrado(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getBoolean(Util.IS_CLIENTE_REGISTRADO, false);
	}
	
	public static String getDatosClienteEdad(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getString(DATOS_CLIENTE_EDAD, "");
	}
	
	public static void setDatosClienteEdad(Context context,
			String edad) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(DATOS_CLIENTE_EDAD, edad);
		editor.commit();
		
	}
	
	public static String getDatosClienteLocalidad(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getString(DATOS_CLIENTE_LOCALIDAD, "");
	}
	
	public static void setDatosClienteLocalidad(Context context,
			String localidad) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(DATOS_CLIENTE_LOCALIDAD, localidad);
		editor.commit();
		
	}
	
	public static String getDatosClienteSexo(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		return prefs.getString(DATOS_CLIENTE_SEXO, "");
	}
	
	public static void setDatosClienteSexo(Context context,
			String sexo) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(DATOS_CLIENTE_SEXO, sexo);
		editor.commit();
		
	}
	
	
	public static void setIsClienteRegistrado(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		Editor edit = prefs.edit();
		edit.putBoolean(IS_CLIENTE_REGISTRADO, true);
		edit.commit();
	}

	
	public static void fijarPantalla(Context ctx, Activity act) {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			switch (ctx.getResources().getConfiguration().orientation) {
			case Configuration.ORIENTATION_PORTRAIT:
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
					act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				} else {
					int rotation = act.getWindowManager().getDefaultDisplay().getRotation();
					if (rotation == android.view.Surface.ROTATION_90 || rotation == android.view.Surface.ROTATION_180) {
						act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
					} else {
						act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					}
				}
				break;

			case Configuration.ORIENTATION_LANDSCAPE:
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
					act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}
				int rotation = act.getWindowManager().getDefaultDisplay().getRotation();
				if (rotation == android.view.Surface.ROTATION_0 || rotation == android.view.Surface.ROTATION_90) {
					act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				} else {
					act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
				}
				break;
			}

		} else {
			if (ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				act.setRequestedOrientation(Configuration.ORIENTATION_PORTRAIT);
			} else {
				act.setRequestedOrientation(Configuration.ORIENTATION_LANDSCAPE);
			}
		}
	}


	
	public static void dialogErrorConexion(final Context mContext) {

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
		builder.setMessage(mContext.getResources().getString(R.string.internet_error));

		builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});

		final AlertDialog dialog = builder.create();
		dialog.show();
	}
	

	public static boolean checkPlayServices(Context ctx, Activity act) {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
		if (status != ConnectionResult.SUCCESS) {
			return false;
		}
		return true;
	}

	public static boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}




}
