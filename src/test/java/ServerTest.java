import com.company.domain.User;
import com.company.service.ClintService;
import com.company.service.ServerService;
import org.junit.Test;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class ServerTest {
    @Test
    public void testMSG() throws Exception{

    }
    @Test
    public void receive() throws Exception{
    }
}
/*
* [123, 34, 109, 115, 103, 34, 58, 34, 49, 34, 44, 34, 112, 111, 114, 116, 34, 58, 34, 53, 54, 56, 51, 56, 34, 44, 34, 102, 114, 111, 109, 34, 58, 34, -27, -113, -72, -25, -69, -76, -25, -69, -76, 34, 44, 34, 116, 111, 34, 58, 34, -25, -114, -117, -25, -100, -97, -23, -102, -66, 34, 44, 34, 116, 105, 109, 101, 34, 58, 34, 50, 48, 50, 48, 47, 53, 47, 50, 48, 32, 49, 49, 58, 52, 48, 34, 125]
* [123, 34, 109, 115, 103, 34, 58, 34, 49, 34, 44, 34, 112, 111, 114, 116, 34, 58, 34, 53, 54, 56, 51, 56, 34, 44, 34, 102, 114, 111, 109, 34, 58, 34, -27, -113, -72, -25, -69, -76, -25, -69, -76, 34, 44, 34, 116, 111, 34, 58, 34, -25, -114, -117, -25, -100, -97, -23, -102, -66, 34, 44, 34, 116, 105, 109, 101, 34, 58, 34, 50, 48, 50, 48, 47,
* */