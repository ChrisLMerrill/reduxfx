package com.netopyr.reduxfx.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class UpdateRootPatch extends Patch {

    private final VNode newNode;

    public UpdateRootPatch(int index, VNode newNode) {
        super(index);
        this.newNode = Objects.requireNonNull(newNode, "NewNode must not be null");
    }

    public VNode getNewNode() {
        return newNode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("newNode", newNode)
                .toString();
    }
}
