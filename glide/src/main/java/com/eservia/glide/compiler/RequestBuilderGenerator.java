package com.eservia.glide.compiler;

import androidx.annotation.Nullable;

import com.eservia.glide.annotation.GlideExtension;
import com.eservia.glide.annotation.GlideOption;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Function;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Joiner;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Predicate;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.FluentIterable;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableSet;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Lists;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

final class RequestBuilderGenerator {
  private static final String REQUEST_OPTIONS_PACKAGE_NAME = "com.eservia.glide.request";
  private static final String REQUEST_OPTIONS_SIMPLE_NAME = "RequestOptions";
  private static final String REQUEST_OPTIONS_QUALIFIED_NAME =
      REQUEST_OPTIONS_PACKAGE_NAME + "." + REQUEST_OPTIONS_SIMPLE_NAME;

  private static final String REQUEST_BUILDER_PACKAGE_NAME = "com.eservia.glide";
  private static final String REQUEST_BUILDER_SIMPLE_NAME = "RequestBuilder";
  static final String REQUEST_BUILDER_QUALIFIED_NAME =
      REQUEST_BUILDER_PACKAGE_NAME + "." + REQUEST_BUILDER_SIMPLE_NAME;

  // Uses package private methods and variables.
  private static final String GENERATED_REQUEST_BUILDER_SIMPLE_NAME = "GlideRequest";

  /**
   * An arbitrary name of the Generic type in the generated RequestBuilder. e.g.
   * RequestBuilder<TranscodeType>
   */
  private static final String TRANSCODE_TYPE_NAME = "TranscodeType";
  /** A set of method names to avoid overriding from RequestOptions. */
  private static final ImmutableSet<String> EXCLUDED_METHODS_FROM_BASE_REQUEST_OPTIONS =
      ImmutableSet.of("clone", "apply");

  private final ProcessingEnvironment processingEnv;
  private final ProcessorUtil processorUtil;
  private final TypeVariableName transcodeTypeName;
  private final TypeElement requestOptionsType;
  private final TypeElement requestBuilderType;
  private ClassName generatedRequestBuilderClassName;
  private ClassName requestOptionsClassName;
  private ParameterizedTypeName generatedRequestBuilderOfTranscodeType;

  RequestBuilderGenerator(ProcessingEnvironment processingEnv, ProcessorUtil processorUtil) {
    this.processingEnv = processingEnv;
    this.processorUtil = processorUtil;

    requestBuilderType =
        processingEnv.getElementUtils().getTypeElement(REQUEST_BUILDER_QUALIFIED_NAME);

    transcodeTypeName = TypeVariableName.get(TRANSCODE_TYPE_NAME);

    requestOptionsType =
        processingEnv.getElementUtils().getTypeElement(REQUEST_OPTIONS_QUALIFIED_NAME);
  }

  TypeSpec generate(
      String generatedCodePackageName,
      Set<String> glideExtensionClassNames,
      @Nullable TypeSpec generatedOptions) {
    if (generatedOptions != null) {
      requestOptionsClassName = ClassName.get(generatedCodePackageName, generatedOptions.name);
    } else {
      requestOptionsClassName =
          ClassName.get(
              RequestOptionsGenerator.REQUEST_OPTIONS_PACKAGE_NAME,
              RequestOptionsGenerator.BASE_REQUEST_OPTIONS_SIMPLE_NAME);
    }

    generatedRequestBuilderClassName =
        ClassName.get(generatedCodePackageName, GENERATED_REQUEST_BUILDER_SIMPLE_NAME);
    generatedRequestBuilderOfTranscodeType =
        ParameterizedTypeName.get(generatedRequestBuilderClassName, transcodeTypeName);
    RequestOptionsExtensionGenerator requestOptionsExtensionGenerator =
        new RequestOptionsExtensionGenerator(generatedRequestBuilderOfTranscodeType, processorUtil);

    ParameterizedTypeName requestBuilderOfTranscodeType =
        ParameterizedTypeName.get(
            ClassName.get(REQUEST_BUILDER_PACKAGE_NAME, REQUEST_BUILDER_SIMPLE_NAME),
            transcodeTypeName);

    List<MethodSpec> requestOptionsExtensionMethods =
        requestOptionsExtensionGenerator.generateInstanceMethodsForExtensions(
            glideExtensionClassNames);

    return TypeSpec.classBuilder(GENERATED_REQUEST_BUILDER_SIMPLE_NAME)
        .addJavadoc(
            "Contains all public methods from {@link $T}, all options from\n", requestBuilderType)
        .addJavadoc("{@link $T} and all generated options from\n", requestOptionsType)
        .addJavadoc("{@link $T} in annotated methods in\n", GlideOption.class)
        .addJavadoc("{@link $T} annotated classes.\n", GlideExtension.class)
        .addJavadoc("\n")
        .addJavadoc("<p>Generated code, do not modify.\n")
        .addJavadoc("\n")
        .addJavadoc("@see $T\n", requestBuilderType)
        .addJavadoc("@see $T\n", requestOptionsType)
        .addAnnotation(
            AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unused")
                .addMember("value", "$S", "deprecation")
                .build())
        .addModifiers(Modifier.PUBLIC)
        .addTypeVariable(transcodeTypeName)
        .superclass(requestBuilderOfTranscodeType)
        .addSuperinterface(Cloneable.class)
        .addMethods(generateConstructors())
        .addMethod(generateDownloadOnlyRequestMethod())
        .addMethods(
            generateGeneratedRequestOptionsEquivalents(
                requestOptionsExtensionMethods, generatedOptions))
        .addMethods(generateRequestBuilderOverrides())
        .addMethods(requestOptionsExtensionMethods)
        .build();
  }

  private List<MethodSpec> generateGeneratedRequestOptionsEquivalents(
      final List<MethodSpec> requestOptionsExtensionMethods,
      @Nullable final TypeSpec generatedOptions) {
    if (generatedOptions == null) {
      return Collections.emptyList();
    }
    return FluentIterable.from(generatedOptions.methodSpecs)
        .filter(
            new Predicate<MethodSpec>() {
              @Override
              public boolean apply(MethodSpec input) {
                return isUsefulGeneratedRequestOption(requestOptionsExtensionMethods, input);
              }
            })
        .transform(
            new Function<MethodSpec, MethodSpec>() {
              @Override
              public MethodSpec apply(MethodSpec input) {
                return generateGeneratedRequestOptionEquivalent(input);
              }
            })
        .toList();
  }

  private boolean isUsefulGeneratedRequestOption(
      List<MethodSpec> requestOptionsExtensionMethods, final MethodSpec requestOptionsMethod) {
    return !EXCLUDED_METHODS_FROM_BASE_REQUEST_OPTIONS.contains(requestOptionsMethod.name)
        && requestOptionsMethod.hasModifier(Modifier.PUBLIC)
        && !requestOptionsMethod.hasModifier(Modifier.STATIC)
        && requestOptionsMethod.returnType.toString().equals(requestOptionsClassName.toString())
        && !isExtensionMethod(requestOptionsExtensionMethods, requestOptionsMethod);
  }

  private boolean isExtensionMethod(
      List<MethodSpec> requestOptionsExtensionMethods, final MethodSpec requestOptionsMethod) {
    return FluentIterable.from(requestOptionsExtensionMethods)
        .anyMatch(
            new Predicate<MethodSpec>() {
              @Override
              public boolean apply(MethodSpec input) {
                return input.name.equals(requestOptionsMethod.name)
                    && input.parameters.equals(requestOptionsMethod.parameters);
              }
            });
  }

  private MethodSpec generateGeneratedRequestOptionEquivalent(MethodSpec requestOptionMethod) {
    CodeBlock callRequestOptionsMethod =
        CodeBlock.builder()
            .add(".$N(", requestOptionMethod.name)
            .add(
                FluentIterable.from(requestOptionMethod.parameters)
                    .transform(
                        new Function<ParameterSpec, String>() {
                          @Override
                          public String apply(ParameterSpec input) {
                            return input.name;
                          }
                        })
                    .join(Joiner.on(", ")))
            .add(");\n")
            .build();

    MethodSpec.Builder result =
        MethodSpec.methodBuilder(requestOptionMethod.name)
            .addJavadoc(
                processorUtil.generateSeeMethodJavadoc(
                    requestOptionsClassName, requestOptionMethod))
            .addModifiers(Modifier.PUBLIC)
            .varargs(requestOptionMethod.varargs)
            .addAnnotations(
                FluentIterable.from(requestOptionMethod.annotations)
                    .filter(
                        new Predicate<AnnotationSpec>() {
                          @Override
                          public boolean apply(AnnotationSpec input) {
                            return !input.type.equals(TypeName.get(Override.class))
                                // SafeVarargs can only be applied to final methods. GlideRequest is
                                // non-final to allow for mocking.
                                && !input.type.equals(TypeName.get(SafeVarargs.class))
                                // We need to combine warnings below.
                                && !input.type.equals(TypeName.get(SuppressWarnings.class));
                          }
                        })
                    .toList())
            .addTypeVariables(requestOptionMethod.typeVariables)
            .addParameters(requestOptionMethod.parameters)
            .returns(generatedRequestBuilderOfTranscodeType)
            .addCode("return ($T) super", generatedRequestBuilderOfTranscodeType)
            .addCode(callRequestOptionsMethod);

    AnnotationSpec suppressWarnings = buildSuppressWarnings(requestOptionMethod);
    if (suppressWarnings != null) {
      result.addAnnotation(suppressWarnings);
    }
    return result.build();
  }

  @Nullable
  private AnnotationSpec buildSuppressWarnings(MethodSpec requestOptionMethod) {
    Set<String> suppressions = new HashSet<>();
    if (requestOptionMethod.annotations.contains(
        AnnotationSpec.builder(SuppressWarnings.class).build())) {
      for (AnnotationSpec annotation : requestOptionMethod.annotations) {
        if (annotation.type.equals(TypeName.get(SuppressWarnings.class))) {
          List<CodeBlock> codeBlocks = annotation.members.get("value");
          suppressions.addAll(
              FluentIterable.from(codeBlocks)
                  .transform(
                      new Function<CodeBlock, String>() {
                        @Override
                        public String apply(CodeBlock input) {
                          return input.toString();
                        }
                      })
                  .toSet());
        }
      }
    }

    if (requestOptionMethod.annotations.contains(
        AnnotationSpec.builder(SafeVarargs.class).build())) {
      suppressions.add("unchecked");
      suppressions.add("varargs");
    }

    if (suppressions.isEmpty()) {
      return null;
    }
    // Enforce ordering across compilers (Internal and External compilers end up disagreeing on the
    // order produced by the Set additions above.)
    ArrayList<String> suppressionsList = new ArrayList<>(suppressions);
    Collections.sort(suppressionsList);

    AnnotationSpec.Builder builder = AnnotationSpec.builder(SuppressWarnings.class);
    for (String suppression : suppressionsList) {
      builder.addMember("value", "$S", suppression);
    }

    return builder.build();
  }

  private List<MethodSpec> generateRequestBuilderOverrides() {
    TypeMirror rawRequestBuilderType =
        processingEnv.getTypeUtils().erasure(requestBuilderType.asType());
    return Lists.transform(
        processorUtil.findInstanceMethodsReturning(requestBuilderType, rawRequestBuilderType),
        new Function<ExecutableElement, MethodSpec>() {
          @Override
          public MethodSpec apply(ExecutableElement input) {
            return generateRequestBuilderOverride(input);
          }
        });
  }

  private MethodSpec generateRequestBuilderOverride(ExecutableElement methodToOverride) {
    // We've already verified that this method returns a RequestBuilder and RequestBuilders have
    // exactly one type argument, so this is safe unless those assumptions change.
    TypeMirror typeArgument =
        ((DeclaredType) methodToOverride.getReturnType()).getTypeArguments().get(0);

    ParameterizedTypeName generatedRequestBuilderOfType =
        ParameterizedTypeName.get(generatedRequestBuilderClassName, ClassName.get(typeArgument));

    MethodSpec.Builder builder =
        processorUtil.overriding(methodToOverride).returns(generatedRequestBuilderOfType);
    builder.addCode(
        CodeBlock.builder()
            .add(
                "return ($T) super.$N(",
                generatedRequestBuilderOfType,
                methodToOverride.getSimpleName())
            .add(
                FluentIterable.from(builder.build().parameters)
                    .transform(
                        new Function<ParameterSpec, String>() {
                          @Override
                          public String apply(ParameterSpec input) {
                            return input.name;
                          }
                        })
                    .join(Joiner.on(", ")))
            .add(");\n")
            .build());

    for (AnnotationMirror mirror : methodToOverride.getAnnotationMirrors()) {
      builder = builder.addAnnotation(AnnotationSpec.get(mirror));
    }

    if (methodToOverride.isVarArgs()) {
      builder =
          builder
              .addModifiers(Modifier.FINAL)
              .addAnnotation(SafeVarargs.class)
              .addAnnotation(
                  AnnotationSpec.builder(SuppressWarnings.class)
                      .addMember("value", "$S", "varargs")
                      .build());
    }

    return builder.build();
  }

  private List<MethodSpec> generateConstructors() {
    ParameterizedTypeName classOfTranscodeType =
        ParameterizedTypeName.get(ClassName.get(Class.class), transcodeTypeName);

    TypeName wildcardOfObject = WildcardTypeName.subtypeOf(Object.class);
    ParameterizedTypeName requestBuilderOfWildcardOfObject =
        ParameterizedTypeName.get(ClassName.get(requestBuilderType), wildcardOfObject);

    MethodSpec firstConstructor =
        MethodSpec.constructorBuilder()
            .addParameter(
                ParameterSpec.builder(classOfTranscodeType, "transcodeClass")
                    .addAnnotation(processorUtil.nonNull())
                    .build())
            .addParameter(
                ParameterSpec.builder(requestBuilderOfWildcardOfObject, "other")
                    .addAnnotation(processorUtil.nonNull())
                    .build())
            .addStatement("super($N, $N)", "transcodeClass", "other")
            .build();

    ClassName context = ClassName.get("android.content", "Context");
    ClassName glide = ClassName.get("com.eservia.glide", "Glide");
    ClassName requestManager = ClassName.get("com.eservia.glide", "RequestManager");
    MethodSpec secondConstructor =
        MethodSpec.constructorBuilder()
            .addParameter(
                ParameterSpec.builder(glide, "glide")
                    .addAnnotation(processorUtil.nonNull())
                    .build())
            .addParameter(
                ParameterSpec.builder(requestManager, "requestManager")
                    .addAnnotation(processorUtil.nonNull())
                    .build())
            .addParameter(
                ParameterSpec.builder(classOfTranscodeType, "transcodeClass")
                    .addAnnotation(processorUtil.nonNull())
                    .build())
            .addParameter(
                ParameterSpec.builder(context, "context")
                    .addAnnotation(processorUtil.nonNull())
                    .build())
            .addStatement(
                "super($N, $N ,$N, $N)", "glide", "requestManager", "transcodeClass", "context")
            .build();
    return ImmutableList.of(firstConstructor, secondConstructor);
  }

  private MethodSpec generateDownloadOnlyRequestMethod() {
    ParameterizedTypeName generatedRequestBuilderOfFile =
        ParameterizedTypeName.get(generatedRequestBuilderClassName, ClassName.get(File.class));
    return MethodSpec.methodBuilder("getDownloadOnlyRequest")
        .addAnnotation(Override.class)
        .addAnnotation(processorUtil.checkResult())
        .addAnnotation(processorUtil.nonNull())
        .returns(generatedRequestBuilderOfFile)
        .addModifiers(Modifier.PROTECTED)
        .addStatement(
            "return new $T<>($T.class, $N).apply($N)",
            generatedRequestBuilderClassName,
            File.class,
            "this",
            "DOWNLOAD_ONLY_OPTIONS")
        .build();
  }
}
