package id.go.kominfo.andi.datapersistencedemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //definisikan dan deklarasikan object ArrayList ls
        List<String> ls = new ArrayList<>();

        //definisikan dan deklarasikan object ListView
        ListView listView = findViewById(R.id.listview);

        //buat sebuah adapter dengan layout simple_list_item_1 yang disediakan android
        //tampilkan data dalam text1 dengan datanya ls
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, ls);

        //tentukan adapter untuk listview
        listView.setAdapter(adapter);

        //definisikan dan deklarasikan object FloatingActionButton fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v->{
            View inputLayout = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.input_layout, null);

            EditText etName = inputLayout.findViewById(R.id.et_name);

            new AlertDialog.Builder(this)
                    .setTitle("Masukan Nama Teman")
                    .setView(inputLayout)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (Objects.requireNonNull(etName.getText()).toString().isEmpty()) {
                            Toast.makeText(this, "Nama Tidak Boleh Kosong",
                                    Toast.LENGTH_SHORT).show();

                            return;
                        }

                        ls.add(etName.getText().toString());
                        adapter.notifyDataSetChanged();
                    })
                    .show();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp = getSharedPreferences("romandb", MODE_PRIVATE);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setTitle("Welcome back "+
                sp.getString("username", null) + " !");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mi_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm to Logout")
                    .setMessage("Logout from app?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        SharedPreferences sp =
                                getSharedPreferences("romandb", MODE_PRIVATE);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("loginStatus", false);
                        editor.apply();

                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

}