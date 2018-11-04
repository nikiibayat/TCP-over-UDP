public abstract class TCPServerSocket {
    public TCPServerSocket(int port) throws Exception {}

    public abstract TCPSocket accept() throws Exception;

    public abstract void close() throws Exception;
}