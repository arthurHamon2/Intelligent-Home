package server.Sensors.Frame;
import java.nio.ByteBuffer;

import server.models.AbstractFrame;


abstract public class OceanAbstractFrame extends AbstractFrame {
	byte mFrame[];
	
	
	public enum enumFrameStructure {
		 SYNC_BYTE1(0)
		,SYNC_BYTE0(1)
		,H_SEQ_LENGTH(2)
		,ORG(3)
		,DATA_BYTE3(4)
		,DATA_BYTE2(5)
		,DATA_BYTE1(6)
		,DATA_BYTE0(7)
		,ID_BYTE3(8)
		,ID_BYTE2(9)
		,ID_BYTE1(10)
		,ID_BYTE0(11)
		,STATUS(12)
		,CHECKSUM(13);
	 
		/** L'attribut qui contient la valeur associé à l'enum */
		private final int value;
	 
		/** Le constructeur qui associe une valeur à l'enum */
		private enumFrameStructure(int value) {
			this.value = value;
		}
	 
		/** La méthode accesseur qui renvoit la valeur de l'enum */
		public int getValue() {
			return this.value;
		}
	};

	
	protected static final int FrameSizeInByte = 14;
	protected static final int BIT0 = 1;
	protected static final int BIT1 = 2;
	protected static final int BIT2 = 4;
	protected static final int BIT3 = 8;
	protected static final int BIT4 = 16;
	protected static final int BIT5 = 32;
	protected static final int BIT6 = 64;
	protected static final int BIT7 = 128;
	public final static int NB_FRAME_CARACTER = 28;
	
	public OceanAbstractFrame(String trame) throws Exception {
		super(parseSensorId(convertToBytesArray(trame)));
		mFrame = convertToBytesArray(trame);
		
	}
	
	
	/**
	 * @param trame
	 * Convert String in Hex in a Short Array
	 * Not safe for leading zeros
	 * @return
	 */
	protected static byte[] convertToBytesArray(String trame) {

        int len = trame.length();
        byte[] byteArray = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
        	byteArray[i / 2] = (byte) ((Character.digit(trame.charAt(i), 16) << 4)
                    + Character.digit(trame.charAt(i+1), 16));
        }
        return byteArray;

		
	}

	
	public static long parseSensorId(byte[] bytes) throws Exception
	{
		long id = 0;
		
		if (bytes.length != FrameSizeInByte)
		{
			throw (new Exception("Invalid Frame !, Size is only : "+Integer.toString(bytes.length)));
			
		}
		else
		{
			ByteBuffer bb = ByteBuffer.allocate(8);
			bb.put((byte)0);
			bb.put((byte)0);
			bb.put((byte)0);
			bb.put((byte)0);
			bb.put((byte) readUnsigendByte(bytes,enumFrameStructure.ID_BYTE3));
			bb.put((byte) readUnsigendByte(bytes,enumFrameStructure.ID_BYTE2));
			bb.put((byte) readUnsigendByte(bytes,enumFrameStructure.ID_BYTE1));
			bb.put((byte) readUnsigendByte(bytes,enumFrameStructure.ID_BYTE0));

			bb.rewind();
			id = bb.getLong();
		}
		return id;
	}
	
	public static long parseSensorId(String Frame) throws Exception
	{
		byte b[] = convertToBytesArray(Frame);
		return parseSensorId(b);
	}
	
	
	protected static long getData(byte [] Framebytes)
	{
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.put((byte)0);
		bb.put((byte)0);
		bb.put((byte)0);
		bb.put((byte)0);
		bb.put((byte) readUnsigendByte(Framebytes,enumFrameStructure.DATA_BYTE3));
		bb.put((byte) readUnsigendByte(Framebytes,enumFrameStructure.DATA_BYTE2));
		bb.put((byte) readUnsigendByte(Framebytes,enumFrameStructure.DATA_BYTE1));
		bb.put((byte) readUnsigendByte(Framebytes,enumFrameStructure.DATA_BYTE0));
		bb.rewind();
		return bb.getLong();
	   
		
	}
	
	

	
	protected static int readUnsigendByte(byte Framebytes[],enumFrameStructure Byte)
	{
		
		return  Framebytes[Byte.getValue()] & 0xff;
	}
	
	
	protected static boolean isTeachInFrame(byte[] Framebytes) throws Exception
	{
		
		boolean retour = false;
		
		if (Framebytes.length != FrameSizeInByte)
		{
			throw (new Exception("Invalid Frame !, Size is only : "+Integer.toString(Framebytes.length)));
			
		}
		else
		{

			int org = readUnsigendByte(Framebytes,enumFrameStructure.ORG);
			if (org == 0x7)
			{
				int data0 = readUnsigendByte (Framebytes,enumFrameStructure.DATA_BYTE0);
				if ((data0&BIT3) == 0 && (data0&BIT7)==BIT7)
				{
					retour = true;
					
				}
				
			}
		}

		return retour;
	}
	
	public static boolean isTeachInFrame(String Frame) throws Exception
	{
		byte b[] = convertToBytesArray(Frame);
		return isTeachInFrame(b);
		
	}
	
	
	static public String toString(byte Framebytes[])
	{
		String str = new String();
		str += "Info frame : \n";
		try {
			str += "Capteur n ° "+Long.toString(parseSensorId(Framebytes))+"\n";
		} catch (Exception e) {
			e.printStackTrace();
		}
		str += "ORG = ";
		int org = readUnsigendByte(Framebytes,enumFrameStructure.ORG);
		str += Integer.toString(org)+"\n";
		str += "Status = ";
		int status = readUnsigendByte(Framebytes,enumFrameStructure.STATUS);
		str += Integer.toString(status)+"\n";
		str += "Data = ";
		long data = getData(Framebytes);
		str += Long.toString(data)+"\n";

		
		return str;
		
		
	}
	
	public String toString()
	{
		return toString(mFrame);
	
	}
	
	static public String toString(String Frame)
	{
		byte b[] = convertToBytesArray(Frame);
		return toString(b);
	
	}
	
	static public String generateChecksum(String Frame) throws Exception
	{
		byte b[] = convertToBytesArray(Frame);
		long db0 = readUnsigendByte(b, enumFrameStructure.DATA_BYTE0);
		long db1 = readUnsigendByte(b, enumFrameStructure.DATA_BYTE1);
		long db2 = readUnsigendByte(b, enumFrameStructure.DATA_BYTE2);
		long db3 = readUnsigendByte(b, enumFrameStructure.DATA_BYTE3);
		long id0 = readUnsigendByte(b, enumFrameStructure.ID_BYTE0);
		long id1 = readUnsigendByte(b, enumFrameStructure.ID_BYTE1);
		long id2 = readUnsigendByte(b, enumFrameStructure.ID_BYTE2);
		long id3 = readUnsigendByte(b, enumFrameStructure.ID_BYTE3);
		long ORG = readUnsigendByte(b, enumFrameStructure.ORG);
		long status = readUnsigendByte(b, enumFrameStructure.STATUS);
		long H_SEQ_LENGTH = readUnsigendByte(b, enumFrameStructure.H_SEQ_LENGTH);
		long result = db0+db1+db2+db3+id0+id1+id2+id3+status+H_SEQ_LENGTH+ORG;
		result = result&0xFFL;
		
		
		return toHexa(result,2);
		
	}
	
	static public String toHexa(long n,int longueur) throws Exception
	{
		String result = new String();
		result = Long.toHexString(n).toUpperCase();
		
		if (longueur<result.length())
		{
			throw (new Exception("Erreur conversion Hexa"));
			
		}
		else
		{
			while (result.length() < longueur)
			{
				result += "0";
			}
			
		}
		
		
		return result;		
		
	}
	
	static public Boolean isValid(String Frame)
	{
		Boolean retour = false;
		if (Frame.length() == NB_FRAME_CARACTER)
		{
			byte b[] = convertToBytesArray(Frame);
			try {
				if (generateChecksum(Frame).equals(toHexa(readUnsigendByte(b, enumFrameStructure.CHECKSUM),2))){
					retour = true;
				}
			
			} catch (Exception e) {

				System.out.println("Exception in isValid verification !");
			}
			
		}
		return retour;
		
	}
}
	
