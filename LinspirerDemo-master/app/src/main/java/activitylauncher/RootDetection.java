package activitylauncher;
import com.huosoft.wisdomclass.linspirerdemo.lspdemoApplication;

import java.io.File;
import java.util.Objects;
import java.util.Vector;
public class RootDetection {
    public static boolean detectSU() {
        var paths = new Vector<File>();
        var dirs = Objects.requireNonNull(System.getenv("PATH")).split(":");
        for (var dir : dirs) {
            paths.add(new File(dir, "su"));
        }
        for (var path : paths) {
            if (path.exists() && path.canExecute() && path.isFile()) {
                return true;
            }
        }
        if(lspdemoApplication.getMMDM()==4){
            return true;
        }
        return false;
    }
}
