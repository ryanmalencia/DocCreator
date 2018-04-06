package rjm126.doccreator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BlankDoc extends AppCompatActivity {
    String original;
    String filename;
    static Uri path;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_doc);
        EditText theText = (EditText) findViewById(R.id.editText);
        original = theText.getText().toString();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e)
        {
            System.out.println("Error");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeDoc();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void saveFile()
    {
        EditText theText = (EditText) findViewById(R.id.editText);
        String docData = theText.getText().toString();

        try {
            System.out.println(filename);
            FileOutputStream fos = openFileOutput(filename + ".txt",MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            System.out.println(docData);
            osw.write(docData);
            osw.close();
        } catch (IOException e) {
            Context context = getApplicationContext();
            CharSequence text = "Doc Not Saved";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void saveDoc(MenuItem item) {
        if(filename == null) {
            AlertDialog.Builder Builder = new AlertDialog.Builder(this);
            Builder.setTitle("Save");
            final EditText input = new EditText(this);
            input.setInputType(1);
            Builder.setView(input);
            Builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    filename = input.getText().toString();
                    saveFile();
                }
            });

            Builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            Builder.show();
        }
        else
            saveFile();
    }

    public void saveAs(MenuItem item) {
        if(filename == null)
            saveDoc(item);
        else
        {
            AlertDialog.Builder Builder = new AlertDialog.Builder(this);
            Builder.setTitle("Save");
            final EditText input = new EditText(this);
            input.setInputType(1);
            Builder.setView(input);
            Builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    silentDelete();
                    filename = input.getText().toString();
                    saveFile();
                }
            });

            Builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            Builder.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BlankDoc Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://rjm126.doccreator/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BlankDoc Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://rjm126.doccreator/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void openDoc(MenuItem item)
    {
        if(filename!=null)
            saveFile();
        Intent intent = new Intent(this,showDocs.class);
        startActivity(intent);
        finish();
    }

    public void closeDoc(MenuItem item)
    {

        if(filename!=null)
            saveFile();
        Intent intent = new Intent(this,showDocs.class);
        startActivity(intent);
        finish();
    }

    public void closeDoc()
    {

        if(filename!=null)
            saveFile();
        Intent intent = new Intent(this,showDocs.class);
        startActivity(intent);
        finish();
    }

    public void newDoc(MenuItem item)
    {
        if(filename!=null)
            saveFile();
        Intent intent = new Intent(this, BlankDoc.class);
        startActivity(intent);
        finish();
    }

    public void deleteDoc(MenuItem item)
    {
        if(filename!=null)
            showDeleteOption();
    }

    public void silentDelete()
    {
        File thefile = new File(getBaseContext().getFilesDir().toString() + "/" + filename + ".txt");
        boolean deleted = thefile.delete();
        if(!deleted)
        {
            Context context = getApplicationContext();
            CharSequence text = "Doc not deleted";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void showDeleteOption()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        final TextView text = new TextView(this);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(25);
        text.setText(R.string.delete_option);
        adb.setTitle("Delete");
        adb.setView(text);
        adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File the_file = new File(getBaseContext().getFilesDir().toString() + "/" + filename + ".txt");
                boolean deleted = the_file.delete();
                if(!deleted)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Doc not deleted";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                Intent intent = new Intent(getBaseContext(), showDocs.class);
                startActivity(intent);
                finish();
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

    public void Send(MenuItem item) {
        if(filename == null)
        {
            Context context = getApplicationContext();
            CharSequence text = "Save before Sending";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            saveDoc(item);
            sendIt();
        }
    }

    public void sendIt()
    {
        String send_file = getBaseContext().getFilesDir().toString() + "/" + filename + ".txt";
        path = Uri.fromFile(new File(send_file));
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        System.out.println(path.toString());
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, filename);
        startActivity(Intent.createChooser(emailIntent, "Send"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(filename!=null) {
                saveFile();
            }
            else {
                saveDoc(null);
            }
            Intent intent = new Intent(this,showDocs.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
