package com.allwayup.processor;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;

public class LinkMqProcessor extends AbstractProcessor {

    private final static Set<String> SUPPORTED_ANNOTATION_TYPES = Set.of("*");
    private final static SourceVersion SUPPORTED_SOURCE_VERSION = SourceVersion.latestSupported();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> rootElements = roundEnv.getRootElements();
        for (Element rootElement : rootElements) {
            System.out.println(rootElement.getKind());
            System.out.println(rootElement.getSimpleName());

            List<? extends Element> enclosedElements = rootElement.getEnclosedElements();
            for (Element enclosedElement : enclosedElements) {
                System.out.println(enclosedElement.getKind());
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATION_TYPES;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SUPPORTED_SOURCE_VERSION;
    }
}
