package com.lionelwei.together.interfaces;

import java.util.List;

public interface IAdapter<P, D> {
    void setPresenter(P presenter);
    void updateData(List<D> data);
}
