package io.leaderli.demo.swing;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class RolloverSpinnerListModel<T> extends AbstractSpinnerModel {
    final Object[] items;
    int index = -1;

    public RolloverSpinnerListModel(T[] items) {
        this.items = items;
        if (items.length > 0) {
            setValue(this.items[0]);
        }
    }

    public RolloverSpinnerListModel(List<T> items) {
        this.items = items.toArray();
    }

    @Override
    public Object getValue() {
        if (index > -1 && index < items.length) {
            return items[index];
        }
        return null;
    }


    @Override
    public void setValue(Object value) {
        if (value instanceof CharSequence) {
            value = ((CharSequence) value).charAt(0);
        }
        for (int i = 0; i < items.length; i++) {

            if (Objects.equals(value, items[i])) {
                index = i;
                fireStateChanged();
                return;
            }
        }
        index = -1;
    }

    @Override
    public T getNextValue() {
        if (index < items.length - 1) {
            return (T) items[index + 1];
        }
        return (T) items[0];
    }

    @Override
    public T getPreviousValue() {
        if (index > 0) {
            return (T) items[index - 1];
        }
        return (T) items[items.length - 1];
    }
}
