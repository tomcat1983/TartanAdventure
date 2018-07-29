package network;

public class NetworkInterface {
	
	/**
	 * Field delimiter
	 */
	private static final String fieldDelimiter = ":";
	
	/**
	 * Packet type
	 */
	public enum PacketType {
		LOGIN, DESIGNER, REGISTER, COMMAND, MAP
	}
	
	/**
	 * Make packet
	 * Packet structure : Type:ID:Data:Data:...
	 */
	public static String makePacket(PacketType type, String ID, String[] data) {
		String packet = type.name() + fieldDelimiter + ID;
		
		for (int index = 0; index < data.length; index++) {
			packet += fieldDelimiter + data[index];
		}
		
		return packet;
	}

}
