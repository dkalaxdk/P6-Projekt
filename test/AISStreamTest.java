
import Dibbidut.Classes.AISBuffer;
import Dibbidut.Classes.AISData;
import Dibbidut.Classes.AISSource;
import Dibbidut.Classes.AISStream;
import Dibbidut.Interfaces.IDataInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AISStreamTest {

    @Test
    public void add_shouldAddElementToStream() {

        AISSource source = new AISSource();
        AISStream stream = new AISStream(source);

    }
}
