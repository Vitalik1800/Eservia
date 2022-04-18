package com.eservia.glide.compiler;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

// Links in Javadoc will work due to build setup, even though there is no direct dependency here.
/**
 * Generates classes based on Glide's annotations that configure Glide, add support for additional
 * resource types, and/or extend Glide's API.
 *

 * <p>Multiple classes are generated by this processor:
 *

 *
 * <p>{@code AppGlideModule} implementations must only be included in applications, not in
 * libraries. There must be exactly one {@code AppGlideModule} implementation per Application. The
 * {@code AppGlideModule} class is used as a signal that all modules have been found and that the
 */
@AutoService(Processor.class)
public final class GlideAnnotationProcessor extends AbstractProcessor {
  static final boolean DEBUG = false;
  private ProcessorUtil processorUtil;
  private LibraryModuleProcessor libraryModuleProcessor;
  private AppModuleProcessor appModuleProcessor;
  private boolean isGeneratedAppGlideModuleWritten;
  private ExtensionProcessor extensionProcessor;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    processorUtil = new ProcessorUtil(processingEnvironment);
    IndexerGenerator indexerGenerator = new IndexerGenerator(processorUtil);
    libraryModuleProcessor = new LibraryModuleProcessor(processorUtil, indexerGenerator);
    appModuleProcessor = new AppModuleProcessor(processingEnvironment, processorUtil);
    extensionProcessor =
        new ExtensionProcessor(processingEnvironment, processorUtil, indexerGenerator);
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> result = new HashSet<>();
    result.addAll(libraryModuleProcessor.getSupportedAnnotationTypes());
    result.addAll(extensionProcessor.getSupportedAnnotationTypes());
    return result;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  /**
   * Each round we do the following:
   *
   * <ol>
   *   <li>Find all {@code AppGlideModule}s and save them to an instance variable (throw if > 1).
   *   <li>Find all {@code LibraryGlideModule}s
   *   <li>For each {@code LibraryGlideModule}, write an {@code Indexer} with an Annotation with the
   *       class name.
   *   <li>If we wrote any {@code Indexer}s, return and wait for the next round.
   *   <li>If we didn't write any {@code Indexer}s and there is a {@code AppGlideModule}, write the
   *       {@code GeneratedAppGlideModule}. Once the {@code GeneratedAppGlideModule} is written, we
   *       expect to be finished. Any further generation of related classes will result in errors.
   * </ol>
   */
  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
    processorUtil.process();
    boolean newModulesWritten = libraryModuleProcessor.processModules(env);
    boolean newExtensionWritten = extensionProcessor.processExtensions(env);
    appModuleProcessor.processModules(set, env);

    if (newExtensionWritten || newModulesWritten) {
      if (isGeneratedAppGlideModuleWritten) {
        throw new IllegalStateException("Cannot process annotations after writing AppGlideModule");
      }
      return false;
    }

    if (!isGeneratedAppGlideModuleWritten) {
      isGeneratedAppGlideModuleWritten = appModuleProcessor.maybeWriteAppModule();
    }
    return false;
  }
}