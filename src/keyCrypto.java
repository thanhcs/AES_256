/**
 * Created by thanhnguyencs on 6/29/15.
 */

public class keyCrypto {
	static int mode;	//need to be matched w/ input mode
	protected byte[] byteKey;
	protected byte[][] roundKeys;
	protected int keyCols;
	protected int rounds;

	
	public keyCrypto(String str) {
		byteKey = stringToByteArray(str);
		keyCols = byteKey.length / 4;
		rounds = keyCols + 4 + 2;
		roundKeys = null;
	}

	
	//Convert input key from string to bytes
	public static byte[] stringToByteArray(String str) {
		String s = str.toLowerCase();
		//padding string up to 64 characters (256-bits)
		while (s.length() % 64 != 0) {
			s = s + "0";
		}
		//truncate string down to the equivalent mode ( 128, 192 or 256 bits)
		s = s.substring(0, mode / 4);

		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	
	public void keyExpansion() {
		roundKeys = new byte[4][(rounds + 1) * 4];
		for ( int i = 0; i < keyCols; i++ ) {
			for(int j = 0; j < 4; j++ ) {
				roundKeys[i][j] = byteKey[4 * i + j];
			}
		}
		
		byte[] col = new byte[4];
		for ( int i = 0; i < 4; i++ ) {
			col[i] = byteKey[4 * keyCols + (i-4)];
		}

		for ( int i = keyCols; i < (rounds + 1) * 4; ) {
			byte temp = col[0];
			col[0] = col[1];
			col[1] = col[2];
			col[2] = col[3];
			col[3] = temp;

			col = subBytesFromSbox(col);

			for ( int j = 0; j < 4; j++ )	{
				roundKeys[i][j] = col[j] = (byte) (roundKeys[i-keyCols][j] ^ col[j] ^
						(byte) (j == 0 ? Constants.Rcon[i / keyCols] : 0));
			}
			i++;
			for ( int j = 0; j < 3; j++, i++ ) {
				for( int k = 0; k < 4; k++ ) {
					roundKeys[i][k] = col[k] = (byte) (roundKeys[i-keyCols][k] ^ col[k]);
				}
			}

			//for 256-bit keys:
			if ( byteKey.length == 32 && i < 60 ) {
				col = subBytesFromSbox(col);
				for ( int j = 0; j < 4; j++, i++ ) {
					for (int k = 0; k < 4; k++) {
						roundKeys[i][k] = col[k] = (byte) (roundKeys[i-keyCols][k] ^ col[k]);
					}
				}
			}
		}
	}

	
	//Substitutes all bytes with corresponding S[] bytes
	private byte[] subBytesFromSbox(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Constants.S[ (int) (bytes[i] & 0xFF) ];
		}
		return bytes;
	}

}
