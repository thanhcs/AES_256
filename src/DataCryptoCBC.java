/**
 * Created by thanhnguyencs on 7/3/15.
 */
public class DataCryptoCBC extends DataCryptoECB {
    private byte[][] CBCVector;

    public DataCryptoCBC(String key, String CBCVector, String length) {
        super(key);
        // initial state
        this.CBCVector = new byte[4][4];
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0;j < state.length; ++j) {
                this.CBCVector[i][j] = 0;
            }
        }
        if (CBCVector != null)
            parseCBCVector(CBCVector);
        AESCrypto.setCBCVector(this.CBCVector);
        setLength(length);
    }

    private void parseCBCVector(String line) {
        parseLine(line, CBCVector);
    }

    public String encrypt(String line) {
        setState(line);
        AESEncryptCBC e = new AESEncryptCBC(state, keyObj);
        e.encrypt();
        return e.getState();
    }

    public String decrypt(String line) {
        setState(line);
        AESDecryptCBC d = new AESDecryptCBC(state, keyObj);
        d.decrypt();
        return d.getState();
    }

    public void setLength(String length) {
        if (length.equals("128")) {
        } else if (length.equals("192")) {
            Nk = 6;
            Nr = 12;
            totalWords = 52;
        } else if (length.equals("256")) {
            Nk = 8;
            Nr = 14;
            totalWords = 60;
        } else {
            throw new IllegalArgumentException("The provided length key is not in standard document");
        }
    }
}

