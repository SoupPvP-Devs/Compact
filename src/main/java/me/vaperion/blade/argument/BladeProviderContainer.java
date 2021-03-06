package me.vaperion.blade.argument;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BladeProviderContainer<T> {
    private final Class<T> type;
    private final BladeProvider<T> provider;
    private final List<Class<? extends Annotation>> requiredAnnotations;

    public boolean doAnnotationsMatch(List<Class<? extends Annotation>> annotations) {
        if (annotations == null) return true;
        if (requiredAnnotations == null) return false;
        return annotations.containsAll(requiredAnnotations) && requiredAnnotations.size() == annotations.size();
    }
}
