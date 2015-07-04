/**
 * Created by thanhnguyencs on 7/3/15.
 */
public class AESDecryptCBC extends AESDecrypt {
    public AESDecryptCBC(byte[][] state, KeyExpansion key) {
        super(state, key);
    }

    public void decrypt() {
        setCBCVector(copyState());
        super.decrypt();
        addCBCVector();
    }


}
