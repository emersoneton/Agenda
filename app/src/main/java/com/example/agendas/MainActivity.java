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

    SQLiteDatabase db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome=(EditText)findViewById(R.id.etNome);
        etTelefone=(EditText)findViewById(R.id.etTelefone);

        btnGravar=(Button)findViewById(R.id.btnGravar);
        btnConsultar=(Button)findViewById(R.id.btnConsultar);
        btnFechar=(Button)findViewById(R.id.btnFechar);

        AbrirBanco();           //Cria ou Abre o BD
        AbrirTabelaContatos(); //Cria a Tabela de contatos caso não exista
        FecharDB();            //Fechar conexão com o Banco de Dados
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

    public void AbrirTabelaContatos(){
        try { // Criar Tabela Contatos
            db.execSQL("CREATE TABLE IF NOT EXISTS contatos (id INTEGER PRIMARY KEY, nome TEXT, fone TEXT);");
        }catch (Exception ex){
            Msg("Erro ao criar a Tabela Contatos: "+ex);
        }finally {
            Log.v("BancodeDados", "Tabela Contatos CRIADA com sucesso!");
        }
    }

    public void InserirRegistros(View view){

        String nome, telefone;
        nome = etNome.getText().toString();
        telefone = etTelefone.getText().toString();

        if(nome.equals("") || telefone.equals("")){
            Msg("Nomes não podem estar vazios");
            return;
        }

        AbrirBanco();
        try {
            db.execSQL("INSERT INTO contatos (nome, fone) VALUES ('"+nome+"', '"+telefone+"');");
            Msg("Registro inserido com sucesso!");
            etNome.setText("");
            etTelefone.setText("");
        }catch (Exception ex){
            Msg("Erro ao inserir Registro: "+ex);
            Log.v("BancodeDados", "Erro ao inserir dados no BD: "+ex);
        }

        FecharDB();

    }

    public void Msg(String txt){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setMessage(txt);
        adb.setNeutralButton("OK",null);
        adb.show();
    }

    public void AbrirTelaConsulta(View view){
        Intent intentTelaConsulta = new Intent(this, TelaConsulta.class);
        startActivity(intentTelaConsulta);
    }

    public void Fechar(View view){
        this.finish();
        this.onDestroy();
    }

}