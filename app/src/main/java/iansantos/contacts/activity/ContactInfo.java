package iansantos.contacts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import iansantos.contacts.R;

public class ContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        setTitle("Informações de Contato");

        TextView name = findViewById(R.id.name);
        TextView phone = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);
        TextView city = findViewById(R.id.city);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String nameText = (String) bundle.get("name");
        String phoneText = (String) bundle.get("phone");
        String emailText = (String) bundle.get("email");
        String cityText = (String) bundle.get("city");

        name.setText(nameText);
        phone.setText(phoneText);
        email.setText(emailText);
        city.setText(cityText);
    }
}
