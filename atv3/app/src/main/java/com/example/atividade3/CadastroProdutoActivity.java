package com.example.atividade3;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

public class CadastroProdutoActivity extends AppCompatActivity {
    EditText editTextNome;
    EditText editTextDescricao;
    EditText editTextPreco;

    boolean edit;
    int idProdutoEditar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextNome = findViewById( R.id.editTextNome );
        editTextDescricao = findViewById( R.id.editTextDescricao );
        editTextPreco = findViewById( R.id.editTextPreco );

        edit = false;

        if( getIntent().getExtras() != null ){
            String nome = (String) getIntent().getExtras().get( "nome" );
            String descricao = (String) getIntent().getExtras().get( "descricao" );
            String preco =  getIntent().getExtras().get( "preco" ).toString();
            idProdutoEditar = (int) getIntent().getExtras().get( "id" );
            editTextNome.setText(nome);
            editTextDescricao.setText(descricao);
            editTextPreco.setText(preco);

            edit = true;
        }

    }

    public void cancelar( View view ){
        setResult( Constants.RESULT_CANCEL );
        finish();
    }

    public void adicionar( View view ){
        Intent intent = new Intent();

        String nome = editTextNome.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String preco = editTextPreco.getText().toString();

        intent.putExtra("nome", nome );
        intent.putExtra("descricao", descricao );
        intent.putExtra("preco", preco );
        if(edit) {
            intent.putExtra("id", idProdutoEditar);
        }
        setResult( Constants.RESULT_ADD, intent );
        finish();
    }

}