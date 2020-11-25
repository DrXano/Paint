package interfaces;

import com.example.paint.Draw;

import java.util.ArrayList;

public interface PathInterface {
    void onDataReceived(ArrayList<Draw> paths, boolean hasDraw);
}
