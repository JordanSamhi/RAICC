package lu.uni.trux.raicc.utils;

import lu.uni.trux.raicc.ResultsAccumulator;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimeOut {

    private final Timer timer;
    private final TimerTask exitTask;
    private final int timeout;

    public TimeOut(int n) {
        this.timer = new Timer();
        this.exitTask = new TimerTask() {
            @Override
            public void run() {
                ResultsAccumulator.v().setReachedTimeout(true);
                Writer.v().pwarning("Timeout reached!");
                Writer.v().pwarning("Ending program...");
                if (CommandLineOptions.v().hasRaw()) {
                    ResultsAccumulator.v().printVectorResults();
                } else {
                    ResultsAccumulator.v().printResults();
                }
                System.exit(1);
            }
        };
        this.timeout = n != 0 ? n : 60;
    }

    public void launch() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, this.timeout);
        this.timer.schedule(this.exitTask, c.getTime());
    }

    public void cancel() {
        this.timer.cancel();
    }
}