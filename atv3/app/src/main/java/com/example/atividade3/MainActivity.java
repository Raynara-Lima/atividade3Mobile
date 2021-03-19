package com.example.atividade3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.atividade3.adapter.AdapterListViewPersonalizada;
import com.example.atividade3.model.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import android.widget.ProgressBar;

import network.CadastrarProduto;
import network.DownloadProdutos;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<Produto> cardapio= new ArrayList<Produto>();;
    int selected;
    AdapterListViewPersonalizada adapter;
    ProgressBar progressBar;


    ListView listViewProdutos;
    DownloadProdutos downloadProdutos;
    CadastrarProduto cadastrarProduto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        selected = -1;
//        cardapio = new ArrayList<Produto>();
//        cardapio.add(new Produto("Vatapá de frango","Acompanha arroz branco e farofinha de mandioca. Vatapá de frando desfiado e finalizado com batata palha.", new BigDecimal(19.90)));
//        cardapio.add(new Produto("Frango com salsa de ervas frescas","Acompanha arroz branco e purê de batatas. Filé de peito de frango em tiras adicnado de molho a base de azeite com ervas frescas.", new BigDecimal(19.90)));

        adapter = new AdapterListViewPersonalizada(cardapio, this);

        listViewProdutos = (ListView) findViewById(R.id.listViewProdutos);

        listViewProdutos.setAdapter(adapter);
        listViewProdutos.setSelector(R.color.selected);


        listViewProdutos.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                selected = position;
            }

        });

        downloadProdutos = new DownloadProdutos( this, listViewProdutos );
        downloadProdutos.start();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cardapio, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.adicionar:
                adicionarProduto();
                break;
            case R.id.editar:
                editarProduto();
                break;
            case R.id.excluir:
                excluirProduto();
                break;

        }
        return true;
    }

    public void adicionarProduto() {
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        startActivityForResult(intent, Constants.REQUEST_ADD);
    }

    public void editarProduto() {
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        Produto produto = cardapio.get(selected);

        intent.putExtra("id", produto.getId());
        intent.putExtra("nome", produto.getNome());
        intent.putExtra("descricao", produto.getDescricao());
        intent.putExtra("preco", produto.getPreco());

        startActivityForResult(intent, Constants.REQUEST_EDIT);
    }

    public void excluirProduto() {
        if (cardapio.size() > 0) {
            cadastrarProduto = new CadastrarProduto( cardapio.get(selected), false, true );
            cadastrarProduto.start();
//            downloadProdutos = new DownloadProdutos( this, listViewProdutos );
//            downloadProdutos.start();
            cardapio.remove(selected);

            adapter.notifyDataSetChanged();

        } else {
            selected = -1;
        }

    }
    public void updateCardapio( ArrayList<Produto> lista ){
//        progressBar.setVisibility( View.INVISIBLE );

//        Produto[] lista = cardapio;
        cardapio.clear();
        cardapio.addAll(lista);

        adapter.notifyDataSetChanged();;
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD) {
            String nome = (String) data.getExtras().get("nome");
            String descricao = (String) data.getExtras().get("descricao");
            String preco = (String) data.getExtras().get("preco");
            int id;
            if(cardapio.isEmpty()) {
                id = 0;
            }else {
                id = cardapio.get(cardapio.size() -1).getId() + 1;
            }
            Produto produto = new Produto(id, nome, descricao, new BigDecimal(preco));
            cadastrarProduto = new CadastrarProduto( produto, false, false );
            cadastrarProduto.start();

            cardapio.add(produto);

            adapter.notifyDataSetChanged();

        } else if (requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD) {
            String nome = (String) data.getExtras().get("nome");
            String descricao = (String) data.getExtras().get("descricao");
            String preco = (String) data.getExtras().get("preco");
            int idEdit = (int) data.getExtras().get("id");

            for (Produto produto : cardapio) {
                if (produto.getId() == idEdit) {
                    produto.setNome(nome);
                    produto.setDescricao(descricao);
                    produto.setPreco(new BigDecimal(preco));
                    cadastrarProduto = new CadastrarProduto( produto, true, false );
                    cadastrarProduto.start();
                }
            }

            adapter.notifyDataSetChanged();
        } else if (resultCode == Constants.RESULT_CANCEL) {
            Toast.makeText(this, "Cancelado",
                    Toast.LENGTH_SHORT).show();
        }
//        downloadProdutos = new DownloadProdutos( this, listViewProdutos );
//        downloadProdutos.start();
    }
}