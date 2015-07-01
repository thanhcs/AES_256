/**
 * Created by thanhnguyencs on 6/29/15.
 */
public class DataCrypto {
    private byte[][] state;
    private KeyExpansion keyObj;


    // constructor (parameter: string)
    // create object of class key (pass into: string key)

    public DataCrypto(String key) {
        keyObj = new KeyExpansion(key);
        state = new byte[4][4];
        // initial state
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0;j < state.length; ++j) {
                state[i][j] = 0;
            }
        }
    }

    // input the line containing state
    private void setState(String line) {
        int num = 0;
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0;j < state.length; ++j) {
                String twoChars = line.substring(num, num += 2);
                state[j][i] =(byte) ((Character.digit(twoChars.charAt(0), 16) << 4)
                        + Character.digit(twoChars.charAt(1), 16));
            }
        }
    }

    // encrypt
    public String encrypt(String line) {
        setState(line);
        AESEncrypt e = new AESEncrypt(state);
        e.encrypt();
        return e.getState();
    }









    // addRoundKey <- roundKey by calling (object key).getRoundKey() type ???
    // same as decrypt

}
