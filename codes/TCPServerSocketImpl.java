
public class TCPServerSocketImpl extends TCPServerSocket {
    public TCPServerSocketImpl(int port) throws Exception {
        super(port);
    }

    @Override
    public TCPSocket accept() throws Exception {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void close() throws Exception {
        throw new RuntimeException("Not implemented!");
    }
}
