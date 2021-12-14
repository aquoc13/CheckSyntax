package Server;

public class ServerTimer extends Thread{
    private final long loopTime; //Thời gian quét sau mỗi n phút
    private final long sessionTime; //Thời gian cho 1 phiên làm việc của Client

    public ServerTimer(long loopTime, long sessionTime) {
        //1 phút = 60 * 1000 millisecond
        this.loopTime = loopTime * 60 * 1000;
        this.sessionTime = sessionTime * 60 * 1000;
    }

    @SuppressWarnings({"BusyWait"})
    @Override
    public void run() {
        try {
            while (!interrupted()) {
                Thread.sleep(minSessionTime()); //ngủ

                long currentTime = System.currentTimeMillis();
                //Thực hiện quét
                for (User user : Server.users) {
                    long userTime = user.getSessionTime();
                    if (userTime == 0) //bỏ qua nếu session time là 0
                        continue;

                    if (currentTime - userTime >= (sessionTime)) { //session time quá 60 phút thì set Expired
                        user.setSecretKey("Expired");
                        user.setSessionTime(0);
                        if (Server.users.remove(user))
                            Server.users.add(user);

                        ServerListener.recheckIfTargetAtManager(user); //(bỏ qua)
                    } else break;
                }
            }
        } catch (InterruptedException ignored) {}
    }

    public long minSessionTime() {
        long min = sessionTime; //60min
        long currentTime = System.currentTimeMillis();
        for (User user : Server.users) {
            long userTime = currentTime - user.getSessionTime();
            if (userTime == 0) //bỏ qua nếu session time là 0
                continue;
            if (userTime < min)
                min = userTime;
        }
        if (min == 0 || Server.users.isEmpty())
            min = loopTime;
        //System.out.println(min);
        return min;
    }

    public void cancel() {
        interrupt();
    }
}
