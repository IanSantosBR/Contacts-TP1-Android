package iansantos.contacts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import iansantos.contacts.R;
import iansantos.contacts.adapter.ContactAdapter;
import iansantos.contacts.model.Contact;
import iansantos.contacts.utils.RecyclerItemClickListener;

public class ContactList extends AppCompatActivity {

    public static List<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        setTitle("Lista de Contatos");

        contactList = readFile();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ContactAdapter adapter = new ContactAdapter(contactList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Contact contact = contactList.get(position);
                                Intent intent = new Intent(ContactList.this, ContactInfo.class);
                                intent.putExtra("name", contact.getName());
                                intent.putExtra("phone", contact.getPhone());
                                intent.putExtra("email", contact.getEmail());
                                intent.putExtra("city", contact.getCity());
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            }
                        }
                )
        );
    }

    public List<Contact> readFile() {

        List<Contact> contactList = new ArrayList<>();
        File inputFile = new File(getFilesDir(), MainActivity.filename);
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.equals("#")) {
                    String name = bufferedReader.readLine();
                    String phone = bufferedReader.readLine();
                    String email = bufferedReader.readLine();
                    String city = bufferedReader.readLine();
                    contactList.add(new Contact(name, phone, email, city));
                }
                line = bufferedReader.readLine();
            }
        } catch (Exception ex) {
            Log.d("Exception", ex.toString());
        }
        return contactList;
    }
}
