package com.developer.barbosa.eventoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemVideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------------------Sem conexao com o cloud----------------------------------------
        /*//final String[] arrayAulas = getResources().getStringArray(R.array.array_aulas);

        final List<ItemVideo> itemVideos = new ArrayList<>();
        itemVideos.add(new ItemVideo("Principais erros", "26/09/2016", "http://"));
        itemVideos.add(new ItemVideo("Video Aula Pratica 1", "28/09/2016", "http://"));
        itemVideos.add(new ItemVideo("Video Aula Pratica 2", "29/09/2016", "http://"));
        itemVideos.add(new ItemVideo("Duvidas Recebidas", "30/09/2016", "http://"));*/
        //-----------------------------------------------------------------------------------

        ListView lista = (ListView) findViewById(R.id.lista);

        //adapter = new ItemVideoAdapter(this, itemVideos);

        adapter = new ItemVideoAdapter(this, new ArrayList<ItemVideo>());

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetalheActivity.class);
                Bundle bundle = new Bundle();
                ItemVideo aula = (ItemVideo) parent.getItemAtPosition(position);
                intent.putExtra("AULA", aula);
                startActivity(intent);
            }
        });

        new EventoTask().execute();

    }

    class EventoTask extends AsyncTask<Void, Void, List<ItemVideo>> {


        @Override
        protected List<ItemVideo> doInBackground(Void... params) {
            //Esta etapa é usada para executar a tarefa em background.

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL("http://private-d88f6f-semanadevandroid.apiary-mock.com/listar");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String linha;
                StringBuffer buffer = new StringBuffer();
                while((linha = reader.readLine()) != null){
                    buffer.append(linha+"\n");

                }

                return JsonUtil.fromJson(buffer.toString());

            }catch(Exception e){
                e.printStackTrace();
                if(urlConnection != null){
                    urlConnection.disconnect();
                }

                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<ItemVideo> listaItens) {
            //O resultado da execução em background é passado para esta passo como um parâmetro.
            adapter.clear();
            adapter.addAll(listaItens);
            adapter.notifyDataSetChanged();
        }



        //---------Conhecimento extras-------------------------------------------------------------------------------------
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Esta passo é usado para configurar a tarefa, por exemplo, mostrando uma barra de progresso na interface do usuario
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //Este método é usado para exibir qualquer forma de progresso na interface do usuário, enquanto a tarefa ainda está em execução.
        }
    }
}
