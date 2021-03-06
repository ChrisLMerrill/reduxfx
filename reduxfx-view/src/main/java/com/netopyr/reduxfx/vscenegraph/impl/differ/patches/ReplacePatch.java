package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import io.vavr.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class ReplacePatch extends Patch {

    private final int index;
    private final VNode newNode;

    public ReplacePatch(Vector<Object> path, int index, VNode newNode) {
        super(path);
        this.index = index;
        this.newNode = Objects.requireNonNull(newNode, "NewNode must not be null");
    }

    public int getIndex() {
        return index;
    }

    public VNode getNewNode() {
        return newNode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("index", index)
                .append("newNode", newNode)
                .toString();
    }
}
