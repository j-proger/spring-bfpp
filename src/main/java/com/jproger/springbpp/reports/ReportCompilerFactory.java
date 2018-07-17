package com.jproger.springbpp.reports;

import java.util.Optional;

public interface ReportCompilerFactory {
    Optional<ReportCompiler> getReportCompiler(DocumentType documentType);
}
