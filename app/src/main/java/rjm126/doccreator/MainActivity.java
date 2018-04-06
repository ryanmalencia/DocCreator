package rjm126.doccreator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public void NewDoc(View view)
    {
        Intent intent = new Intent(this,BlankDoc.class);
        startActivity(intent);
    }

    public void OpenDoc(View view)
    {
        Intent intent = new Intent(this,showDocs.class);
        startActivity(intent);
    }

    public void about(MenuItem item) {
        Intent intent = new Intent(this, about.class);
        startActivity(intent);
    }
}
