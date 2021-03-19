package com.example.atividade3.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atividade3.R;
import com.example.atividade3.model.Produto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListViewPersonalizada extends BaseAdapter {

    private final List<Produto> produtos;
    //    private final Activity act;
    private final Context c;

    public AdapterListViewPersonalizada(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.c = context;
        ;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Produto getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(c).inflate(R.layout.layout_list, parent, false);
        Produto produto = produtos.get(position);

        TextView nome = (TextView) convertView.findViewById(R.id.lista_produtos_personalizada_nome);
        TextView descricao = (TextView) convertView.findViewById(R.id.lista_produtos_personalizada_descricao);
        TextView preco = (TextView) convertView.findViewById(R.id.lista_produtos_personalizada_preco);

        nome.setText(produto.getNome());
        descricao.setText(produto.getDescricao());
        Locale ptBr = new Locale("pt", "BR");
        preco.setText(NumberFormat.getCurrencyInstance(ptBr).format(produto.getPreco()));


        return convertView;

    }
}