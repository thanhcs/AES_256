/**
 * Created by thanhnguyencs on 7/3/15.
 */
public class DataCrypto {


    // variable used after indicating the mode
    public static int Nk = 4;
    public static int Nr = 10;
    public static int totalWords = 44;

    protected byte[][] state;
    protected KeyExpansion keyObj;


    public DataCrypto(String key, String length) {
        setLength(length);
        keyObj = new KeyExpansion(key);
        state = new byte[4][4];
        // initial state
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0;j < state.length; ++j) {
                state[i][j] = 0;
            }
        }
    }

    // input the line containing state into state 2d array
    protected void setState(String line) {
        parseLine(line, state);
    }

    protected void parseLine(String line, byte[][] state) {
        int num = 0;
        boolean flag = true;
        for (int i = 0; i < state[0].length && flag; ++i) {
            for (int j = 0;j < state.length && flag; ++j) {
                if (line.length() == num) {
                    flag = false;
                } else {
                    if (line.length() == num + 1) {
                        line = line.concat("0");
                        break;
                    }
                    String twoChars = line.substring(num, num += 2);
                    state[j][i] = (byte) ((Character.digit(twoChars.charAt(0), 16) << 4)
                            + Character.digit(twoChars.charAt(1), 16));
                    //handle the case when the line is longer or shorter truncate or pad to right with 0
                }
            }
        }
    }

    public String encrypt(String line) {
        setState(line);
        AESEncrypt e = new AESEncrypt(state, keyObj);
        if (AES.DEBUG)
            e.encryptDB();
        else
            e.encrypt();
        return e.getState();
    }

    public String decrypt(String line) {
        setState(line);
        AESDecrypt d = new AESDecrypt(state, keyObj);
        if (AES.DEBUG)
            d.decryptDB();
        else
            d.decrypt();
        return d.getState();
    }

    public void setLength(String length) {
        if (length.equals("128")) {
        } else if (length.equals("192")) {
            DataCrypto.Nk = 6;
            DataCrypto.Nr = 12;
            DataCrypto.totalWords = 52;
        } else if (length.equals("256")) {
            DataCrypto.Nk = 8;
            DataCrypto.Nr = 14;
            DataCrypto.totalWords = 60;
        } else {
            throw new IllegalArgumentException("The provided length key is not in standard document");
        }
    }
}
