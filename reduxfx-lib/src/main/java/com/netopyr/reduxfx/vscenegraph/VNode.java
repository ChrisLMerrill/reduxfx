package com.netopyr.reduxfx.vscenegraph;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javaslang.Tuple2;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;
import java.util.function.Consumer;

public final class VNode implements VElement {

    private final VNodeType type;
    private final Option<Consumer<Object>> ref;
    private final List<VNode> children;
    private final Map<VPropertyType, Object> properties;
    private final Map<VEventType, EventHandler<?>> eventHandlers;
    private final Map<VPropertyType, ChangeListener<?>> changeListeners;
    private final Map<VPropertyType, InvalidationListener> invalidationListeners;
    private final int size;


    public VNode(VNodeType type, VElement... elements) {
        this.type = Objects.requireNonNull(type, "Type must not be null");

        final List<VElement> allElements = List.of(elements);

        this.ref = allElements.findLast(element -> element instanceof VReference)
                .map(element -> ((VReference) element).getRef());
        this.children = allElements.filter(element -> element instanceof VNode)
                .map(element -> (VNode) element);
        this.properties = allElements.filter(element -> element instanceof VProperty)
                .map(element -> (VProperty) element)
                .toMap(element -> new Tuple2<>(element.getType(), element.getValue()));
        this.eventHandlers = allElements.filter(element -> element instanceof VEventHandler)
                .map(element -> (VEventHandler<?>) element)
                .toMap(element -> new Tuple2<>(element.getType(), element.getEventHandler()));
        this.changeListeners = allElements.filter(element -> element instanceof VChangeListener)
                .map(element -> (VChangeListener<?>) element)
                .toMap(element -> new Tuple2<>(element.getType(), element.getListener()));
        this.invalidationListeners = allElements.filter(element -> element instanceof VInvalidationListener)
                .map(element -> (VInvalidationListener) element)
                .toMap(element -> new Tuple2<>(element.getType(), element.getListener()));

        this.size = children.map(VNode::getSize).sum().intValue() + 1;
    }


    public int getSize() {
        return size;
    }

    public VNodeType getType() {
        return type;
    }

    public Option<Consumer<Object>> getRef() {
        return ref;
    }

    public List<VNode> getChildren() {
        return children;
    }

    public Map<VPropertyType, Object> getProperties() {
        return properties;
    }

    public Map<VEventType, EventHandler<?>> getEventHandlers() {
        return eventHandlers;
    }

    public Map<VPropertyType, ChangeListener<?>> getChangeListeners() {
        return changeListeners;
    }

    public Map<VPropertyType, InvalidationListener> getInvalidationListeners() {
        return invalidationListeners;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("children", children)
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .append("changeListeners", changeListeners)
                .append("invalidationListeners", invalidationListeners)
                .toString();
    }
}