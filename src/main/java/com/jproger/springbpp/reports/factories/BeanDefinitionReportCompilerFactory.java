package com.jproger.springbpp.reports.factories;

import com.jproger.springbpp.reports.DocumentType;
import com.jproger.springbpp.reports.ReportCompiler;
import com.jproger.springbpp.reports.ReportCompilerFactory;
import com.jproger.springbpp.reports.ReportCompilerTarget;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class BeanDefinitionReportCompilerFactory implements ReportCompilerFactory, BeanFactoryPostProcessor, BeanFactoryAware {
    private final Map<DocumentType, Class<? extends ReportCompiler>> beanClasses = new HashMap<>();
    private BeanFactory beanFactory;

    @Override
    public Optional<ReportCompiler> getReportCompiler(DocumentType documentType) {
        return Optional.ofNullable(beanClasses.get(documentType))
                .map(beanFactory::getBean);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            String beanClassName = beanFactory.getBeanDefinition(beanDefinitionName).getBeanClassName();

            if (Objects.nonNull(beanClassName)) {
                handleBeanClassName(beanClassName);
            }
        }
    }

    private void handleBeanClassName(String beanClassName) throws BeansException {
        try {
            Class<?> clazz = Class.forName(beanClassName);

            if (ReportCompiler.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(ReportCompilerTarget.class)) {
                rememberReportCompilerClass((Class<? extends ReportCompiler>) clazz);
            }
        } catch (ClassNotFoundException e) {
            throw new FatalBeanException("Bean " + beanClassName + " not found", e);
        }
    }

    private void rememberReportCompilerClass(Class<? extends ReportCompiler> reportCompilerClass) {
        DocumentType documentType = reportCompilerClass.getAnnotation(ReportCompilerTarget.class).documentType();

        beanClasses.put(documentType, reportCompilerClass);
    }
}
