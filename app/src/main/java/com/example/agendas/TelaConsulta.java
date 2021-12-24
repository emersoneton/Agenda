package com.example.agendas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaConsulta extends AppCompatActivity {

    EditText etNome, etTelefone;
    Button btnAnterior, btnProximo, btnVoltar;

    Cursor cursor;

    CxMsg msg;

    database bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consulta);

        etNome=(EditText) findViewById(R.id.etNomeConsulta);
        etTelefone=(EditText) findViewById(R.id.etTelefoneConsulta);

        btnAnterior=(Button) findViewById(R.id.btnAnteriorConsulta);
        btnProximo=(Button) findViewById(R.id.btnProximoConsulta);
        btnVoltar=(Button) findViewById(R.id.btnVoltarConsulta);

        cursor = bd.BuscarDados(this);
        if(cursor.getCount() != 0){
            MostrarDados();
        }else{
            msg.Mensagem("Nenhum registro encontrado!", this);
        }
    }



    public void MostrarDados(){

        etNome.setText(cursor.getString(cursor.getColumnIndex("nome")));
        etTelefone.setText(cursor.getString(cursor.getColumnIndex("fone")));

    }

    public void RegistroProximo(View view){
        try {
            cursor.moveToNext();
            MostrarDados();
        }catch (Exception ex){
            if(cursor.isAfterLast()){
                msg.Mensagem("Não existem mais registros",this);
            }else{
                msg.Mensagem("Erro ao navegar pelos registros",this);
            }
        }
    }

    public void RegistroAnterior(View view){
        try {
            cursor.moveToPrevious();
            MostrarDados();
        }catch (Exception ex){
            if(cursor.isBeforeFirst()){
                msg.Mensagem("Não existem mais registros",this);
            }else{
                msg.Mensagem("Erro ao navegar pelos registros", this);
            }
        }

    }

    public void Voltar(View view){
        this.finish();
    }
}