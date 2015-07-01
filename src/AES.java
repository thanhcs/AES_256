/**
 * Created by thanhnguyencs on 6/28/15.
 */
public class AES {
    public static boolean DEBUG = false;


    public static void main(String[] args) {

        if (args[0].equals("db")) {
            // debug mode. Command line: java AES db <d or e> <key> <one line of plaintext>
            AES.DEBUG = true;

            String mode = args[1];
            String key = args[2];
            String state = args[3];

            DataCrypto aes = new DataCrypto(key);
            aes.encrypt(state);

            KeyExpansion keyExp = new KeyExpansion(key);
            keyExp.keyExpansion(key);
            System.out.println("Key Expansion: ");
            keyExp.printState();
        }

        // parse command line

        // encrypt or decrypt object (parameter String key)

        // call methode encrypt or decrypt
        // object <- plaintext'S line (block) -> cipher'S line (block) -> file (*.enc or *.dec)



    }
}
