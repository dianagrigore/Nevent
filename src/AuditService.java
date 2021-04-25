import java.io.*;
import java.util.Date;

public class AuditService {
    private static AuditService instance = null;
    public static AuditService getInstance() {
        if (instance == null){
            instance = new AuditService();
        }
        return instance;
    }
    private static BufferedWriter bufferedWriter;
    private static final String LOG_NAME = "./resource/audit_output.csv";
    public AuditService(){
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(LOG_NAME, true));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void log(String message) throws IOException {
        Date current_date = new Date();
        bufferedWriter.write(message + ", " + current_date + '\n');
    }

    public void close() throws IOException {
        bufferedWriter.flush();
        bufferedWriter.close();
    }

}
