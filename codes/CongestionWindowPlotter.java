import java.awt.*;
import java.io.IOException;
import java.util.Date;

public interface CongestionWindowPlotter {
    long getSSThreshold();

    long getWindowSize();

    default void onWindowChange() {
        long systemTimeInMillis = System.currentTimeMillis();
        windowSizeDateSeries.xy(systemTimeInMillis, getWindowSize());
        ssThresholdDataSeries.xy(systemTimeInMillis, getSSThreshold());
    }

    default void saveCongestionWindowPlot() {
        try {
            Plot.plot(Plot.plotOpts().title("Congestion Window"))
                    .series("Window Size",
                            windowSizeDateSeries,
                            new Plot.DataSeriesOptions()
                                    .color(Color.BLACK))
                    .series("SSThreshold",
                            ssThresholdDataSeries,
                            new Plot.DataSeriesOptions()
                                    .color(Color.RED)
                                    .line(Plot.Line.DASHED)
                                    .lineWidth(1))
                    .yAxis("mss", null)
                    .xAxis("ms", null)
                    .save(String.format("CongestionWindow [%s]", new Date().toString().replace(":", "-")), "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Plot.Data windowSizeDateSeries = Plot.data();
    Plot.Data ssThresholdDataSeries = Plot.data();
}
