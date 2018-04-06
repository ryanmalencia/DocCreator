package rjm126.doccreator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class showDocs extends Activity {
    String name[];
    Button [] myButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_docs);
        update();
    }

    private void update() {
        LinearLayout myLayout = findViewById(R.id.mylayout);
        myLayout.removeAllViews();
        String path = this.getFilesDir().toString();
        File f = new File(path);
        File [] files = f.listFiles();

        for(int i=files.length-1;i>=0;i--) {
            myButtons = new Button[files.length];
            File file = files[i];
            String filePath = file.getName();
            if(filePath.endsWith(".txt")) {
                name = file.getName().split("\\.");
                myButtons[i] = new Button(this);
                myButtons[i].setText(name[0]);
                myButtons[i].setTextSize(25);
                myButtons[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        editDoc(((Button) v).getText().toString());
                        finish();
                    }
                });
                myButtons[i].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDeleteOption(v);
                        return true;
                    }
                });
                myLayout.addView(myButtons[i]);
            }
        }
    }

    public void newDoc(View view) {
        Intent intent = new Intent(getApplicationContext(),BlankDoc.class);
        startActivity(intent);
    }

    public void editDoc(String thefile) {
        Intent intent = new Intent(this,EditDoc.class);
        intent.putExtra("myfile",thefile);
        startActivity(intent);
        finish();
    }

    public void showDeleteOption(View v) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        final Button clicked = (Button) v;
        final TextView text = new TextView(this);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(25);
        text.setText(R.string.delete_option);
        adb.setTitle("Delete?");
        adb.setView(text);
        adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File thefile = new File(getBaseContext().getFilesDir().toString() + "/" + clicked.getText() + ".txt");
                boolean deleted = thefile.delete();
                if(!deleted)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Doc not deleted";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                update();
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.show();
    }
}
