package Server;

public class ServerTimer extends Thread{
    private final long loopTime; //Thời gian quét sau mỗi n phút
    private final long sessionTime; //Thời gian cho 1 phiên làm việc của Client

    public ServerTimer(long loopTime, long sessionTime) {
        //1 phút = 60 * 1000 millisecond
        this.loopTime = loopTime * 60 * 1000;
        this.sessionTime = sessionTime * 60 * 1000;
    }

    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(loopTime); //10min
                long currentTime = System.currentTimeMillis();
                for (User user : Server.users) {
                    long userTime = user.getSessionTime();
                    if (userTime == 0)
                        continue;

                    if (currentTime - userTime >= (sessionTime)) { //60min
                        user.setSecretKey("Expired");
                        user.setSessionTime(0);
                        Server.users.remove(user);
                        Server.users.add(user);
                    } else break;
                }
            } catch (InterruptedException ignored) {}
        }
    }
}
