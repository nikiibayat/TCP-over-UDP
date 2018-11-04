import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Date;

public class EnhancedDatagramSocket extends DatagramSocket {
    public EnhancedDatagramSocket(int port) throws SocketException {
        super(port);
        randomGenerator = new Random(System.currentTimeMillis());
        payloadLimitInBytes = DEFAULT_PAYLOAD_LIMIT_IN_BYTES;
        lossRate = DEFAULT_LOSS_RATE;
        delayInMilliseconds = DEFAULT_DELAY_IN_MILLISECONDS;
        timer = new Timer();
        plotData = Plot.data();
        plotData.xy(0, 0);
        currentTime = 0;
        currentSecondSentBytes = new AtomicLong(0);
        scheduleToCollectPlotData();
    }

    @Override
    public void send(DatagramPacket datagramPacket) throws IOException {
        if(datagramPacket.getLength() > payloadLimitInBytes)
            throw new PayloadLimitViolationException(
                    String.format("Payload limit is %d bytes but sending packet contains %d bytes.",
                            payloadLimitInBytes,
                            datagramPacket.getLength()
                    ));

        currentSecondSentBytes.addAndGet(datagramPacket.getLength());

        if(randomGenerator.nextDouble() <= lossRate)
            return;

        if(delayInMilliseconds != 0)
            scheduleToSend(datagramPacket);
        else
            super.send(datagramPacket);
    }

    @Override
    public void close() {
        super.close();
        try {
            Thread.sleep(SENT_BYTES_SAMPLING_PERIOD_IN_MILLISECONDS);
        } catch (InterruptedException ignored) {}
        timer.cancel();
        plot();
    }

    public void plot() {
        plotData.xy(currentTime + SENT_BYTES_SAMPLING_PERIOD_IN_MILLISECONDS, 0);

        Plot.PlotOptions plotOptions = Plot.plotOpts();
        Plot.DataSeriesOptions dataSeriesOptions = new Plot.DataSeriesOptions();

        plotOptions.title("Sent bytes per ms");
        try {
            Plot.plot(plotOptions)
                .series(null, plotData, dataSeriesOptions)
                .yAxis("byte", null)
                .xAxis("ms", null)
                .save(String.format("BytesPerTime [%s]", new Date().toString().replace(":", "-")), "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPayloadLimitInBytes() {
        return payloadLimitInBytes;
    }

    public void setPayloadLimitInBytes(int payloadLimitInBytes) {
        if(payloadLimitInBytes < 0)
            throw new RuntimeException("Payload limit cannot be negative.");

        this.payloadLimitInBytes = payloadLimitInBytes;
    }

    public double getLossRate() {
        return lossRate;
    }

    public void setLossRate(double lossRate) {
        if(lossRate < 0)
            throw new RuntimeException("Loss rate cannot be negative.");

        this.lossRate = lossRate;
    }

    public void setDelayInMilliseconds(int delayInMilliseconds) {
        if(delayInMilliseconds < 0)
            throw new RuntimeException("Delay cannot be negative.");

        this.delayInMilliseconds = delayInMilliseconds;
    }

    public long getDelayInMilliseconds() {
        return delayInMilliseconds;
    }

    private void scheduleToSend(DatagramPacket datagramPacket) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(!isClosed())
                        EnhancedDatagramSocket.super.send(datagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delayInMilliseconds);
    }

    private void scheduleToCollectPlotData() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTime += SENT_BYTES_SAMPLING_PERIOD_IN_MILLISECONDS;
                plotData.xy(currentTime, currentSecondSentBytes.getAndSet(0));

                if(!isClosed())
                    scheduleToCollectPlotData();
            }
        }, SENT_BYTES_SAMPLING_PERIOD_IN_MILLISECONDS);
    }

    public static final int DEFAULT_PAYLOAD_LIMIT_IN_BYTES = 1408;
    public static final double DEFAULT_LOSS_RATE = 0;
    public static final long DEFAULT_DELAY_IN_MILLISECONDS = 0;
    private static final int SENT_BYTES_SAMPLING_PERIOD_IN_MILLISECONDS = 50;
    private int payloadLimitInBytes;
    private double lossRate;
    private long delayInMilliseconds;
    private Random randomGenerator;
    private Timer timer;
    private Plot.Data plotData;
    private int currentTime;
    private AtomicLong currentSecondSentBytes;
}
