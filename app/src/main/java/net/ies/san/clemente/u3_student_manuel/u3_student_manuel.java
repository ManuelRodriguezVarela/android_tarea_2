package net.ies.san.clemente.u3_student_manuel;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class u3_student_manuel extends AppCompatActivity {
    private String text;
    private TextView textoResultado;
    private EditText inputText;
    private CheckBox clearText;
    private RadioButton radioRed;
    private RadioButton radioBlue;
    private Context context;
    private Spinner spinner;
    private ImageView image;
    private static final String[] PROVINCIAS_GALICIA = { "Coruña A", "Lugo", "Pontevedra", "Ourense" };
    private Chronometer chronometer;
    private Switch aSwitch;
    private static final int COUNT_FOR_DESTROY = 15;

    public u3_student_manuel() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = "";
        textoResultado = (TextView) findViewById(R.id.txtView);
        inputText = (EditText) findViewById(R.id.inputTxt);
        clearText = (CheckBox) findViewById(R.id.ckBClear);
        radioRed = (RadioButton) findViewById(R.id.rBRed);
        radioBlue = (RadioButton) findViewById(R.id.rBBlue);
        aSwitch = (Switch) findViewById(R.id.switch1);
        context = this;

        spinner = (Spinner) findViewById(R.id.spinner);
        //Evento del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setToastFromSpinnerSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chronometer = (Chronometer) findViewById(R.id.chrono);
        //Evento cronómetro
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                manageChronometer();
            }
        });
    }

    //acción botton añadir / limpiar
    public void addClearText(View view) {
        boolean clear = clearText.isChecked();
        if(!clear) {
            setText();
            text = getViewText().concat(" ").concat(text);
        } else {
            text = "";
        }
        setViewText();
    }

    //añadir texto al text view
    public void setViewText() {
        textoResultado.setText(text);
    }

    //añadir texto al text view
    public String getViewText() {
        return textoResultado.getText().toString();
    }

    //get inputext
    public void setText() {
        String input = inputText.getText().toString();
        if (input != null) {
            text = input;
        }
    }

    //setear color del viewtext
    public void setTextColor(View view) {
        if(radioRed.isChecked()) {
            textoResultado.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else if (radioBlue.isChecked()) {
            textoResultado.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
    }

    public void setToastFromSpinnerSelected() {
        String msm = getString(R.string.text_toast_no_gal);
        String provincia = spinner.getSelectedItem().toString();
        List<String> provinciasGalicia = Arrays.asList(PROVINCIAS_GALICIA);
        if (provinciasGalicia.contains(provincia)) {
            msm = getString(R.string.text_toast_gal);
        }
        throwToast(msm);
    }

    //lanzar toast provincias
    public void throwToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    //Toast de tag de imagen
    public void getTag(View view) {
        image = (ImageView) findViewById(R.id.image);
        throwToast(image.getTag().toString());
    }

    public void startStopChronometer(View view) {
        if(aSwitch.isChecked()){
            aSwitch.setText(getString(R.string.text_stop));
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        } else {
            aSwitch.setText(getString(R.string.text_start));
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.stop();
        }
    }

    public void manageChronometer() {
        long tiempoPasado = SystemClock.elapsedRealtime() - chronometer.getBase();
        int tiempoSeg = (int) tiempoPasado / 1000;
        if (tiempoSeg == COUNT_FOR_DESTROY) finish();
    }
}