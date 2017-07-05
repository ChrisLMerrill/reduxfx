package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings({"unused", "SameParameterValue"})
public class LabeledBuilder<BUILDER extends LabeledBuilder<BUILDER>> extends ControlBuilder<BUILDER> {

    private static final String GRAPHIC = "graphic";

    private static final String ALIGNMENT = "alignment";
    private static final String MNEMONIC_PARSING = "mnemonicParsing";
    private static final String TEXT = "text";
    private static final String TEXT_ALIGNMENT = "textAlignment";
    private static final String TEXT_FILL = "textFill";
    private static final String WRAP_TEXT = "wrapText";

    public LabeledBuilder(Class<?> nodeClass,
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
        return (BUILDER) new LabeledBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public BUILDER graphic(VNode value) {
        return child(GRAPHIC, value);
    }

    public BUILDER alignment(Pos value) {
        return property(ALIGNMENT, value);
    }

    public BUILDER mnemonicParsing(boolean value) {
        return property(MNEMONIC_PARSING, value);
    }

    public BUILDER text(String value) {
        return property(TEXT, value);
    }

    public BUILDER textAlignment(TextAlignment value) {
        return property(TEXT_ALIGNMENT, value);
    }

    public BUILDER textFill(Paint value) {
        return property(TEXT_FILL, value);
    }

    public BUILDER wrapText(boolean value) {
        return property(WRAP_TEXT, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}