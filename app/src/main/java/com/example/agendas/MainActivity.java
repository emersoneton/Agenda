package com.example.agendas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase; //Banco de Dados
import android.database.Cursor; //Navegar entre os registros
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText etNome, etTelefone;
    Button btnGravar, btnConsultar, btnFechar;

    CxMsg msg;

    database bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome=(EditText)findViewById(R.id.etNome);
        etTelefone=(EditText)findViewById(R.id.etTelefone);

        btnGravar=(Button)findViewById(R.id.btnGravar);
        btnConsultar=(Button)findViewById(R.id.btnConsultar);
        btnFechar=(Button)findViewById(R.id.btnFechar);

        bd.AbrirBanco(this);           //Cria ou Abre o BD
        bd.AbrirTabelaContatos(this); //Cria a Tabela de contatos caso não exista
        bd.FecharDB();                      //Fechar conexão com o Banco de Dados
    }

    public void InserirRegistros(View view){

        String nome, telefone;
        nome = etNome.getText().toString();
        telefone = etTelefone.getText().toString();

        if(nome.equals("") || telefone.equals("")){
            msg.Mensagem("Nomes não podem estar vazios",this);
            return;
        }

        if(bd.InserirRegistros(this, nome, telefone)){
            etNome.setText("");
            etTelefone.setText("");
        }

    }

    public void AbrirTelaConsulta(View view){
        Intent intentTelaConsulta = new Intent(this, TelaConsulta.class);
        startActivity(intentTelaConsulta);
    }

    public void Fechar(View view){
        this.finish();
    }

}