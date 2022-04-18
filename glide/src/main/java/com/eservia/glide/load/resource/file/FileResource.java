package com.eservia.glide.load.resource.file;

import com.eservia.glide.load.resource.SimpleResource;
import java.io.File;


@SuppressWarnings("WeakerAccess")
public class FileResource extends SimpleResource<File> {
  public FileResource(File file) {
    super(file);
  }
}
