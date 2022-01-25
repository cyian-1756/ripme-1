package com.rarchives.ripme.ripper.rippers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.rarchives.ripme.ripper.AbstractHTMLRipper;
import com.rarchives.ripme.utils.Http;

public class FapomaniaRipper extends AbstractHTMLRipper {
    String lastPage;

    public FapomaniaRipper(URL url) throws IOException {
        super(url);
    }

    @Override
    public String getHost() {
        return "fapomania";
    }

    @Override
    public String getDomain() {
        return "fapomania.com";
    }

    @Override
    public String getGID(URL url) throws MalformedURLException {
        Pattern p = Pattern.compile("https?://fapomania\\.com/([a-zA-Z0-9_-]*)/?");
        Matcher m = p.matcher(url.toExternalForm());
        if (m.matches()) {
            return m.group(1);
        }
        throw new MalformedURLException("Expected fapomania.com URL format: " +
                "fapomania.com/COMICID - got " + url + " instead");
    }

    @Override
    public Document getFirstPage() throws IOException {
        try {
            return Http.url(url).get();
        } catch (IOException e) {
            throw new IOException("Unable to get first page");
        }
    }

    @Override
    public List<String> getURLsFromPage(Document doc) {
        List<String> result = new ArrayList<String>();
        for (Element el : doc.select("div.leftocontar > div.previzakosblo > div.previzako > a > div > img")) {
            String imageURL = el.attr("src").replaceAll("_300px", "");
            result.add(imageURL);
        }
        return result;
    }

    @Override
    public void downloadURL(URL url, int index) {
        addURLToDownload(url, getPrefix(index));
    }
}
