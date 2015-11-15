package com.emprendev.matijesi.socialmap;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emprendev.matijesi.socialmap.clases.Usuario;
import com.emprendev.matijesi.socialmap.clases.Util;
import com.emprendev.matijesi.socialmap.webservices.ServicioWebSoap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

	private ImageButton btnRegistro;

	protected static GoogleMap gMap;

	private LocationManager locationManager;

	private static final String TAG = "MainActivity";

	private Context context;

	double latitudActual, longitudActual;

	private static float zoomActual = -1;

	boolean isCamaraEnUbicacion = false;

	boolean isClienteRegistrado;

	long fechaHoraUltActualizacion = 0;

	boolean isEjecutandoActualizacion = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		btnRegistro = (ImageButton) findViewById(R.id.ima_registrar);
		btnRegistro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, PerfilActivity.class);
				startActivity(intent);
			}
		});

		gMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		gMap.setMyLocationEnabled(true);

		locate();

		float zoom;
		if (zoomActual == -1) {
			zoom = 15.0F;
		} else {
			zoom = zoomActual;
		}
		CameraPosition INIT = new CameraPosition.Builder()
				.target(new LatLng(latitudActual, longitudActual)).zoom(zoom)
				.bearing(0.0F) // orientation.
				.tilt(50F) // viewing angle.
				.build();
		gMap.animateCamera(CameraUpdateFactory.newCameraPosition(INIT));

		gMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker arg0) {

				return null;

			}

			@Override
			public View getInfoContents(Marker marker) {

				View myContentView = getLayoutInflater().inflate(
						R.layout.dialogo_map, null);
				TextView titulo = ((TextView) myContentView
						.findViewById(R.id.titulo));
				titulo.setText(marker.getTitle());
				TextView descripcion = ((TextView) myContentView
						.findViewById(R.id.descripcion));
				descripcion.setText(marker.getSnippet());
				return myContentView;

			}
		});
		gMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {

				// marker.hideInfoWindow();
				// titulo = marker.getTitle();
				// mostrarDialogoAM(0, 0).show();
			}

		});
		gMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition pos) {
				if (pos.zoom != zoomActual) {
					zoomActual = pos.zoom;
				}
			}
		});

		isClienteRegistrado = Util.getIsClienteRegistrado(context);
		if (isClienteRegistrado) {
			isEjecutandoActualizacion = true;
			new ActualizarMarker().execute();
		}
	}

	private void colocarMarkerEnMapa(List<Usuario> listaUsuarios) {
		for (Usuario usuario : listaUsuarios) {
			addMarker(usuario);
		}
	}

	private void addMarker(Usuario usuario) {
		String snipper = "";

		BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inMutable = true;
		options.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_pin_drop_black_24dp, options);
		gMap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
				.position(
						new LatLng(usuario.getLatitud(), usuario.getLongitud()))
				.title(usuario.getNombre() + " " + usuario.getApellido())
				.snippet(snipper));

	}

	private void locate() {

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		double ultLatitudGPS = 0;
		double ultLongitudGPS = 0;
		double timeGPS = 0;
		double ultLatitudNETWORK = 0;
		double ultLongitudNETWORK = 0;
		double timeNETWORK = 0;

		try {
			ultLatitudGPS = locationManager.getLastKnownLocation(
					LocationManager.GPS_PROVIDER).getLatitude();
			ultLongitudGPS = locationManager.getLastKnownLocation(
					LocationManager.GPS_PROVIDER).getLongitude();
			timeGPS = locationManager.getLastKnownLocation(
					LocationManager.GPS_PROVIDER).getTime();
		} catch (Exception e) {
			timeGPS = 0;
		}

		try {
			ultLatitudNETWORK = locationManager.getLastKnownLocation(
					LocationManager.NETWORK_PROVIDER).getLatitude();
			ultLongitudNETWORK = locationManager.getLastKnownLocation(
					LocationManager.NETWORK_PROVIDER).getLongitude();
		} catch (Exception e) {
			timeNETWORK = 0;
		}
		if (timeNETWORK == 0 && timeGPS == 0) {
			latitudActual = 0;
			longitudActual = 0;
		} else {
			if (timeNETWORK > timeGPS) {
				latitudActual = ultLatitudNETWORK;
				longitudActual = ultLongitudNETWORK;
			} else {
				latitudActual = ultLatitudGPS;
				longitudActual = ultLongitudGPS;
			}
		}

		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {

				latitudActual = location.getLatitude();
				longitudActual = location.getLongitude();
				if (!isCamaraEnUbicacion && zoomActual == -1) {
					CameraPosition INIT = new CameraPosition.Builder()
							.target(new LatLng(latitudActual, longitudActual))
							.zoom(15.0F).bearing(0.0F).tilt(50F).build();

					gMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(INIT));
					isCamaraEnUbicacion = true;
				}
				long tiempoEntreConsulta = System.currentTimeMillis()
						- fechaHoraUltActualizacion;
				if (isClienteRegistrado && !isEjecutandoActualizacion
						&& tiempoEntreConsulta > 10000) {
					new ActualizarMarker().execute();
				}
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				10000, 10, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 10000, 10, locationListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class ActualizarMarker extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog = null;
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();

		@Override
		protected void onPreExecute() {
			// dialog = new ProgressDialog(MainActivity.this);
			// dialog.setTitle(getResources().getString(R.string.app_name));
			// dialog.setMessage("Cargando..");
			//
			// dialog.show();

			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {

			String idAndroid = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
			listaUsuarios = ServicioWebSoap
					.SWAndroidActualizarRecuperarGeoPosicionUsuario(idAndroid,
							String.valueOf(latitudActual),
							String.valueOf(longitudActual));

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			colocarMarkerEnMapa(listaUsuarios);

			Toast.makeText(context, "Actualizó", Toast.LENGTH_LONG).show();

			// if (dialog != null) {
			// dialog.dismiss();
			// }
			isEjecutandoActualizacion = false;

			super.onPostExecute(result);
		}
	}
	
//	private class EnviarEmergencia extends AsyncTask<Void, Void, Void> {
//		private ProgressDialog dialog = null;
//
//		@Override
//		protected void onPreExecute() {
//			// dialog = new ProgressDialog(MainActivity.this);
//			// dialog.setTitle(getResources().getString(R.string.app_name));
//			// dialog.setMessage("Cargando..");
//			//
//			// dialog.show();
//
//			super.onPreExecute();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//
//			String idAndroid = Secure.getString(context.getContentResolver(),
//					Secure.ANDROID_ID);
//			listaUsuarios = ServicioWebSoap
//					.SWAndroidActualizarRecuperarGeoPosicionUsuario(idAndroid,
//							String.valueOf(latitudActual),
//							String.valueOf(longitudActual));
//
//			return null;
//
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//
//			colocarMarkerEnMapa(listaUsuarios);
//
//			Toast.makeText(context, "Actualizó", Toast.LENGTH_LONG).show();
//
//			// if (dialog != null) {
//			// dialog.dismiss();
//			// }
//			isEjecutandoActualizacion = false;
//
//			super.onPostExecute(result);
//		}
//	}

}
