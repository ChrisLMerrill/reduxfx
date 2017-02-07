package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class StackPaneBuilder<BUILDER extends StackPaneBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String ALIGNMENT = "alignment";

    public StackPaneBuilder(Class<?> nodeClass,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new StackPaneBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER alignment(Pos value) {
        return property(ALIGNMENT, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
