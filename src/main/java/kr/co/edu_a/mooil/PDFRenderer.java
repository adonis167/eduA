package kr.co.edu_a.mooil;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PDFRenderer extends Activity {

    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_real);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PdfRendererBasicFragment(),
                            FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.intro_message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}