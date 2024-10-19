package br.com.bagnascojhoel.contactsaggregator.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
public final class Page<T> {

    private final List<T> content;
    private final int page;
    private final int totalElements;

    public static <T> Page<T> of(@NonNull Collection<T> content) {
        return new Page<T>(new ArrayList<T>(content), 0, content.size());
    }

    public int getPageSize() {
        return this.content.size();
    }

}
