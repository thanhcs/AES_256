/**
 * Created by thanhnguyencs on 6/30/15.
 */
public class AESDecrypt {
    byte[][] state;

    public AESDecrypt(byte[][] state) {
        this.state = state;
    }

    private void invSubBytes() {
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0; j < state.length; ++j) {
                char c = Constants.INV_S[state[j][i] & 0xFF];
                state[j][i] = (byte) c;
            }
        }
    }

    private void invShiftRows() {
        byte temp0;
        byte temp1;

        // row 2
        temp0 = state[1][3];
        state[1][3] = state[1][2];
        state[1][2] = state[1][1];
        state[1][1] = state[1][0];
        state[1][0] = temp0;

        // row 3
        temp0 = state[2][0];
        temp1 = state[2][1];
        state[2][0] = state[2][2];
        state[2][1] = state[2][3];
        state[2][2] = temp0;
        state[2][3] = temp1;

        // row 4
        temp0 = state[3][0];
        state[3][0] = state[3][1];
        state[3][1] = state[3][2];
        state[3][2] = state[3][3];
        state[3][0] = temp0;
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
