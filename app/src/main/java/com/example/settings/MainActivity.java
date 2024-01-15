package com.example.settings;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        SharedPreferences pref = getDefaultSharedPreferences(this);

        logPref(pref);
        printPrefStored(pref);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean res = super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_borrar) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.ad_borrar_titulo)
                    .setMessage(R.string.ad_borrar_mensaje)
                    .setCancelable(false)
                    .setNegativeButton(R.string.ad_cancelar, null)
                    .setPositiveButton(R.string.ad_aceptar, (dialog, which) -> {
                        SharedPreferences pref = getDefaultSharedPreferences(this);
                        pref.edit().clear().commit();
                    })
                    .create()
                    .show();
            printPrefStored(getDefaultSharedPreferences(this));
            res = true;
        } else if (item.getItemId() == R.id.menu_config) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
            res = true;
        }

        return res;
    }

    private void printPrefStored(SharedPreferences pref) {

        TextView lblDescansar = findViewById(R.id.lblDescansar);
        String descansarValue = pref.getString(getString(R.string.pref_key_descansar), "" + getResources().getInteger(R.integer.descansar_default));
        String descansar = getString(R.string.pref_sum_descansar) + ": " + descansarValue + " hora(s)";
        lblDescansar.setText(descansar);

        TextView lblAlarma = findViewById(R.id.lblAlarma);
        String alarmaValue = pref.getString(getString(R.string.pref_key_alarma), getString(R.string.default_alarma));
        String alarma = getString(R.string.pref_tit_alarma) + ": " + alarmaValue;
        lblAlarma.setText(alarma);

        TextView lblPantalla = findViewById(R.id.lblPantalla);
        String pantallaValue = pref.getString(getString(R.string.pref_key_pantalla), getResources().getInteger(R.integer.pantalla_default) + "");
        String pantalla = getString(R.string.pref_tit_pantalla) + ": " + pantallaValue;
        lblPantalla.setText(pantalla);

        TextView lblFuente = findViewById(R.id.lblFuente);
        int fuenteValue = pref.getInt(getString(R.string.pref_key_fuente), getResources().getInteger(R.integer.fuente_default));
        String fuente = getString(R.string.pref_tit_fuente) + ": " + pantallaValue;
        lblFuente.setText(fuente);

        TextView lblModoOscuro = findViewById(R.id.lblModoOscuro);
        boolean modoOscuroValue = pref.getBoolean(getString(R.string.pref_key_modo_oscuro), false);
        String modoOscuro = getString(R.string.pref_tit_modo_oscuro) + ": " + (modoOscuroValue ? "activado" : "desactivado");
        lblModoOscuro.setText(modoOscuro);

        TextView lblDatos = findViewById(R.id.lblDatos);
        Set<String> defaultDatos = new ArraySet<>();
        Set<String> datosValue = pref.getStringSet(getString(R.string.pref_key_datos), defaultDatos);
        String datos = getString(R.string.pref_tit_datos) + ": " + String.join(", ", datosValue);
        lblDatos.setText(datos);

        TextView lblCalidad = findViewById(R.id.lblCalidad);
        Set<String> defaultCalidad = new ArraySet<>();
        Set<String> calidadValue = pref.getStringSet(getString(R.string.pref_key_calidad), defaultCalidad);
        String calidad = getString(R.string.pref_tit_calidad) + ": " + String.join(", ", calidadValue);
        lblCalidad.setText(calidad);

        TextView lblModoRestrictivo = findViewById(R.id.lblModoRestrictivo);
        boolean modoRestrictivoValue = pref.getBoolean(getString(R.string.pref_key_modo_restrictivo), true);
        String modoRestrictivo = getString(R.string.pref_tit_modo_restrictivo) + ": " + (modoRestrictivoValue ? "activado" : "desactivado");
        lblModoRestrictivo.setText(modoRestrictivo);

        TextView lblEstadisticas = findViewById(R.id.lblEstadisticas);
        boolean estadisticasValue = pref.getBoolean(getString(R.string.pref_key_estadisticas), true);
        String estadisticas = getString(R.string.pref_tit_estadisticas) + ": " + (estadisticasValue ? "activado" : "desactivado");
        lblEstadisticas.setText(estadisticas);
        
    }

    private static void logPref(SharedPreferences pref) {
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("PMDM", entry.getKey() + ": " + entry.getValue().toString());
        }
    }
}