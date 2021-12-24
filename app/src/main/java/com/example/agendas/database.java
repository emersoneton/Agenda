package com.example.agendas;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.Cursor; //Navegar entre os registros
import android.content.ContextWrapper;
import android.view.View;

import static android.content.Context.MODE_PRIVATE;

public class database {

    static SQLiteDatabase db=null;
    static Cursor cursor;

    static CxMsg msg;

    public static void FecharDB(){
        db.close();
    }

    public static void AbrirBanco(Activity activity){

        try { // Criar o BD se caso não existir ou abrir caso existir
            ContextWrapper cw=new ContextWrapper(activity);
            db=cw.openOrCreateDatabase("bancoAgenda", MODE_PRIVATE, null);
        }catch (Exception ex){
            msg.Mensagem("Erro ao abrir o BD: "+ex, activity);
        }finally {
            Log.v("BancodeDados", "BancoAgenda ABERTO com sucesso!");
        }
    }

    public static void AbrirTabelaContatos(Activity activity){
        try { // Criar Tabela Contatos
            db.execSQL("CREATE TABLE IF NOT EXISTS contatos (id INTEGER PRIMARY KEY, nome TEXT, fone TEXT);");
        }catch (Exception ex){
            msg.Mensagem("Erro ao criar a Tabela Contatos: "+ex,activity);
        }finally {
            Log.v("BancodeDados", "Tabela Contatos CRIADA com sucesso!");
        }
    }

    public static boolean InserirRegistros(Activity activity, String nome, String telefone){
        AbrirBanco(activity);
        try {
            db.execSQL("INSERT INTO contatos (nome, fone) VALUES ('"+nome+"', '"+telefone+"');");
            msg.Mensagem("Registro inserido com sucesso!",activity);
            return true;
        }catch (Exception ex){
            msg.Mensagem("Erro ao inserir Registro: "+ex,activity);
            Log.v("BancodeDados", "Erro ao inserir dados no BD: "+ex);
        }

        FecharDB();
        return false;
    }

    public static Cursor BuscarDados(Activity activity){
        AbrirBanco(activity);
        cursor=db.query( "contatos",
                new String[]{"nome","fone"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst(); //move para o primeiro registro
        return cursor;
    }

}
