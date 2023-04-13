package com.zagvladimir.service.pdf;

import com.zagvladimir.domain.ItemLeased;

import java.util.Set;

public interface PDFService {
    String generateAnInvoice(Set<ItemLeased> itemLeased, Long renterId);
}
