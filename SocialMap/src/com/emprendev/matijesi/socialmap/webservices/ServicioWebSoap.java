package com.emprendev.matijesi.socialmap.webservices;


import java.util.Arrays;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.emprendev.matijesi.socialmap.clases.Usuario;

import android.content.Context;
import android.util.Log;

public class ServicioWebSoap {
	private static final String TAG = "ServicioWebSoap";

	// WS ANDROID

	public static String SWAndroidRegistroUsuario(Context context,
			String nombre, String apellido, String tel, String mail,
			String idAndroid, String registrationID, String fechaNac, String sexo,
			String localidad) {
		String resp;

		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://wi081910.ferozo.com/HackathonMatiJesi.asmx";
		final String METHOD_NAME = "SWAndroidRegistroUsuario";
		final String SOAP_ACTION = "http://tempuri.org/SWAndroidRegistroUsuario";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("a", "matijesi1234.");
		request.addProperty("idAndroid", idAndroid);
		request.addProperty("nombreUsuario", nombre);
		request.addProperty("apellidoUsuario", apellido);
		request.addProperty("telefono", tel);
		request.addProperty("mail", mail);
		request.addProperty("codigoC2DM", registrationID);
		request.addProperty("fechaNac", fechaNac);
		request.addProperty("sexo", sexo);
		request.addProperty("localidad", localidad);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);

		try {
			transporte.call(SOAP_ACTION, envelope);

			SoapPrimitive resultado_xml = (SoapPrimitive) envelope
					.getResponse();
			resp = resultado_xml.toString();
		} catch (Exception e) {
			Log.d(TAG, "Error registro en mi servidor: " + e.getCause()
					+ " || " + e.getMessage());
			resp = "-9~Excepcion Android: " + e.getMessage();
		}

		return resp;
	}

	public static String SWAndroidUpdateCodigoC2DMCliente(Context context,
			String idAndroid, String regid) {
		String resp;

		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://wi081910.ferozo.com/HackathonMatiJesi.asmx";
		final String METHOD_NAME = "SWAndroidUpdateCodigoC2DMUsuario";
		final String SOAP_ACTION = "http://tempuri.org/SWAndroidUpdateCodigoC2DMUsuario";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty("idAndroid", idAndroid);
		request.addProperty("codigoC2DM", regid);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);

		try {
			transporte.call(SOAP_ACTION, envelope);

			SoapPrimitive resultado_xml = (SoapPrimitive) envelope
					.getResponse();
			resp = resultado_xml.toString();
		} catch (Exception e) {
			Log.d(TAG, "Error registro en mi servidor: " + e.getCause()
					+ " || " + e.getMessage());
			resp = "-9~Excepcion Android: " + e.getMessage();
		}

		return resp;
	}

	public static List<Usuario> SWAndroidActualizarRecuperarGeoPosicionUsuario(
			String idAndroid, String latitud, String longitud) {

		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://wi081910.ferozo.com/HackathonMatiJesi.asmx";
		final String METHOD_NAME = "SWAndroidActualizarRecuperarGeoPosicionUsuario";
		final String SOAP_ACTION = "http://tempuri.org/SWAndroidActualizarRecuperarGeoPosicionUsuario";
		Usuario[] usuarios = null;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("a", "matijesi1234.");
		request.addProperty("idAndroid", idAndroid);
		request.addProperty("latitud", latitud);
		request.addProperty("longitud", longitud);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			SoapObject resSoap = (SoapObject) envelope.getResponse();
			usuarios = new Usuario[resSoap.getPropertyCount()];
			for (int i = 0; i < usuarios.length; i++) {
				SoapObject ic = (SoapObject) resSoap.getProperty(i);
				Usuario usuario = new Usuario(ic.getProperty(0)
						.toString(), ic.getProperty(1).toString(), ic.getProperty(2).toString(), ic.getProperty(3).toString(), ic.getProperty(4).toString(), ic.getProperty(5).toString(), ic.getProperty(6).toString(), Double.parseDouble(ic.getProperty(7).toString()), Double.parseDouble(ic.getProperty(8).toString()), ic.getProperty(9).toString());
				usuarios[i] = usuario;
			}
			return Arrays.asList(usuarios);
		} catch (Exception e) {
			return null;
		}
	}

	
}
