package com.eservia.glide.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


final class RequestManagerFactoryGenerator {
  private static final String GLIDE_QUALIFIED_NAME = "com.eservia.glide.Glide";
  private static final String LIFECYCLE_QUALIFIED_NAME = "com.eservia.glide.manager.Lifecycle";
  private static final String REQUEST_MANAGER_TREE_NODE_QUALIFIED_NAME =
      "com.eservia.glide.manager.RequestManagerTreeNode";
  private static final String REQUEST_MANAGER_FACTORY_QUALIFIED_NAME =
      "com.eservia.glide.manager.RequestManagerRetriever.RequestManagerFactory";
  private static final String REQUEST_MANAGER_QUALIFIED_NAME = "com.eservia.glide.RequestManager";
  private static final ClassName CONTEXT_CLASS_NAME = ClassName.get("android.content", "Context");

  static final String GENERATED_REQUEST_MANAGER_FACTORY_PACKAGE_NAME = "com.eservia.glide";
  static final String GENERATED_REQUEST_MANAGER_FACTORY_SIMPLE_NAME =
      "GeneratedRequestManagerFactory";

  private final TypeElement glideType;
  private final TypeElement lifecycleType;
  private final TypeElement requestManagerTreeNodeType;
  private final TypeElement requestManagerFactoryInterface;
  private final ClassName requestManagerClassName;
  private final ProcessorUtil processorUtil;

  RequestManagerFactoryGenerator(ProcessingEnvironment processingEnv, ProcessorUtil processorUtil) {
    this.processorUtil = processorUtil;
    Elements elementUtils = processingEnv.getElementUtils();
    glideType = elementUtils.getTypeElement(GLIDE_QUALIFIED_NAME);
    lifecycleType = elementUtils.getTypeElement(LIFECYCLE_QUALIFIED_NAME);
    requestManagerTreeNodeType =
        elementUtils.getTypeElement(REQUEST_MANAGER_TREE_NODE_QUALIFIED_NAME);

    requestManagerFactoryInterface =
        elementUtils.getTypeElement(REQUEST_MANAGER_FACTORY_QUALIFIED_NAME);

    TypeElement requestManagerType = elementUtils.getTypeElement(REQUEST_MANAGER_QUALIFIED_NAME);
    requestManagerClassName = ClassName.get(requestManagerType);
  }

  TypeSpec generate(String generatedCodePackageName, TypeSpec generatedRequestManagerSpec) {
    return TypeSpec.classBuilder(GENERATED_REQUEST_MANAGER_FACTORY_SIMPLE_NAME)
        .addModifiers(Modifier.FINAL)
        .addSuperinterface(ClassName.get(requestManagerFactoryInterface))
        .addJavadoc("Generated code, do not modify\n")
        .addMethod(
            MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addAnnotation(processorUtil.nonNull())
                .returns(requestManagerClassName)
                .addParameter(
                    ParameterSpec.builder(ClassName.get(glideType), "glide")
                        .addAnnotation(processorUtil.nonNull())
                        .build())
                .addParameter(
                    ParameterSpec.builder(ClassName.get(lifecycleType), "lifecycle")
                        .addAnnotation(processorUtil.nonNull())
                        .build())
                .addParameter(
                    ParameterSpec.builder(ClassName.get(requestManagerTreeNodeType), "treeNode")
                        .addAnnotation(processorUtil.nonNull())
                        .build())
                .addParameter(
                    ParameterSpec.builder(CONTEXT_CLASS_NAME, "context")
                        .addAnnotation(processorUtil.nonNull())
                        .build())
                .addStatement(
                    "return new $T(glide, lifecycle, treeNode, context)",
                    ClassName.get(generatedCodePackageName, generatedRequestManagerSpec.name))
                .build())
        .build();
  }
}
