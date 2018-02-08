package com.example.thiag.calc5132177;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CalculadoraActivity extends AppCompatActivity
{
    EditText valor1;
    EditText valor2;

    Button btnSum;
    Button btnSub;
    Button btnMul;
    Button btnDiv;

    Button btnHistory;

    TextView txtRes;

    double num1;
    double num2;
    double res;

    final String caminhoDiretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + "/arqs/";

    File diretorio;
    File fileExt;
    String diretorioApp;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        valor1 = (EditText)findViewById(R.id.edtValor1);
        valor2 = (EditText)findViewById(R.id.edtValor2);

        btnSum = (Button) findViewById(R.id.btnSum);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        btnHistory = (Button) findViewById(R.id.btnHistory);

        txtRes = (TextView)findViewById(R.id.txtRes);

        context = this;

        if(validaHistorico())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage("Você quer restaurar o histórico antigo?");

            builder.setPositiveButton("Restaurar", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(context, "restaurado com sucesso", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Não restaurar", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    // define o diretório
                    diretorioApp = caminhoDiretorio;
                    diretorio = new File(diretorioApp);
                    diretorio.mkdirs();

                    // define o arquivo
                    fileExt = new File(diretorioApp, "Dreco.txt");
                    fileExt.getParentFile().mkdir();

                    // salva
                    FileOutputStream fosExt;
                    try
                    {
                        fosExt = new FileOutputStream(fileExt);
                        fosExt.write("".getBytes());
                        fosExt.close();
                        Toast.makeText(context, "histórico apagado", Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {

                    }
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }




        btnSum.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    // Fará a Soma
                    num1 = Double.parseDouble(valor1.getText().toString());
                    num2 = Double.parseDouble(valor2.getText().toString());

                    res = num1 + num2;

                    txtRes.setText("resultado: " + res);

                    salvaOperacao(num1, num2, res, "+");
                }
                catch(Exception e)
                {
                    Toast.makeText(context, "Insira um número válido!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    // Fará a Soma
                    num1 = Double.parseDouble(valor1.getText().toString());
                    num2 = Double.parseDouble(valor2.getText().toString());

                    res = num1 - num2;

                    txtRes.setText("resultado: " + res);

                    salvaOperacao(num1, num2, res, "-");
                }
                catch(Exception e)
                {
                    Toast.makeText(context, "Insira um número válido!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    // Fará a Soma
                    num1 = Double.parseDouble(valor1.getText().toString());
                    num2 = Double.parseDouble(valor2.getText().toString());

                    res = num1 * num2;

                    txtRes.setText("resultado: " + res);

                    salvaOperacao(num1, num2, res, "*");
                }
                catch(Exception e)
                {
                    Toast.makeText(context, "Insira um número válido!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    num1 = Double.parseDouble(valor1.getText().toString());
                    num2 = Double.parseDouble(valor2.getText().toString());

                    try
                    {
                        res = num1 / num2;
                        txtRes.setText("resultado: " + res);

                        salvaOperacao(num1, num2, res, "/");
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(CalculadoraActivity.this, "Número dividido por zero", Toast.LENGTH_SHORT).show();
                        txtRes.setText("resultado: 0.0");
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(context, "Insira um número válido!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //troca de tela
                Intent intent = new Intent(context, HistoricoActivity.class);
                startActivity(intent);

            }
        });

    }

    private void salvaOperacao(double num1, double num2, double res, String operacao)
    {
        String operacoes;

        // define o diretório
        diretorioApp = caminhoDiretorio;
        diretorio = new File(diretorioApp);
        diretorio.mkdirs();

        operacoes = mostraHistorico() + num1 + " " + operacao + " " + num2 + " = " + res + " -o0o-\n";

        // define o arquivo
        fileExt = new File(diretorioApp, "Dreco.txt");
        fileExt.getParentFile().mkdir();

        // salva
        FileOutputStream fosExt;
        try
        {
            fosExt = new FileOutputStream(fileExt);
            fosExt.write(operacoes.getBytes());
            fosExt.close();
        }
        catch (IOException e)
        {
            // Se não possui permissão
            if (ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                }
                else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
            } else {
                // Tudo OK, podemos prosseguir.
                Toast.makeText(context, "Por favor forneça a permissão de salvar.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String mostraHistorico()
    {
        File arq;
        String texto = "";
        String lstrlinha;

        try
        {
            arq = new File(caminhoDiretorio, "Dreco.txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null)
            {
                if (!texto.equals(""))
                {
                    texto = texto + "\n";
                }
                texto = texto + lstrlinha;
            }
        }
        catch (Exception e)
        {
            texto = "";
        }

        return texto;
    }

    private boolean validaHistorico()
    {
        String historico = mostraHistorico();

        if(historico == ""){
            return false;
        }else{
            return true;
        }
    }

}