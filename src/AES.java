/**
 * Created by thanhnguyencs on 6/28/15.
 */
public class AES {
    public static boolean DEBUG = false;


    public static void main(String[] args) {

        if (args[0].equals("db")) {
            // debug mode. Command line: java AES db <d or e> <key> <one line of plaintext>
            AES.DEBUG = false;

            String option = args[1];
            String key = args[2];
            String data = args[3];
            if (option.equals("e")) {
                DataCrypto aes = new DataCrypto(key);
                String ret = aes.encrypt(data);
                System.out.println();
                System.out.println("Key: \n" + key);
                System.out.println("Plaintext: \n" + data);
                System.out.println("Cipher: \n" + ret);
            } else if (option.equals("d")) {
                DataCrypto aes = new DataCrypto(key);
                String ret = aes.decrypt(data);
                System.out.println();
                System.out.println("Key: \n" + key);
                System.out.println("Cipher: \n" + data);
                System.out.println("Plaintext: \n" + ret);
            }
        }
    }
}
