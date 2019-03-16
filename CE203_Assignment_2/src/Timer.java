public class Timer {

    private boolean isPaused;

    private float milliSeconds;

    private float untilNextCycle;

    private long lastUpdate;

    private int totalCycles;

    //Creates a new Timer and sets is cycles per second
    Timer(float seconds) {
        setCycles(seconds);
        ResetTimer();

    }

    //sets number of cycles that occur per second
    private void setCycles(float seconds) {
        this.milliSeconds = (1.0f / seconds) * 1000;

    }

    //returns the current system nano time
    private static final long GetCurrentTime() {
        return (System.nanoTime() / 1000000);

    }

    //Resets the timer
    public void ResetTimer() {
        this.totalCycles = 0;
        this.untilNextCycle = 0.0f;

        this.lastUpdate = GetCurrentTime();
        this.isPaused = false;

    }

    //Updates the timer
    public void UpdateTimer() {
        long nowTime = GetCurrentTime();
        float frametime = (float) (nowTime - lastUpdate) + untilNextCycle;

        if (!isPaused) {
            this.totalCycles += (int) frametime / milliSeconds;
            this.untilNextCycle = frametime % milliSeconds;
        }

        this.lastUpdate = nowTime;
    }

    //pauses the timer
    public void setPaused(boolean paused) {
        this.isPaused = paused;

    }

    //returns true if a cycle has been completed
    public boolean cycleComplete() {
        if (totalCycles > 0) {
            this.totalCycles--;
            return true;
        }
        return false;
    }

}
