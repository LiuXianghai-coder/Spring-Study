package com.example.demo.tools;

import com.example.demo.common.IfExtract;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lxh
 * @date 2022/8/9-下午10:53
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(value = {"com.example.demo.common.IfExtract"})
public class IfExtractProcess extends AbstractProcessor {

    private final List<Element> interMethods = new ArrayList<>();

    private Elements elementUtils;

    private ProcessingEnvironment procEnv;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.procEnv = processingEnv;
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        Set<? extends Element> annotates = roundEnv.getElementsAnnotatedWith(IfExtract.class);
        for (Element e : annotates) {
            String interName = e.getAnnotation(IfExtract.class).name();
            List<? extends Element> list = e.getEnclosedElements();
            for (Element enclosed : list) {
                ElementKind kind = enclosed.getKind();
                if (kind != ElementKind.METHOD) continue;

                Set<Modifier> modifiers = enclosed.getModifiers();
                if (modifiers.contains(Modifier.STATIC)) continue;
                if (!modifiers.contains(Modifier.PUBLIC)) continue;
                interMethods.add(enclosed);
            }

            writeInterToFile(interName);
        }

        return false;
    }

    private void writeInterToFile(String interName) {
        try (Writer writer = procEnv.getFiler()
                .createSourceFile(interName)
                .openWriter()
        ) {
            Element e = interMethods.get(0);
            String packName = elementUtils.getPackageOf(e).toString();
            writer.write("package " + packName + ";\n");
            writer.write("public interface " + interName + "{\n");
            for (Element method : interMethods) {
                ExecutableElement element = (ExecutableElement) method;
                StringBuilder sig = new StringBuilder();
                sig.append("\tpublic ").append(element.getReturnType())
                        .append(" ").append(method.getSimpleName())
                        .append(createArgList(element.getParameters()))
                        .append(";\n\n");
                writer.write(sig.toString());
            }
            writer.write("}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createArgList(List<? extends VariableElement> params) {
        String s = params.stream().map(v -> v.asType() + " " + v.getSimpleName())
                .collect(Collectors.joining(", "));
        return "(" + s + ")";
    }
}
