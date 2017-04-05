package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.ActionEvent;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.ACTION;

@SuppressWarnings("unused")
public class TitledPaneBuilder<BUILDER extends TitledPaneBuilder<BUILDER>> extends LabeledBuilder<BUILDER> {

    private static final String CONTENT = "content";

    public TitledPaneBuilder(Class<?> nodeClass,
                             Map<String, Array<VNode>> childrenMap,
                             Map<String, Option<VNode>> singleChildMap,
                             Map<String, VProperty> properties,
                             Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new TitledPaneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public BUILDER content(VNode value) {
        return child(CONTENT, value);
    }


    public BUILDER onAction(VEventHandler<ActionEvent> eventHandler) {
        return onEvent(ACTION, eventHandler);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}