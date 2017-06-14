package whizz.me.calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.mockito.runners.MockitoJUnitRunner;

import whizz.me.calculator.utils.PasteUtils;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class PastUtilsTest {
    @Test
    public void pastInto_Test() throws Exception{
        String oldM = "Hello";
        String newM = "Apple";

        assertArrayEquals(PasteUtils.pastInto(oldM,newM,0).toCharArray(),"AppleHello".toCharArray());
        assertArrayEquals(PasteUtils.pastInto(oldM,newM,1).toCharArray(),"HAppleello".toCharArray());
        assertArrayEquals(PasteUtils.pastInto(oldM,newM,2).toCharArray(),"HeApplello".toCharArray());
        assertArrayEquals(PasteUtils.pastInto(oldM,newM,oldM.length()).toCharArray(),"HelloApple".toCharArray());
    }


    @Test
    public void removeBefore_Test() throws Exception{
        assertArrayEquals(PasteUtils.removeBefore("hello",1).toCharArray(),"ello".toCharArray());
        assertArrayEquals(PasteUtils.removeBefore("hello",2).toCharArray(),"hllo".toCharArray());
    }
}