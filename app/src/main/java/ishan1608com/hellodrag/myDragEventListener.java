package ishan1608com.hellodrag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

public class myDragEventListener implements View.OnDragListener {

    // This is the method that system calls when it dispatches a drag event to the listener
    @Override
    public boolean onDrag(View v, DragEvent event) {

        // Defines a variable to store the action type for the incoming event
        final int action = event.getAction();

        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this view can accept the dragged data
                // Can also add check based on the content of the clip description (label / tag)
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    // As an example of what your application might do.
                    // applies a blue color tint to the View to indicate that it can accept data
                    ((ImageView) v ).setColorFilter(Color.BLUE, PorterDuff.Mode.LIGHTEN);

                    // Invalidate the view to force redraw
                    v.invalidate();

                    // Returns true to indicate that the View can accept the dragged data
                    return true;
                }

                // Returns false. During the current drag and drop operation, this view will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;
            case DragEvent.ACTION_DRAG_ENTERED:

                // Applies a green tint to the view.
                ((ImageView) v).setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);
                v.invalidate();
                // Return true, return value is ignored
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:

                // Re-sets the color tint to blue.
                ((ImageView) v).setColorFilter(Color.BLUE, PorterDuff.Mode.LIGHTEN);
                v.invalidate();
                // return value will be ignored
                return true;
            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item
                CharSequence dragData = item.getText();

                // Displays a message containing the dragged data
                Snackbar.make(v, "Dragged data is " + dragData, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Turns off any color tints
                ((ImageView) v).clearColorFilter();
                ((ImageView) v).setImageResource(R.drawable.drag_item);
                v.invalidate();

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:

                // Turns off any color tinting
                ((ImageView) v).clearColorFilter();

                // Invalidates the view to force a redraw
                v.invalidate();

                // Does a getResult(), and displays what happened
                if (event.getResult()) {
//                    Snackbar.make(v, "The drop was handled", Snackbar.LENGTH_SHORT)
//                            .setAction("Action", null).show();
                    Log.d("DragDrop Example", "The drop was handled");
                } else {
                    Snackbar.make(v, "The drop didn't work", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                // Returns true, the value is ignored
                return true;
            default:
                // An unknown action type was received
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener");
                break;
        }
        return false;
    }
}
