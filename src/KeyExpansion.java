/**
 * Created by thanhnguyencs on 7/1/15.
 */
public class KeyExpansion {
    Word[] keyExp;
    public static int Nk = 8;
    public static int Nr = 14;
    public static int totalWords = 60;

    public KeyExpansion(String key) {
        keyExp = new Word[totalWords];
        for (int i = 0; i < keyExp.length; ++i)
            keyExp[i] = new Word();
    }

    private void copyKey(String key) {
        int num = 0;
        for (int i = 0; i < Nk; ++i) {
            for (int j = 0; j < Constants.Nb; ++j) {
                String twoChars = key.substring(num, num += 2);
                byte t = (byte) ((Character.digit(twoChars.charAt(0), 16) << 4)
                        + Character.digit(twoChars.charAt(1), 16));
                keyExp[i].add(t);
            }
        }
    }

    private Word subWord(Word d) {
        Word temp = new Word();
        for (int i = 0; i < d.length(); ++i) {
            byte t = d.get(i);
            char c = Constants.S[(t < 0) ? (t + 256) : t];
            temp.add((byte) c);
        }
        return temp;
    }

    private Word rotWord(Word d) {
        return d.cyclicP();
    }

    private Word getRcon(int i) {
        byte value = (byte) Constants.Rcon[i];
        byte[] t = {value, 0, 0, 0};
        return new Word(t);
    }


    public void keyExpansion(String key) {
        copyKey(key);

        Word temp;
        for (int i = Nk; i < totalWords; ++i) {
            temp = keyExp[i-1];
            if (i % Nk ==0) {
                temp = subWord(rotWord(temp)).xor(getRcon(i/Nk));
            }
            else if (Nk > 6 && i % Nk == 4) {
                temp = subWord(temp);
            }

            keyExp[i] = keyExp[i - Nk].xor(temp);
        }
    }

    public void printState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalWords; ++i) {
            for (int j = 0; j < Constants.Nb; ++j) {
                byte t = keyExp[i].get(j);
                int tInt = (t < 0) ? (t + 256) : t;
                sb.append(Integer.toHexString(tInt >>> 4 & 0xF));
                sb.append(Integer.toHexString(t & 0xF));

            }
            sb.append(" ");
            if (i != 0 && (i + 1) % (Nr + 1) == 0) {
                sb.append("\n");
            }
        }
        System.out.println(sb.toString().toUpperCase());
    }
}
