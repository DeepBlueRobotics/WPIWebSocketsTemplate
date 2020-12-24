{% import "../partials/components/" + schemaName + "Setup.java" as config %}
{% set name = config.name %}
{% set type = config.type %}
{% set hasId = config.hasId %}
{% set hasInit = schema.properties() | contains("<init") -%}
{% set cstatic = " " if hasId else " static " -%}
{% set cthis = "this" if hasId else name + "Sim" -%}
{% set qz = '""' | safe -%}
{% set cid = "id" if hasId else qz -%}
{%- set props = schema.properties() | filterProps -%}
// PLEASE DO NOT EDIT THIS FILE!!!
// This file is auto-generated by https://github.com/DeepBlueRobotics/WPIWebSocketsTemplate
// To regenerate please run:
// 'ag -o "WebotsWebSocketsImpl/src/main/org/team199/wpiws/devices" "<path/to/wpilib-ws.yaml>" "https://github.com/DeepBlueRobotics/WPIWebSocketsTemplate.git"'
package org.team199.wpiws.devices;

{% if hasId -%}
import java.util.HashMap;
{% endif -%}
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.team199.wpiws.ScopedObject;
import org.team199.wpiws.StateDevice;
import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.*;

{% if hasId -%}
public class {{ name }}Sim extends StateDevice<{{ name }}Sim.State> {
{% else -%}
public class {{ name }}Sim {
{% endif -%}
    {% if hasInit %}
    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
    {% endif -%}
    {% if hasId %}
    private static final HashMap<String, {{ name }}Sim.State> STATE_MAP = new HashMap<>();
    {%- else %}
    private static final {{ name }}Sim.State STATE = new State();
    {% endif -%}

    {% if hasId %}

    public {{ name }}Sim(String id) {
        super(id, STATE_MAP);
    }
    {% endif -%}

    {% if hasInit %}
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = {{ name }}Sim::cancelStaticInitializedCallback;
    public static void cancelStaticInitializedCallback(BooleanCallback callback) {
        STATIC_INITIALIZED_CALLBACKS.remove(callback);
    }
    
    public ScopedObject<BooleanCallback> registerInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().init);
        }
        return new ScopedObject<>(callback, CANCEL_INITIALIZED_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_INITIALIZED_CALLBACK = this::cancelInitializedCallback;
    public void cancelInitializedCallback(BooleanCallback callback) {
        getState().INITIALIZED_CALLBACKS.remove(callback);
    }

    public boolean getInitialized() {
        return getState().init;
    }

    public void setInitialized(boolean initialized) {
        setInitialized(initialized, true);
    }

    public final Consumer<BooleanCallback> CALL_INITIALIZED_CALLBACK = callback -> callback.callback(id, getState().init);
    private void setInitialized(boolean initialized, boolean notifyRobot) {
        getState().init = initialized;
        if(initialized) {
            STATIC_INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            getState().INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            INITIALIZED_DEVICES.addIfAbsent(id);
        } else {
            INITIALIZED_DEVICES.remove(id);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "{{ type }}", new WSValue("<init", initialized));
        }
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }
    {% endif -%}

    {% if hasId %}
    @Override
    protected State generateState() {
        return new State();
    }
    
    {% else %}
    protected static {{ name }}Sim.State getState() {
        return STATE;
    }
    
    {% endif -%}

    {% for propName, prop in props -%}
    {% import "../partials/initVars.java" as varInfo with context -%}
    public{{ cstatic }}ScopedObject<{{ varInfo.ptype }}Callback> register{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback callback, boolean initialNotify) {
        getState().{{ varInfo.pnameu }}_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback({{ cid }}, getState().{{ varInfo.pnamel }});
        }
        return new ScopedObject<>(callback, CANCEL_{{ varInfo.pnameu }}_CALLBACK);
    }

    public{{ cstatic }}final Consumer<{{ varInfo.ptype }}Callback> CANCEL_{{ varInfo.pnameu }}_CALLBACK = {{ cthis }}::cancel{{ varInfo.pname }}Callback;
    public{{ cstatic }}void cancel{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback callback) {
        getState().{{ varInfo.pnameu }}_CALLBACKS.remove(callback);
    }

    public{{ cstatic }}{{ varInfo.pprimtype }} get{{ varInfo.pname }}() {
        return getState().{{ varInfo.pnamel }};
    }

    public{{ cstatic }}void set{{ varInfo.pname }}({{ varInfo.pprimtype }} {{ varInfo.pnamel }}) {
        set{{ varInfo.pname }}({{ varInfo.pnamel }}, true);
    }

    public{{ cstatic }}final Consumer<{{ varInfo.ptype }}Callback> CALL_{{ varInfo.pnameu }}_CALLBACK = callback -> callback.callback({{ cid }}, getState().{{ varInfo.pnamel }});
    private{{ cstatic }}void set{{ varInfo.pname }}({{ varInfo.pprimtype }} {{ varInfo.pnamel }}, boolean notifyRobot) {
        if({{ varInfo.pnamel }} != getState().{{ varInfo.pnamel }}) {
            getState().{{ varInfo.pnamel }} = {{ varInfo.pnamel }};
            getState().{{ varInfo.pnameu }}_CALLBACKS.forEach(CALL_{{ varInfo.pnameu }}_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage({{ cid }}, "{{ type }}", new WSValue("{{ varInfo.pfname }}", {{ varInfo.pnamel }}));
        }
    }

    {% endfor -%}

    public static void processMessage(String device, List<WSValue> data) {
        {%- if hasId %}
        {{ name }}Sim simDevice = new {{ name }}Sim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
        {%- else %}
        for(WSValue value: data) {
            processValue(value);
        }
        {%- endif %}
    }

    {% if hasInit -%}
    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    {% endif -%}
    {% for propName, prop in props -%}
    {% import "../partials/initVars.java" as varInfo with context -%}
    private{{ cstatic }}final BiConsumer<{{ varInfo.parrtype }}, Boolean> SET_{{ varInfo.pnameu }} = {{ cthis }}::set{{ varInfo.pname }};
    {% endfor -%}
    private{{ cstatic }}void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                {% if hasInit -%}
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                {%- endif -%}
                {% for propName, prop in props -%}
                {% import "../partials/initVars.java" as varInfo with context %}
                case "{{ varInfo.pfname }}": {
                    {% if hasId -%}
                    filterMessageAndIgnoreRobotState(value.getValue(), {{ varInfo.parrtype }}.class, SET_{{ varInfo.pnameu }});
                    {% else -%}
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), {{ varInfo.parrtype }}.class, SET_{{ varInfo.pnameu }});
                    {% endif -%}
                    break;
                }
                {%- endfor %}
            }
        }
    }

    public static class State {
        {% if hasInit -%}
        public boolean init = false;
        {% endif -%}
        {% for propName, prop in props -%}
        {% import "../partials/initVars.java" as varInfo with context -%}
        public {{ varInfo.pprimtype }} {{ varInfo.pnamel }} = {{ varInfo.pinit }};
        {% endfor -%}
        {% if hasInit -%}
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        {% endif -%}
        {% for propName, prop in props -%}
        {% import "../partials/initVars.java" as varInfo with context -%}
        public final CopyOnWriteArrayList<{{ varInfo.ptype }}Callback> {{ varInfo.pnameu }}_CALLBACKS = new CopyOnWriteArrayList<>();
        {%- endfor %}
    }

}