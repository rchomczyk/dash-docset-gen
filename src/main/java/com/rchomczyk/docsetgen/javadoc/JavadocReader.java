package com.rchomczyk.docsetgen.javadoc;

import com.rchomczyk.docsetgen.docset.EntryType;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class JavadocReader {

    private final Javadoc javadoc;

    public JavadocReader(@NotNull Javadoc javadoc) {
        this.javadoc = javadoc;
    }

    public Set<JavadocEntry> read() {
        Set<JavadocEntry> entries = new HashSet<>();
        try {
            Document indexFile = Jsoup.parse(javadoc.getIndexFile());

            Elements indexElements = indexFile.getElementsByTag("dt");
            for (Element indexElement : indexElements) {
                String elementText = indexElement.text();
                if (indexElement.hasText() && elementText.toLowerCase().contains("method")) {
                    String[] partitionedText = elementText.split(" - ");
                    String methodName = partitionedText[0];
                    String methodPath;
                    Elements linkElements = indexElement.getElementsByTag("a");
                    if (linkElements.isEmpty()) {
                        methodPath = (partitionedText[1].contains(" in class ")
                                ? partitionedText[1].split(" in class ")[1]
                                : partitionedText[1].split(" in interface ")[1])
                                .replace(".", "/");
                    } else {
                        methodPath = linkElements.get(0).attr("href");
                    }
                    entries.add(new JavadocEntry(methodName, EntryType.METHOD, methodPath));
                }

                if (indexElement.hasText() && elementText.toLowerCase(Locale.ROOT).contains("constructor")) {
                    Elements linkElements = indexElement.getElementsByTag("a");
                    if (linkElements.isEmpty()) {
                        continue;
                    }
                    Element linkElement = linkElements.get(0);
                    String constructorName = linkElement.text();
                    String constructorPath = linkElement.attr("href");
                    entries.add(new JavadocEntry(constructorName, EntryType.CONSTRUCTOR, constructorPath));
                }
            }

            Document indexPackageFile = Jsoup.parse(javadoc.getIndexPackageFile());

            Element packageElements = indexPackageFile.getElementsByClass("summary-table").get(0);
            for (Element packageElement : packageElements.getElementsByClass("col-first")) {
                String packageName = packageElement.text();
                String packagePath = packageName.replace(".", "/") + "/package-summary.html";
                if (packagePath.startsWith("Package/")) {
                    continue;
                }
                entries.add(new JavadocEntry(packageName, EntryType.PACKAGE, packagePath));
            }

            Document indexClassFile = Jsoup.parse(javadoc.getIndexClassFile());
            for (int typeIndex = 0; typeIndex < 8; typeIndex++) {
                EntryType situationalType = EntryType.getEntryTypeWithIndex(typeIndex);
                if (situationalType == null) {
                    continue;
                }
                Elements elements = indexClassFile.getElementsByClass("all-classes-table-tab" + typeIndex);
                for (Element element : elements) {
                    Elements linkElements = element.getElementsByTag("a");
                    if (linkElements.isEmpty()) {
                        continue;
                    }
                    Element linkElement = linkElements.get(0);
                    entries.add(new JavadocEntry(linkElement.text(), situationalType, linkElement.attr("href")));
                }
            }

            Document indexConstantDocument = Jsoup.parse(javadoc.getIndexConstantFile());

            Elements constantElements = indexConstantDocument.getElementsByClass("col-second");
            for (Element constantElement : constantElements) {
                Elements linkElements = constantElement.getElementsByTag("a");
                if (linkElements.isEmpty()) {
                    continue;
                }
                Element linkElement = linkElements.get(0);
                String constantName = linkElement.text();
                String constantPath = linkElement.attr("href");
                if (constantPath.contains("../")) {
                    constantPath = constantPath.split(javadoc.getSourceFile().getName())[1].substring(1);
                }
                entries.add(new JavadocEntry(constantName, EntryType.CONSTANT, constantPath));
            }
        } catch (IOException exception) {
            throw new JavadocReaderException("There was an unexpected incident, while trying to read the javadoc indexes.", exception);
        }

        return entries;
    }
}
