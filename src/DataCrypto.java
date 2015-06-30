/**
 * Created by thanhnguyencs on 6/29/15.
 */
public class DataCrypto {
    private byte[][] state;
    private keyCrypto keyObj;


    // constructor (parameter: string)
    // create object of class key (pass into: string key)

    DataCrypto(String key) {
        keyObj = new keyCrypto(key);
        state = new byte[4][4];
        // initial state
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0;j < state.length; ++j) {
                state[i][j] = 0;
            }
        }
    }

    // input the line containing state
    public void setState(String line) {
        int num = 0;
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0;j < state.length; ++j) {
                String twoChars = line.substring(num, num += 2);
                state[i][j] = Byte.valueOf(twoChars, 16);
            }
        }
    }







    // addRoundKey <- roundKey by calling (object key).getRoundKey() type ???
    // same as decrypt

    public void sam() {
        if (AES.DEBUG) {

        }
    }
}
