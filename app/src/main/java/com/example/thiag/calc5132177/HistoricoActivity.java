package com.example.thiag.calc5132177;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HistoricoActivity extends AppCompatActivity
{

    final String caminhoDiretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + "/arqs/";
    TextView dados;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Calculadora Della Noce");
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dados = (TextView) findViewById(R.id.txtDados);

        File arq;
        String lstrlinha;

        try
        {
            arq = new File(caminhoDiretorio, "Dreco.txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null)
            {
                if (!lstrlinha.equals(""))
                {
                    dados.setText("\n");
                }

                String[] dadosAux = lstrlinha.split("-o0o-");

                for (int i = 0; i < dadosAux.length; i++)
                {
                    dados.append(dadosAux[i]);
                    dados.append("\n");
                }
            }
        }
        catch (Exception e)
        {
            dados.setText("Você não tem histórico salvo.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
