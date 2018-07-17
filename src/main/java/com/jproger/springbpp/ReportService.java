package com.jproger.springbpp;

import com.jproger.springbpp.reports.DocumentType;
import com.jproger.springbpp.reports.ReportCompiler;
import com.jproger.springbpp.reports.ReportCompilerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportCompilerFactory reportCompilerFactory;

    public ReportService(ReportCompilerFactory reportCompilerFactory) {
        this.reportCompilerFactory = reportCompilerFactory;
    }

    public String createReport(DocumentType documentType) {
        return reportCompilerFactory.getReportCompiler(documentType)
                .map(ReportCompiler::compile)
                .orElse("");
    }
}
