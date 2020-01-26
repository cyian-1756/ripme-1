package com.rarchives.ripme.tst.ripper.rippers;

import com.rarchives.ripme.ripper.rippers.HentaidudeRipper;

import java.io.IOException;
import java.net.URL;

public class HentaidudeRipperTest extends RippersTest{

    public void testRip() throws IOException {
        HentaidudeRipper ripper = new HentaidudeRipper(new URL("https://hentaidude.com/girlfriends-4ever-dlc-2/"));
        testRipper(ripper);

    }



}