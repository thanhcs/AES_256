import cli.*;

import java.io.*;
import java.util.List;

/**
 * Created by thanhnguyencs on 6/28/15.
 */
public class AES {
    public static boolean DEBUG = false;


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        if (args[0].equals("db")) {
            // debug mode. Command line: java AES db <d or e> <key> <one line of plaintext>
            debug(args);
        } else {
            CommandLineParser parser = new DefaultParser();
            Options options = new Options();
            options.addOption("length", false, "set length of the key");
            options.addOption("mode", false, "set mode cbc or ecb");
            DataCrypto aes = null;
            String mode = "ecb";
            String length = "128";
            int num = 1;
            String option = args[0];
            String[] arguments = null;
            try {
                // parse the command line arguments
                CommandLine line = parser.parse(options, args);
                List<String> l = line.getArgList();
                arguments = line.getArgs();

                if (line.hasOption("length")) {
                    length = arguments[num];
                    ++num;
                }
                if (line.hasOption("mode")) {
                    mode = arguments[num];
                    ++num;
                }

            }
            catch( ParseException exp ) {
                // oops, something went wrong
                System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
            }

            assert arguments != null;
            String keyFile = arguments[num];
            String fileData = arguments[++num];
            String[] key_cbc = getKey(keyFile);

            String key = key_cbc[0];
            String CBCVector = key_cbc[1];
            if (!validateInputLine(key)) {
                throw new IllegalArgumentException("Key is malformed");
            }
            if (mode.equals("ecb"))
                aes = new DataCryptoECB(key, length);
            else if (mode.equals("cbc"))
                aes = new DataCryptoCBC(key, CBCVector, length);
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                in = new BufferedReader(new FileReader(fileData));
                String line;
                assert aes != null;
                if (option.equals("e")) {
                    out = new PrintWriter(fileData + ".enc");
                    while ((line = in.readLine()) != null) {
                        if (validateInputLine(line)) {
                            String ret = aes.encrypt(line);
                            out.println(ret);
                        }
                    }
                } else if (option.equals("d")) {
                    out = new PrintWriter(fileData + ".dec");
                    while ((line = in.readLine()) != null) {
                        if (validateInputLine(line)) {
                            String ret = aes.decrypt(line);
                            out.println(ret);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Can't find the data file");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
            }

            long endTime = System.nanoTime();
            double timeRun = (endTime - startTime) / 1000000000.0;

            System.out.println("Time to run option " + option + " on " + fileData + " : " + timeRun);
        }
    }

    private static void debug(String[] args) {
        AES.DEBUG = true;

        String option = args[1];
        String key = args[2];
        String data = args[3];
        if (!validateInputLine(key)) {
            throw new IllegalArgumentException("Key is malformed");
        }
        if (option.equals("e")) {
            DataCryptoECB aes = new DataCryptoECB(key, "128");
            String ret = "";
            if (validateInputLine(data))
                ret = aes.encrypt(data);
            System.out.println();
            System.out.println("Key: \n" + key);
            System.out.println("Plaintext: \n" + data);
            System.out.println("Cipher: \n" + ret);
        } else if (option.equals("d")) {
            DataCryptoECB aes = new DataCryptoECB(key, "256");
            String ret = "";
            if (validateInputLine(data))
                ret = aes.decrypt(data);
            System.out.println();
            System.out.println("Key: \n" + key);
            System.out.println("Cipher: \n" + data);
            System.out.println("Plaintext: \n" + ret);
        }
    }

    private static boolean validateInputLine(String key) {
        for (int i = 0; i < 256 && i < key.length(); ++i) {  // hard code 256
            char c = key.charAt(i);
            boolean t = ((c >= '0' && c <= '9') ||
                        (c >= 'a' && c <= 'f') ||
                        (c >= 'A' && c <= 'F'));
            if (!t)
                return false;
        }
        return true;
    }

    private static String[] getKey(String keyFile) {
        String[] temp = new String[2];
        BufferedReader inKey = null;
        try {
            inKey = new BufferedReader(new FileReader(keyFile));
            temp[0] = inKey.readLine();
            temp[1] = inKey.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Can't find the key file");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inKey != null) {
                try {
                    inKey.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }
}
