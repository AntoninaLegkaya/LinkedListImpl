package com.dbbest.linkedlistimpl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputField;
    private EditText inputIndexField;
    private TextView resultField;
    private TextView resultGetField;
    private AlertDialog alert;
    private LinkedListImpl<String> list = new LinkedListImpl<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButtonByIndex = findViewById(R.id.btn_add_by_index);
        Button setButtonByIndex = findViewById(R.id.btn_set_by_index);

        Button addButtonFirst = findViewById(R.id.btn_add_first);
        Button addButtonLast = findViewById(R.id.btn_add_last);
        Button getButtonByIndex = findViewById(R.id.btn_get);
        Button removeByIndexButton = findViewById(R.id.btn_remove_by_index);
        Button removeButton = findViewById(R.id.btn_remove);

        addButtonByIndex.setOnClickListener(this);
        setButtonByIndex.setOnClickListener(this);

        addButtonFirst.setOnClickListener(this);
        addButtonLast.setOnClickListener(this);

        getButtonByIndex.setOnClickListener(this);

        removeByIndexButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);

        inputField = findViewById(R.id.text_entry);
        inputIndexField = findViewById(R.id.text_index);

        resultField = findViewById(R.id.text_result);
        resultGetField = findViewById(R.id.text_get_result);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Ошибка!")
                .setMessage("Неверно введен index!")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alert = builder.create();


    }

    @Override
    public void onClick(View v) throws IndexOutOfBoundsException {
        String result = "";
        resultGetField.setText("");
        try {
            if (v.getId() == R.id.btn_add_by_index) {

                if (inputIndexField.getText() != null & inputIndexField.getText().length() > 0) {
                    list.add(Integer.valueOf(inputIndexField.getText().toString()), inputField.getText
                            ().toString());
                }
            }

            if (v.getId() == R.id.btn_set_by_index) {
                if (inputIndexField.getText() != null & inputIndexField.getText().length() > 0) {
                    list.set(Integer.valueOf(inputIndexField.getText().toString()), inputField.getText
                            ().toString());
                }
            }

            if (v.getId() == R.id.btn_add_first) {

                list.addFirst(inputField.getText().toString());
            }
            if (v.getId() == R.id.btn_add_last) {
                list.addLast(inputField.getText().toString());
            }
            if (v.getId() == R.id.btn_get) {
                if (inputIndexField.getText() != null & inputIndexField.getText().length() > 0) {
                    String res = list.get(Integer.valueOf(inputIndexField.getText().toString()));
                    if (res != null) {
                        resultGetField.setText(res);
                    }
                }

            }
            if (v.getId() == R.id.btn_remove_by_index) {
                if (inputIndexField.getText() != null & inputIndexField.getText().length() > 0) {
                    list.remove(Integer.valueOf(inputIndexField.getText().toString()).intValue());
                }

            }

            if (v.getId() == R.id.btn_remove) {
                if (inputField.getText() != null && inputField.getText().length() > 0) {
                    list.remove(inputField.getText().toString());
                }
            }
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                result = result + " " + iterator.next();
            }
            resultField.setText(result);
        } catch (IndexOutOfBoundsException e) {
            alert.show();
        }
    }
}
