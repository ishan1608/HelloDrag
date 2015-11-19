package ishan1608com.hellodrag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String DRAG_ME_TAG = "DRAG_ME_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ImageView dragItemImageView = (ImageView) findViewById(R.id.drag_item);
        dragItemImageView.setTag(DRAG_ME_TAG);
        dragItemImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Create a new ClipData

                // Create a new ClipData.Item from ImageView object's tag
                // Had to cast it to a String as it wasn't working according to the documentation instructions
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                // Create a new ClipData using the tag as label, the plain text MIME type, and
                // the already-created item, this will create a new ClipDescription object within the
                // ClipData, and set its MIME type entry to "text/plain"
                ClipData dragData = new ClipData((CharSequence) v.getTag(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);

                // Instantiates the drag shadow builder.
                // This one doesn't show anything as I suspect the drawable is blank
                // View.DragShadowBuilder myShadow = new MyDragShadowBuilder(dragItemImageView);

                // Implement a dragShadow without extending View.DragShadowBuilder
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(dragItemImageView);

                // Starts the drag
                v.startDrag(dragData, // the data to be dragged
                        myShadow, // the drag shadow builder
                        null, // no need to use local data
                        0 // flags (not currently used, set to 0)
                );
                return true;
            }
        });

        ImageView dropZoneImageView = (ImageView) findViewById(R.id.drop_zone);
        // Sets the drag even listener for the View
        dropZoneImageView.setOnDragListener(new myDragEventListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
