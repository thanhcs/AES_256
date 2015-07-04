/**
 * Created by thanhnguyencs on 7/3/15.
 */
public class AESEncryptCBC extends AESEncrypt {
    public AESEncryptCBC(byte[][] state, KeyExpansion key) {
        super(state, key);
    }

    public void encrypt() {
        addCBCVector();
        super.encrypt();
        setCBCVector(copyState());
    }
}
