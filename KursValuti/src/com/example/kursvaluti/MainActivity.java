package com.example.kursvaluti;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.adapter.ValutiAdapter;
import com.example.database.ValutiDb;
import com.example.model.Valuta;
import com.example.service.ValutaService;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;


public class MainActivity extends ListActivity {

	private List<Valuta> currencyList;
	
	private ValutiDb db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.valuti);

		if (savedInstanceState == null) {
			new CurrencyServiceTask().execute();
		} else {
			currencyList = new ArrayList<Valuta>();

			ArrayList<String> shName = savedInstanceState
					.getStringArrayList("short_name");
			ArrayList<String> fuName = savedInstanceState
					.getStringArrayList("full_name");
			ArrayList<String> values = savedInstanceState
					.getStringArrayList("values");
			ArrayList<Integer> images = savedInstanceState
					.getIntegerArrayList("images");

			for (int i = 0; i < shName.size(); i++) {
				currencyList.add(new Valuta(shName.get(i), fuName.get(i),
						values.get(i), images.get(i)));
			}

			ValutiAdapter adapter = new ValutiAdapter(
					MainActivity.this,
					R.layout.rowlayout_valuti, currencyList);

			adapter.notifyDataSetChanged();
			setListAdapter(adapter);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		if(currencyList.isEmpty())
			return;
		
		db.open();
		outState.putBoolean("flag", true);
		ArrayList<String> shName = new ArrayList<String>();
		ArrayList<String> fuName = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<Integer> images = new ArrayList<Integer>();
		for (int i = 0; i < currencyList.size(); i++) {
			shName.add(currencyList.get(i).getShortName());
			fuName.add(currencyList.get(i).getFullNameMac());
			values.add(currencyList.get(i).getAverage());
			images.add(currencyList.get(i).getFlag());
			Valuta v = new Valuta();
			v.setShortName(currencyList.get(i).getShortName());
			v.setFullNameMac(currencyList.get(i).getFullNameMac());
			v.setAverage(currencyList.get(i).getAverage());
			v.setFlag(currencyList.get(i).getFlag());
			db.insert(v);
		}
		db.close();

		outState.putStringArrayList("short_name", shName);
		outState.putStringArrayList("full_name", fuName);
		outState.putStringArrayList("values", values);
		outState.putIntegerArrayList("images", images);

	}

	private class CurrencyServiceTask extends AsyncTask<URL, Integer, String> {

		private ProgressDialog progressDialog;

		@Override
		protected String doInBackground(URL... params) {

			HashMap<String, ArrayList<Valuta>> data = null;
			try {
				data = ValutaService.getExchangeRateNBRM();

				String[] shortNames = new String[] { "EUR", "GBP", "USD",
						"CAD", "AUD", "DKK", "JPY", "NOK", "SEK", "CHF" };

				HashMap<Integer, Integer> images = new HashMap<Integer, Integer>();
				images.put(0, R.drawable.ic_eur);
				images.put(1, R.drawable.ic_gbp);
				images.put(2, R.drawable.ic_usd);
				images.put(3, R.drawable.ic_cad);
				images.put(4, R.drawable.ic_aud);
				images.put(5, R.drawable.ic_dkk);
				images.put(6, R.drawable.ic_jpy);
				images.put(7, R.drawable.ic_nok);
				images.put(8, R.drawable.ic_sek);
				images.put(9, R.drawable.ic_chf);
				images.put(10, R.drawable.ic_launcher);

				String[] names = new String[10];
				String[] values = new String[10];

				currencyList = new ArrayList<Valuta>();

				for (int i = 0; i < 10; i++) {
					names[i] = data.get(shortNames[i]).get(0).getFullNameMac();
					values[i] = data.get(shortNames[i]).get(0).getAverage();
					currencyList.add(new Valuta(shortNames[i], names[i],
							values[i], images.get(i)));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ValutiAdapter adapter = new ValutiAdapter(
					MainActivity.this,
					R.layout.rowlayout_valuti, currencyList);

			adapter.notifyDataSetChanged();
			setListAdapter(adapter);

			if (progressDialog.isShowing())
				progressDialog.dismiss();
		}

	}
}

