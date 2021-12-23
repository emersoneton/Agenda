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

    SQLiteDatabase db=null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consulta);

        etNome=(EditText) findViewById(R.id.etNomeConsulta);
        etTelefone=(EditText) findViewById(R.id.etTelefoneConsulta);

        btnAnterior=(Button) findViewById(R.id.btnAnteriorConsulta);
        btnProximo=(Button) findViewById(R.id.btnProximoConsulta);
        btnVoltar=(Button) findViewById(R.id.btnVoltarConsulta);

        AbrirBanco();           //Cria ou Abre o BD
        FecharDB();            //Fechar conexão com o Banco de Dados
        BuscarDados();
    }

    public void FecharDB(){
        db.close();
    }

    public void AbrirBanco(){

        try { // Criar o BD se caso não existir ou abrir caso existir
            db=openOrCreateDatabase("bancoAgenda", MODE_PRIVATE, null);
        }catch (Exception ex){
            Msg("Erro ao abrir o BD: "+ex);
        }finally {
            Log.v("BancodeDados", "BancoAgenda ABERTO com sucesso!");
        }
    }

    public void BuscarDados(){
        AbrirBanco();
        cursor=db.query( "contatos",
                new String[]{"nome","fone"},
                null,
                null,
                null,
                null,
                null
        );
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            MostrarDados();
        }else{
            Msg("Nenhum registro encontrado!");
        }
    }

    public void MostrarDados(){

        etNome.setText(cursor.getString(cursor.getColumnIndex("nome")));
        etTelefone.setText(cursor.getString(cursor.getColumnIndex("fone")));

    }

    public void RegistroProximo(View view){
        cursor.moveToNext();
        MostrarDados();
    }

    public void RegistroAnterior(View view){
        cursor.moveToPrevious();
        MostrarDados();
    }

    public void Msg(String txt){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setMessage(txt);
        adb.setNeutralButton("OK",null);
        adb.show();
    }

    public void Voltar(View view){
        this.finish();
    }
}