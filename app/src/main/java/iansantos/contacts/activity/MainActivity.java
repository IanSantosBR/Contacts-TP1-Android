package iansantos.contacts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import iansantos.contacts.R;
import iansantos.contacts.model.Contact;

public class MainActivity extends AppCompatActivity {

    public static final String filename = "contacts.txt";
    EditText name;
    EditText phone;
    EditText email;
    EditText city;
    ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contatos");

        viewGroup = findViewById(R.id.main_activity);
        name = findViewById(R.id.name_editText);
        phone = findViewById(R.id.phone_editText);
        email = findViewById(R.id.email_editText);
        city = findViewById(R.id.city_editText);
    }

    public void showContacts(View view) {

        File file = new File(getFilesDir(), filename);
        if (!file.exists()) {
            Snackbar.make(findViewById(R.id.main_activity),
                    "A lista de contatos está vazia.", Snackbar.LENGTH_LONG).show();
            viewGroup.requestFocus();
            hideKeyboard();
        } else {
            Intent intent = new Intent(this, ContactList.class);
            startActivity(intent);
        }
    }

    public void saveContact(View view) {

        if (name.getText().toString().trim().length() == 0 || phone.getText().toString().trim().length() == 0 ||
                email.getText().toString().trim().length() == 0 || city.getText().toString().trim().length() == 0) {
            Snackbar.make(findViewById(R.id.main_activity), "Nenhum campo pode estar vazio.", Snackbar.LENGTH_SHORT)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            viewGroup.requestFocus();
            hideKeyboard();
        } else {
            Contact contact = new Contact(name.getText().toString(), phone.getText().toString(),
                    email.getText().toString(), city.getText().toString());
            File outputFile = new File(getFilesDir(), filename);
            try {
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }
                FileOutputStream outputStream = new FileOutputStream(outputFile, true);
                outputStream.write("#\n".getBytes());
                outputStream.write(contact.toString().getBytes());
                outputStream.close();
                ContactList.contactList.add(contact);
                Toast.makeText(this, "O contato foi adicionado!", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Log.d("Exception", ex.toString());
            }
            clear(view);
        }
    }

    public void clear(View v) {

        name.getText().clear();
        phone.getText().clear();
        email.getText().clear();
        city.getText().clear();
        viewGroup.requestFocus();
        hideKeyboard();
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception ex){
            Log.d("Exception", ex.toString());
        }
    }
}
