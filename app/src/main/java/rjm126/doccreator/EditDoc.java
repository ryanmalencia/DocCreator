package rjm126.doccreator;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EditDoc extends BlankDoc {
    StringBuilder doctext = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doc);
        filename = getIntent().getStringExtra("myfile");
        System.out.println(filename);
        setTitle(filename);
        openFile();
    }

    public void openFile()
    {
        try {
            InputStream fis = openFileInput(filename + ".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader buffreader = new BufferedReader(isr);
            String line;
            int i = 0;
            while ((line = buffreader.readLine())!=null)
            {
                if(i==0)
                    doctext.append(line);
                else {
                    doctext.append("\n");
                    doctext.append(line);
                }
                i++;
            }
            buffreader.close();
            isr.close();
            fis.close();
            EditText thetext = (EditText) findViewById(R.id.editText);
            thetext.setText(doctext.toString());
            original = thetext.getText().toString();
        }catch(IOException e)
        {
            Context context = getApplicationContext();
            CharSequence text = "Doc Not Opened";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }




}
