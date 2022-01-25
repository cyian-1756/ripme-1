package com.rarchives.ripme.tst.ripper.rippers;

import java.io.IOException;
import java.net.URL;

import com.rarchives.ripme.ripper.rippers.FapomaniaRipper;

import org.junit.jupiter.api.Test;

public class FapomaniaRipperTest extends RippersTest {
    @Test
    public void testRip() throws IOException {
        FapomaniaRipper ripper = new FapomaniaRipper(new URL(
                "https://fapomania.com/laura-harrier/"));
        testRipper(ripper);
    }
}
