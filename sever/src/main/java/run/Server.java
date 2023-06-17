package run;

import exceptions.OpeningServerSocketException;
import interaction.Request;
import interaction.Response;
import interaction.ResponseCode;
import utility.CollectionManager;
import utility.Outputer;
import utility.RequestHandler;

import java.io.*;
import java.net.*;


/**
 * Runs the server.
 */
public class Server {
    private int port;
    private DatagramSocket serverSocket;
    private RequestHandler requestHandler;

    private CollectionManager collectionManager;

    public Server(int port, RequestHandler requestHandler, CollectionManager collectionManager) {
        this.port = port;
        this.requestHandler = requestHandler;
        this.collectionManager = collectionManager;
    }

    /**
     * Begins server operation.
     */

    public void run(){
        try {
            openServerSocket();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                if (reader.ready()){
                    String line = reader.readLine();
                    System.out.println("Read line: " + line);
                    if (line.trim().equals("save")) {
                        collectionManager.saveCollection();
                        Outputer.println("Collection saved in file");
                    } else if (line.trim().equals("stop")) {
                        System.exit(0);
                    }
                } else {

                    boolean  processingStatus = processClientRequest();

                }
            }

        } catch (OpeningServerSocketException exception) {
            Outputer.printerror("Server cannot be started!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finishes server operation.
     */

    public void stop() {
        if (serverSocket == null) return;
        serverSocket.close();
        Outputer.println("Server completed successfully.");
    }

    /**
     * Open server socket.
     */

    private void openServerSocket() throws OpeningServerSocketException {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("Port '" + port + "' is out of range!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Outputer.printerror("An error occurred while trying to use the port '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }

    /**
     * The process of receiving a request from a client.
     */

    private boolean processClientRequest() {
        byte[] buffer = new byte[65507];
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
        Request userRequest = null;
        Response responseToUser = null;
        try {
            serverSocket.receive(requestPacket);
            userRequest = (Request) deserialize(requestPacket.getData());
            Outputer.println(userRequest.getCommandName());
            responseToUser = requestHandler.handle(userRequest);
            byte[] responseData = serialize(responseToUser);
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, requestPacket.getAddress(), requestPacket.getPort());
            serverSocket.send(responsePacket);
            if (responseToUser.getResponseCode() == ResponseCode.SERVER_EXIT) {
                return false;
            }
        } catch (ClassNotFoundException exception) {
            Outputer.printerror("An error occurred while reading received data!");
        } catch (IOException exception) {
            if (userRequest == null) {
                Outputer.printerror("Unexpected loss of connection with the client!");
            } else {
                Outputer.println("Client successfully disconnected from server!");
            }
        }
        return true;
    }

    /**
     * Serialize object to byte array.
     */

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Deserialize byte array to object.
     */

    private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }
}