package com.hzth.myapp.jdk7;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CustomFileVisitor extends SimpleFileVisitor<Path> {

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		System.out.println("find file:" + file.toFile().getName());
		System.out.println("attrs:" + attrs);
		System.out.println(attrs.creationTime().toString());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		System.out.println("find dir:" + dir.toFile().getName());
		System.out.println("attrs:" + attrs);
		System.out.println(attrs.creationTime().toString());
		return FileVisitResult.CONTINUE;
	}

}
