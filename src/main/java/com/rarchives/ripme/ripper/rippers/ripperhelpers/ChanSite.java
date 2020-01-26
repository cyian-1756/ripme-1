package com.rarchives.ripme.ripper.rippers.ripperhelpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChanSite {
    // The domains where the threads are hosted.
    public List<String> domains;
    // The domains where the images are hosted.
    public List<String> cdnDomains;

    public ChanSite(List<String> Domains, List<String> CdnDomains) {
        if (Domains.isEmpty()) {
            throw new IllegalArgumentException("Domains");
        }
        if (CdnDomains.isEmpty()) {
            throw new IllegalArgumentException("CdnDomains");
        }
        domains = Domains;
        cdnDomains = CdnDomains;
    }

    public ChanSite(String Domain, List<String> CdnDomains) {
        if (Domain.isEmpty()) {
            throw new IllegalArgumentException("Domains");
        }
        if (CdnDomains.isEmpty()) {
            throw new IllegalArgumentException("CdnDomains");
        }
        domains = Collections.singletonList(Domain);
        cdnDomains = CdnDomains;
    }

    public ChanSite(String Domain, String CdnDomain) {
        if (Domain.isEmpty()) {
            throw new IllegalArgumentException("Domains");
        }
        if (CdnDomain.isEmpty()) {
            throw new IllegalArgumentException("CdnDomains");
        }
        domains = Collections.singletonList(Domain);
        cdnDomains = Collections.singletonList(CdnDomain);
    }

    public ChanSite(String Domain) {
        if (Domain.isEmpty()) {
            throw new IllegalArgumentException("Domains");
        }
        domains = Collections.singletonList(Domain);
        cdnDomains = Collections.singletonList(Domain);
    }

    public ChanSite(List<String> Domains) {
        if (Domains.isEmpty()) {
            throw new IllegalArgumentException("Domains");
        }
        domains = Domains;
        cdnDomains = Domains;
    }
    public List<String> getDomains() {
        return domains;
    }

    public List<String> getCdns() {
        return cdnDomains;
    }
}
