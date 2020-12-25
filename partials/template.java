{% macro template(name, type, hasId, schema) -%}
{% set hasInit = schema.properties() | contains("<init") -%}
{% set cstatic = " " if hasId else " static " -%}
{% set cthis = "this" if hasId else name + "Sim" -%}
{% set qz = '""' | safe -%}
{% set cid = "id" if hasId else qz -%}
{% set a = name | aAn -%}
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

/**
 * Represents a simulated {{ name | lower }}
 */
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

    /**
     * Creates a new {{ name }}Sim
     * @param id the device identifier of this {{ name }}Sim 
     */
    public {{ name }}Sim(String id) {
        super(id, STATE_MAP);
    }
    {% endif -%}

    {% if hasInit %}
    /**
     * Registers a BooleanCallback to be called whenever {{ a }} {{ name }}Sim device is initialized or uninitialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with the device identifiers of all currently initialized {{ name }}Sims
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelStaticInitializedCallback(BooleanCallback)
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelStaticInitializedCallback(BooleanCallback)}
     */
    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = {{ name }}Sim::cancelStaticInitializedCallback;
    /**
     * Deregisters the given static initialized callback
     * @param callback the callback to deregister
     * @see #registerStaticInitializedCallback(BooleanCallback, boolean)
     */
    public static void cancelStaticInitializedCallback(BooleanCallback callback) {
        STATIC_INITIALIZED_CALLBACKS.remove(callback);
    }
    
    /**
     * Registers a BooleanCallback to be called whenever this device is initialized or uninitialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current initialized state
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelInitializedCallback(BooleanCallback)
     * @see #registerStaticInitializedCallback(BooleanCallback, boolean)
     */
    public ScopedObject<BooleanCallback> registerInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().init);
        }
        return new ScopedObject<>(callback, CANCEL_INITIALIZED_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelInitializedCallback(BooleanCallback)}
     */
    public final Consumer<BooleanCallback> CANCEL_INITIALIZED_CALLBACK = this::cancelInitializedCallback;
    /**
     * Deregisters the given initialized callback
     * @param callback the callback to deregister
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public void cancelInitializedCallback(BooleanCallback callback) {
        getState().INITIALIZED_CALLBACKS.remove(callback);
    }

    /**
     * @return whether this {{ name }}Sim is initialized
     */
    public boolean getInitialized() {
        return getState().init;
    }

    /**
     * Sets the initialized state of this {{ name }}Sim
     * @param initialized the new initialized state of this {{ name }}Sim
     */
    public void setInitialized(boolean initialized) {
        setInitialized(initialized, true);
    }

    /**
     * A Consumer which calls the given BooleanCallback with the current initialized state of this {{ name }}Sim
     */
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

    /**
     * @return an array of the identifiers of all currently initialized {{ name }}Sims
     */
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
    /**
     * Registers a {{ varInfo.ptype }}Callback to be called whenever the {{ varInfo.pnamel }} of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current {{ varInfo.pnamel }} value
     * @return a ScopedObject which can be used to close the callback
     * @see #cancel{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback)
     */
    public{{ cstatic }}ScopedObject<{{ varInfo.ptype }}Callback> register{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback callback, boolean initialNotify) {
        getState().{{ varInfo.pnameu }}_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback({{ cid }}, getState().{{ varInfo.pnamel }});
        }
        return new ScopedObject<>(callback, CANCEL_{{ varInfo.pnameu }}_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancel{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback)}
     */
    public{{ cstatic }}final Consumer<{{ varInfo.ptype }}Callback> CANCEL_{{ varInfo.pnameu }}_CALLBACK = {{ cthis }}::cancel{{ varInfo.pname }}Callback;
    /**
     * Deregisters the given {{ varInfo.pnamel }} callback
     * @param callback the callback to deregister
     * @see #register{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback, boolean)
     */
    public{{ cstatic }}void cancel{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback callback) {
        getState().{{ varInfo.pnameu }}_CALLBACKS.remove(callback);
    }

    /**
     * @return {{ prop.description() | lower }}
     * @see #set{{ varInfo.pname }}({{ varInfo.pprimtype }})
     */
    public{{ cstatic }}{{ varInfo.pprimtype }} get{{ varInfo.pname }}() {
        return getState().{{ varInfo.pnamel }};
    }

    /**
     * Set {{ prop.description() | lower }}
     * @see #get{{ varInfo.pname }}()
     */
    public{{ cstatic }}void set{{ varInfo.pname }}({{ varInfo.pprimtype }} {{ varInfo.pnamel }}) {
        set{{ varInfo.pname }}({{ varInfo.pnamel }}, true);
    }

    /**
     * A Consumer which calls the given callback with the current {{ varInfo.pnamel }} value of this PWMSim
     */
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

    /**
     * An implementation of {@link org.team199.wpiws.interfaces.DeviceMessageProcessor} which processes WPI HALSim messages for {{ name }}Sims
     * @param device the device identifier of the device sending the message
     * @param data the data associated with the message
     */
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

    /**
     * Contains all information about the state of a {{ name }}Sim
     */
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
{% endmacro %}