package com.jproger.springbpp.reports.compilers;

import com.jproger.springbpp.reports.DocumentType;
import com.jproger.springbpp.reports.ReportCompiler;
import com.jproger.springbpp.reports.ReportCompilerTarget;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@ReportCompilerTarget(documentType = DocumentType.HTML)
public class HtmlReportCompiler implements ReportCompiler {

    @Override
    public String compile() {
        return "";
    }
}
