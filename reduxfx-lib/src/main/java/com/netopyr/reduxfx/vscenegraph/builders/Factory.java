package com.netopyr.reduxfx.vscenegraph.builders;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VProperty.Phase;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.Tuple5;
import javaslang.collection.Array;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Factory {

    private static final long EXPIRATION_SECONDS = 30;
    private static final long MAX_SIZE = 10000;

    private static final Cache<Tuple5<Object, Map<String, Array<VNode>>, Map<String, Option<VNode>>, Map<String, VProperty>, Map<VEventType, VEventHandler>>, VNode> nodeCache =
            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(MAX_SIZE)
                    .build();

    private static final Cache<Tuple2<Phase, Object>, VProperty> propertyWithValueCache =
            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(MAX_SIZE)
                    .build();


    private Factory() {
    }


    @SuppressWarnings("unchecked")
    public static <BUILDER extends Builder<BUILDER>> BUILDER node(
            Builder<BUILDER> builder,
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return eventHandlers.isEmpty() && properties.filter(p -> p._2.getChangeListener().isDefined() || p._2.getInvalidationListener().isDefined()).isEmpty() ?
                (BUILDER) nodeCache.get(
                        Tuple.of(builder.getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers),
                        tuple -> builder.create(childrenMap, singleChildMap, properties, eventHandlers)
                ) : builder.create(childrenMap, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    public static <BUILDER extends VNode> BUILDER node(Object typeKey, Supplier<BUILDER> factory) {
        return (BUILDER) nodeCache.get(
                Tuple.of(typeKey, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()),
                tuple -> factory.get()
        );
    }


    public static <TYPE> VProperty property(Phase phase, TYPE value, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return new VProperty(phase, value, Option.of(changeListener), Option.of(invalidationListener));
    }

    public static <TYPE> VProperty property(Phase phase, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return new VProperty(phase, value, Option.of(changeListener), Option.none());
    }

    public static VProperty property(Phase phase, Object value, VInvalidationListener invalidationListener) {
        return new VProperty(phase, value, Option.none(), Option.of(invalidationListener));
    }

    public static VProperty property(Phase phase, Object value) {
        return propertyWithValueCache.get(
                Tuple.of(phase, value),
                tuple -> new VProperty(phase, value, Option.none(), Option.none())
        );
    }

    public static VProperty property(Phase phase, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return new VProperty(phase, Option.of(changeListener), Option.of(invalidationListener));
    }

    public static VProperty property(Phase phase, VChangeListener<?> changeListener) {
        return new VProperty(phase, Option.of(changeListener), Option.none());
    }

    public static VProperty property(Phase phase, VInvalidationListener invalidationListener) {
        return new VProperty(phase, Option.none(), Option.of(invalidationListener));
    }

    public static VProperty property(Phase phase) {
        return new VProperty(phase, Option.none(), Option.none());
    }
}
