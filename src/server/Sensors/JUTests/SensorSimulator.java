package server.Sensors.JUTests;



import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SensorSimulator extends Thread {
	private Socket connectionSocket;
	private ServerSocket welcomeSocket;

	public SensorSimulator() throws IOException {
		welcomeSocket = new ServerSocket(5000);
	}

	@Override
	public void run() {
		try {
			connectionSocket = welcomeSocket.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		boolean clientIsConnected = true;

		while (clientIsConnected) {

			DataOutputStream outToClient = null;
			try {
				outToClient = new DataOutputStream(
						connectionSocket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				outToClient.writeBytes("A55A0B070084990F0004E9570001");
				System.out.println("Trame envoyée ! ");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			try {
				outToClient.writeBytes("A55A0B07100802870004E9570088");
				System.out.println("Trame envoyée ! ");

			} catch (IOException e) {
				
				e.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			try {
				outToClient.writeBytes("A55A0B070084990F0004E9570001");
				System.out.println("Trame envoyée ! ");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}


		}

	}
}
