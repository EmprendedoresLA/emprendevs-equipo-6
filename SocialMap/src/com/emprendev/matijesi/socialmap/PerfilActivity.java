package com.emprendev.matijesi.socialmap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.emprendev.matijesi.socialmap.clases.Util;
import com.emprendev.matijesi.socialmap.webservices.ServicioWebSoap;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class PerfilActivity extends Activity {

	private Context context;
	private String regid;
	private GoogleCloudMessaging gcm;

	private ImageButton btn_send;
	private ImageButton btn_edit;

	private Spinner sp_sexo;
	public int idItemSelectSpinnerProveedor;
	public String descItemSelectSpinnerProveedor;

	private EditText edt_nombre;
	private EditText edt_apellido;
	private EditText edt_tel;
	private EditText edt_mail;
	private EditText edt_edad;
	private EditText edt_localidad;
	boolean isRegistrado;
	String idAndroid;
	private RadioGroup radioSexGroup;
	private RadioButton radioButtonMasc;
	private RadioButton radioButtonFem;
	TextView txt_idAndroid;

    private DatePickerDialog editDatePickerDialog;
    
    private SimpleDateFormat dateFormatter;
    
	private static final String TAG = "PerfilActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfil_main);
		context = getApplicationContext();
		
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		txt_idAndroid = (TextView) findViewById(R.id.textViewIdApp);
		edt_nombre = (EditText) findViewById(R.id.editTextNombre);
		edt_apellido = (EditText) findViewById(R.id.editTextApellido);
		edt_tel = (EditText) findViewById(R.id.editTextTelefono);
		edt_mail = (EditText) findViewById(R.id.editTextMail);
		edt_edad = (EditText) findViewById(R.id.editTextEdad);
		edt_edad.setInputType(InputType.TYPE_NULL);
		setDateTimeField();
		edt_localidad = (EditText) findViewById(R.id.editTextLocalidad);

		sp_sexo = (Spinner) findViewById(R.id.spinnerSexo);

		Spinner spinner = (Spinner) findViewById(R.id.spinnerSexo);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.array_sexo, R.layout.simple_spinner_item_sexo);
		spinner.setAdapter(adapter);

		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_sexo);

		// radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		// radioButtonMasc = (RadioButton) findViewById(R.id.radioM);
		// radioButtonFem = (RadioButton) findViewById(R.id.radioF);

		btn_send = (ImageButton) findViewById(R.id.imageButtonSend);
		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edt_nombre.getText().toString().length() == 0) {
					Toast.makeText(PerfilActivity.this,
							"Debe ingresar el nombre", Toast.LENGTH_LONG)
							.show();
					return;
				}
				if (edt_apellido.getText().toString().length() == 0) {
					Toast.makeText(PerfilActivity.this,
							"Debe ingresar el apellido", Toast.LENGTH_LONG)
							.show();
					return;
				}
				if (edt_tel.getText().toString().length() == 0) {
					Toast.makeText(PerfilActivity.this,
							"Debe ingresar su teléfono", Toast.LENGTH_LONG)
							.show();
					return;
				}
				if (edt_edad.getText().toString().length() == 0) {
					Toast.makeText(PerfilActivity.this,
							"Debe ingresar su fecha de nacimiento", Toast.LENGTH_LONG)
							.show();
					return;
				}
				if (edt_localidad.getText().toString().length() == 0) {
					Toast.makeText(PerfilActivity.this,
							"Debe ingresar su lugar de residencia", Toast.LENGTH_LONG)
							.show();
					return;
				}
				if (edt_mail.getText().toString().length() == 0) {
					Toast.makeText(PerfilActivity.this,
							"Debe ingresar su e-mail", Toast.LENGTH_LONG)
							.show();
					return;
				}
				// if (edt_apellido.getText().toString().length() == 0) {
				// Toast.makeText(PerfilActivity.this,
				// "Debe ingresar el Apellido", Toast.LENGTH_LONG)
				// .show();
				// return;
				// }
				dialogSeguroEnviar().show();
			}
		});
		idAndroid = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		isRegistrado = Util.getIsClienteRegistrado(context);
		iniciarControles();

	}

	private void setDateTimeField() {       
		edt_edad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editDatePickerDialog.show();
			}
		});
		
        Calendar newCalendar = Calendar.getInstance();
        editDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edt_edad.setText(dateFormatter.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        	
	}

	private void iniciarControles() {
		String idAndroidCorto = "ID: "
				+ idAndroid.substring(idAndroid.length() - 4,
						idAndroid.length());
		if (isRegistrado) {
			 btn_send.setVisibility(EditText.VISIBLE);
			edt_nombre.setEnabled(false);
			edt_nombre.setText(Util.getDatosClienteNombre(context));
			edt_apellido.setEnabled(false);
			edt_apellido.setText(Util.getDatosClienteApellido(context));
			edt_tel.setEnabled(false);
			edt_tel.setText(Util.getDatosClienteTel(context));
			edt_mail.setEnabled(false);
			edt_mail.setText(Util.getDatosClienteMail(context));
			txt_idAndroid.setText(idAndroidCorto + "  "
					+ getResources().getString(R.string.registrado));
			edt_edad.setText(Util.getDatosClienteEdad(context));
			edt_edad.setEnabled(false);
			edt_localidad.setText(Util.getDatosClienteLocalidad(context));
			edt_localidad.setEnabled(false);
			if (Util.getDatosClienteSexo(context).equals("Masculino")) {
				// radioButtonMasc.setChecked(true);
				sp_sexo.setSelection(0);
			} else {
				if (Util.getDatosClienteSexo(context).equals("Femenino")) {
					// radioButtonFem.setChecked(true);
					sp_sexo.setSelection(1);
				}
			}
			sp_sexo.setEnabled(false);
			// radioButtonMasc.setEnabled(false);
			// radioButtonFem.setEnabled(false);
		} else {
			btn_send.setVisibility(EditText.VISIBLE);
			edt_nombre.setEnabled(true);
			edt_apellido.setEnabled(true);
			edt_tel.setEnabled(true);
			edt_mail.setEnabled(true);
			txt_idAndroid.setText(idAndroidCorto
					+ "  "
					+ getResources().getString(
							R.string.no_registrado));
			edt_edad.setEnabled(true);
			edt_localidad.setEnabled(true);
			sp_sexo.setEnabled(true);
			// radioButtonMasc.setEnabled(true);
			// radioButtonFem.setEnabled(true);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
//		Intent intent = new Intent();
//		intent.setClass(context, PerfilActivity.class);
//		startActivity(intent);
		super.onBackPressed();
	}


	private Dialog dialogSeguroEnviar() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				PerfilActivity.this, R.style.AppCompatAlertDialogStyle);
		builder.setMessage(context.getResources().getString(
				R.string.desea_enviar_la_solicitud_de_usuario));

		builder.setPositiveButton(R.string.aceptar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Tarea Asinc
						String sexo = null;
//						switch (radioSexGroup.getCheckedRadioButtonId()) {
//						case R.id.radioM:
//							sexo = "Masculino";
//							break;
//						case R.id.radioF:
//							sexo = "Femenino";
//							break;
//						}
						new EnviarSolicitud(edt_nombre.getText().toString(),
								edt_apellido.getText().toString(), edt_tel.getText().toString(), edt_mail
										.getText().toString(), idAndroid,
								edt_edad.getText().toString(), (String) sp_sexo.getSelectedItem(),
								edt_localidad.getText().toString()).execute();
						dialog.dismiss();
					}
				});
		builder.setNegativeButton(R.string.cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
		final AlertDialog dialog = builder.create();
		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class EnviarSolicitud extends AsyncTask<Void, Void, Void> {

		String resp;
		private ProgressDialog dialog;
		String nombre;
		String apellido;
		String tel;
		String mail;
		String idAndroid;
		String registrationID;
		String edad;
		String sexo;
		String localidad;

		public EnviarSolicitud(String nombre, String apellido, String tel,
				String mail, String idAndroid, String edad, String sexo,
				String localidad) {
			this.nombre = nombre;
			this.apellido = apellido;
			this.tel = tel;
			this.mail = mail;
			this.idAndroid = idAndroid;
			this.edad = edad;
			this.sexo = sexo;
			this.localidad = localidad;
		}

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(PerfilActivity.this,R.style.AppCompatAlertDialogStyle);
			dialog.setIcon(R.drawable.ic_launcher);
			dialog.setMessage(getResources().getString(
					R.string.enviando_solicitud));
			dialog.setTitle(getResources().getString(R.string.app_name));
			dialog.setCancelable(false);
			Util.fijarPantalla(context, PerfilActivity.this);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			registrationID = recuperarRegistracionID();
			resp = ServicioWebSoap.SWAndroidRegistroUsuario(
					getApplicationContext(), nombre, apellido, tel, mail,
					idAndroid, registrationID, edad, sexo, localidad);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (resp.charAt(0) == '0') {
				Toast.makeText(getApplicationContext(), "Solicitud aceptada",
						Toast.LENGTH_LONG).show();
				// Guardamos los datos del registro
				Util.setIsClienteRegistrado(context);
				Util.setDatosClienteRegistrado(context, nombre, apellido, tel,
						mail, edad, sexo, localidad);
				Util.setRegistrationId(context, this.idAndroid, regid);
				btn_send.setVisibility(EditText.VISIBLE);
				edt_nombre.setEnabled(false);
				edt_apellido.setEnabled(false);
				edt_tel.setEnabled(false);
				edt_mail.setEnabled(false);
				edt_edad.setEnabled(false);
				edt_localidad.setEnabled(false);
				sp_sexo.setEnabled(false);
				// radioButtonMasc.setEnabled(false);
				// radioButtonFem.setEnabled(false);
			} else
				Toast.makeText(getApplicationContext(),
						"Ocurrió algún error, vuelva a intentarlo",
						Toast.LENGTH_LONG).show();

			dialog.dismiss();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
			super.onPostExecute(result);
		}

		private String recuperarRegistracionID() {
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}

				// Nos registramos en los servidores de GCM
				regid = gcm.register(Util.SENDER_ID);

				Log.d(TAG, "Registrado en GCM: registration_id=" + regid);

				return regid;
			} catch (IOException ex) {
				Log.d(TAG, "Error registro en GCM:" + ex.getMessage());
				return "";
			}
		}

	}

}

