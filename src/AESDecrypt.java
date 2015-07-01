/**
 * Created by thanhnguyencs on 6/30/15.
 */
public class AESDecrypt {
    byte[][] state;

    public AESDecrypt(byte[][] state) {
        this.state = state;
    }

    private void subBytes() {
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0; j < state.length; ++j) {
                char c = Constants.INV_S[state[j][i] & 0xFF];
                state[j][i] = (byte) c;
            }
        }
    }



    // used for debug
    public String printState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0; j < state.length; ++j) {
                byte t = state[j][i];
                int tInt = (t < 0) ? (t + 256) : t;
                sb.append(Integer.toHexString(tInt >>> 4 & 0xF));
                sb.append(Integer.toHexString(t & 0xF));
            }
        }
        return sb.toString().toUpperCase();
    }
}
