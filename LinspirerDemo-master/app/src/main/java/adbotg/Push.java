package adbotg;

import android.os.Handler;
import android.util.Log;

import com.cgutman.adblib.AdbConnection;
import com.cgutman.adblib.AdbStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by xudong on 2/25/14.
 */
public class Push {

    private AdbConnection adbConnection;
    private File local;
    private String remotePath;

    public Push(AdbConnection adbConnection, File local, String remotePath) {
        this.adbConnection = adbConnection;
        this.local = local;
        this.remotePath = remotePath;
    }

    public void execute(Handler handler) throws InterruptedException, IOException {

        AdbStream stream = adbConnection.open("sync:");

        String sendId = "SEND";

        String mode = ",33206";

        int length = (remotePath + mode).length();

        stream.write(ByteUtils.concat(sendId.getBytes(), ByteUtils.intToByteArray(length)));

        stream.write(remotePath.getBytes());

        stream.write(mode.getBytes());

        byte[] buff = new byte[adbConnection.getMaxData()];
        InputStream is = new FileInputStream(local);

        long sent = 0;
        long total = local.length();
        int lastProgress = 0;
        while (true) {
            int read = is.read(buff);
            if (read < 0) {
                break;
            }

            stream.write(ByteUtils.concat("DATA".getBytes(), ByteUtils.intToByteArray(read)));

            if (read == buff.length) {
                stream.write(buff);
            } else {
                byte[] tmp = new byte[read];
                System.arraycopy(buff, 0, tmp, 0, read);
                stream.write(tmp);
            }

            sent += read;

            final int progress = (int)(sent * 100 / total);
            if (lastProgress != progress) {
                handler.sendMessage(handler.obtainMessage(Message.INSTALLING_PROGRESS, Message.PUSH_PART, progress));
                lastProgress = progress;
            }

        }

        stream.write(ByteUtils.concat("DONE".getBytes(), ByteUtils.intToByteArray((int) System.currentTimeMillis())));

        byte[] res = stream.read();
        // TODO: test if res contains "OKEY" or "FAIL"
        Log.d("?", new String(res));

        stream.write(ByteUtils.concat("QUIT".getBytes(), ByteUtils.intToByteArray(0)));
    }
}

class ByteUtils {
    public static byte[] concat(byte[]... arrays) {
        int totalLength = 0;
        for (int i = 0; i < arrays.length; i++) {
            totalLength += arrays[i].length;
        }
        byte[] result = new byte[totalLength];
        int currentIndex = 0;
        for (int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
            currentIndex += arrays[i].length;
        }
        return result;
    }
    public static final byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
    }
}
