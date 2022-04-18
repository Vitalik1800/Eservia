package com.eservia.glide.compiler;

import com.eservia.glide.annotation.GlideExtension;
import com.squareup.javapoet.TypeSpec;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;


final class ExtensionProcessor {
  private final ProcessorUtil processorUtil;
  private final IndexerGenerator indexerGenerator;
  private final GlideExtensionValidator extensionValidator;

  ExtensionProcessor(
      ProcessingEnvironment processingEnvironment,
      ProcessorUtil processorUtil,
      IndexerGenerator indexerGenerator) {
    this.processorUtil = processorUtil;
    this.indexerGenerator = indexerGenerator;
    extensionValidator = new GlideExtensionValidator(processingEnvironment, processorUtil);
  }

  boolean processExtensions(RoundEnvironment env) {
    List<TypeElement> elements = processorUtil.getElementsFor(GlideExtension.class, env);
    processorUtil.debugLog("Processing types : " + elements);
    for (TypeElement typeElement : elements) {
      extensionValidator.validateExtension(typeElement);
      processorUtil.debugLog("Processing elements: " + typeElement.getEnclosedElements());
    }

    if (elements.isEmpty()) {
      return false;
    }
    TypeSpec spec = indexerGenerator.generate(elements);
    processorUtil.writeIndexer(spec);
    return true;
  }

  Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(GlideExtension.class.getName());
  }
}
